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

package com.epam.digital.data.platform.soapapi.core.aspect;

import static com.epam.digital.data.platform.soapapi.core.utils.EndpointTestUtil.getMockHeaders;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.epam.digital.data.platform.soapapi.core.config.TestConfig;
import com.epam.digital.data.platform.soapapi.core.converter.HeadersProvider;
import com.epam.digital.data.platform.soapapi.core.endpoint.TestEndpointHandler;
import com.epam.digital.data.platform.soapapi.core.restclients.TestRestApiClient;
import com.epam.digital.data.platform.starter.actuator.livenessprobe.LivenessStateHandler;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

@Import({AopAutoConfiguration.class})
@SpringBootTest(classes = {LivenessStateCheckAspect.class, TestConfig.class})
class LivenessStateCheckAspectTest {

  @Autowired
  private TestEndpointHandler endpointHandler;
  @Autowired
  private LivenessStateCheckAspect livenessStateCheckAspect;

  @MockBean
  private HeadersProvider headersProvider;
  @MockBean
  private TestRestApiClient restApiClient;
  @MockBean
  private LivenessStateHandler livenessStateHandler;

  @Test
  void expectLivenessStateHandlerIsCalledOnException() {
    RuntimeException mockException = new RuntimeException();
    when(headersProvider.createHttpHeaders(any())).thenThrow(mockException);

    try {
      endpointHandler.search(new Object(), getMockHeaders());
    } catch (Exception ignored) {
    }

    verify(livenessStateHandler).handleResponse(eq(mockException), any());
  }

  @Test
  void expectLivenessStateHandlerIsCalledWithNullExceptionWhenSuccess() {

    endpointHandler.search(new Object(), getMockHeaders());

    verify(livenessStateHandler).handleResponse(eq(null), any());
  }

  @Test
  void testLivenessHealthyExceptionRecognition() {
    assertThat(livenessStateCheckAspect.isNonLivenessHealthyException(mock(FeignException.class))).isFalse();
    assertThat(livenessStateCheckAspect.isNonLivenessHealthyException(new NullPointerException())).isTrue();
  }
}
