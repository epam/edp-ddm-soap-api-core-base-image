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

package com.epam.digital.data.platform.soapapi.core.service;

import static org.assertj.core.api.Assertions.assertThat;
import static com.epam.digital.data.platform.soapapi.core.util.Header.TRACE_ID;
import static com.epam.digital.data.platform.soapapi.core.util.Header.X_SOURCE_BUSINESS_PROCESS;
import static com.epam.digital.data.platform.soapapi.core.util.Header.X_SOURCE_SYSTEM;

import org.junit.jupiter.api.Test;
import org.slf4j.MDC;
import com.epam.digital.data.platform.soapapi.core.util.SoapHeaders;

class MdcTraceProviderTest {

  static final String TEST = "test";

  MdcTraceProvider instance = new MdcTraceProvider();

  @Test
  void testRequestIdRetrieval() {
    MDC.put(TRACE_ID, TEST);
    assertThat(instance.getRequestId()).isEqualTo(TEST);
  }
}
