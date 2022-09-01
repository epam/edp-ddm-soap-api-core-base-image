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

import com.epam.digital.data.platform.soapapi.core.exception.TokenRetrievalException;
import com.epam.digital.data.platform.soapapi.core.util.Header;
import com.epam.digital.data.platform.soapapi.core.util.SoapHeaders;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.keycloak.admin.client.token.TokenManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HeadersProvider {

  private final Logger log = LoggerFactory.getLogger(HeadersProvider.class);

  private final boolean isKeycloakEnabled;

  private final ObjectMapper objectMapper;
  private final TokenManager serviceAccountTokenManager;

  public HeadersProvider(
      @Value("${data-platform.keycloak.enabled}") boolean isKeycloakEnabled,
      ObjectMapper objectMapper,
      TokenManager serviceAccountTokenManager) {
    this.isKeycloakEnabled = isKeycloakEnabled;
    this.objectMapper = objectMapper;
    this.serviceAccountTokenManager = serviceAccountTokenManager;
  }

  public Map<String, Object> createHttpHeaders(SoapHeaders soapHeaders) {
    TypeReference<Map<String, Object>> reference = new TypeReference<>() {};
    var httpHeaders = objectMapper.convertValue(soapHeaders, reference);
    if (isKeycloakEnabled) {
      String jwt;
      try {
        jwt = serviceAccountTokenManager.getAccessTokenString();
      } catch (Exception e) {
        log.info("can not obtain token from Keycloak", e);
        throw new TokenRetrievalException(e);
      }
      httpHeaders.put(Header.X_ACCESS_TOKEN, jwt);
    }
    return httpHeaders;
  }
}
