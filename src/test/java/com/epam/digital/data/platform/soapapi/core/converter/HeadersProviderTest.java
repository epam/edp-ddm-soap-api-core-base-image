/*
 * Copyright 2021 EPAM Systems.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.epam.digital.data.platform.soapapi.core.converter;

import static com.epam.digital.data.platform.soapapi.core.utils.EndpointTestUtil.SOURCE_APP;
import static com.epam.digital.data.platform.soapapi.core.utils.EndpointTestUtil.getMockHeaders;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.epam.digital.data.platform.soapapi.core.exception.TokenRetrievalException;
import com.epam.digital.data.platform.soapapi.core.util.Header;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.keycloak.admin.client.token.TokenManager;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class HeadersProviderTest {

  private HeadersProvider headersProvider;

  private static final String TOKEN = "token";

  private final ObjectMapper objectMapper = new ObjectMapper();
  @Mock
  private TokenManager tokenManager;

  @BeforeEach
  void beforeEach() {
    keycloakEnabled(true);
  }

  @Test
  void shouldConvertSoapToHttpHeadersAndRewriteTokenWhenKeycloakEnabled() {
    keycloakEnabled(true);

    when(tokenManager.getAccessTokenString()).thenReturn(TOKEN);
    var mockSoapSoapHeaders = getMockHeaders();

    Map<String, Object> actual = headersProvider.createHttpHeaders(mockSoapSoapHeaders);

    assertThat(actual).hasSize(2)
            .containsEntry(Header.X_ACCESS_TOKEN, TOKEN)
            .containsEntry(Header.X_SOURCE_APPLICATION, SOURCE_APP);
    verify(tokenManager).getAccessTokenString();
  }

  private void keycloakEnabled(boolean b) {
    headersProvider = new HeadersProvider(b, objectMapper, tokenManager);
  }

  @Test
  void shouldConvertSoapToHttpHeadersWithAccessTokenWhenKeycloakDisabled() {
    keycloakEnabled(false);

    var mockSoapHeaders = getMockHeaders();
    mockSoapHeaders.setAccessToken(TOKEN);

    Map<String, Object> actual = headersProvider.createHttpHeaders(mockSoapHeaders);

    assertThat(actual).hasSize(2)
            .containsEntry(Header.X_ACCESS_TOKEN, TOKEN)
            .containsEntry(Header.X_SOURCE_APPLICATION, SOURCE_APP);
    verify(tokenManager, never()).getAccessTokenString();
  }

  @Test
  void shouldDropExceptionWhenKeycloakRequestFailed() {
    keycloakEnabled(true);
    when(tokenManager.getAccessTokenString()).thenThrow(new RuntimeException());

    assertThatThrownBy(() -> headersProvider.createHttpHeaders(getMockHeaders()))
        .isInstanceOf(TokenRetrievalException.class);
  }
}
