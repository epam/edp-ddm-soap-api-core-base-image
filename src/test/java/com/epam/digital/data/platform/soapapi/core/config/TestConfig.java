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

package com.epam.digital.data.platform.soapapi.core.config;

import com.epam.digital.data.platform.soapapi.core.converter.HeadersProvider;
import com.epam.digital.data.platform.soapapi.core.endpoint.TestEndpointHandler;
import com.epam.digital.data.platform.soapapi.core.restclients.TestRestApiClient;
import com.epam.digital.data.platform.soapapi.core.audit.MdcAuditSourceInfoProcessor;
import com.epam.digital.data.platform.soapapi.core.service.MdcTraceProvider;
import com.epam.digital.data.platform.soapapi.core.service.TraceProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig {

  @Bean
  public ObjectMapper objectMapper() {
    return new ObjectMapper();
  }

  @Bean
  public TraceProvider traceProvider() {
    return new MdcTraceProvider();
  }

  @Bean
  public MdcAuditSourceInfoProcessor auditSourceInfoProcessor() {
    return new MdcAuditSourceInfoProcessor();
  }

  @Bean
  public TestEndpointHandler endpointHandler(
      TestRestApiClient testRestApiClient, HeadersProvider headersProvider) {
    return new TestEndpointHandler(testRestApiClient, headersProvider);
  }
}
