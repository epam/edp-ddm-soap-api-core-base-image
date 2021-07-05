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

package com.epam.digital.data.platform.soapapi.core.audit;

import com.epam.digital.data.platform.soapapi.core.util.SoapHeaders;
import com.epam.digital.data.platform.starter.audit.model.AuditSourceInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.MDC;

import static com.epam.digital.data.platform.soapapi.core.util.Header.X_SOURCE_APPLICATION;
import static com.epam.digital.data.platform.soapapi.core.util.Header.X_SOURCE_BUSINESS_ACTIVITY;
import static com.epam.digital.data.platform.soapapi.core.util.Header.X_SOURCE_BUSINESS_ACTIVITY_INSTANCE_ID;
import static com.epam.digital.data.platform.soapapi.core.util.Header.X_SOURCE_BUSINESS_PROCESS;
import static com.epam.digital.data.platform.soapapi.core.util.Header.X_SOURCE_BUSINESS_PROCESS_DEFINITION_ID;
import static com.epam.digital.data.platform.soapapi.core.util.Header.X_SOURCE_BUSINESS_PROCESS_INSTANCE_ID;
import static com.epam.digital.data.platform.soapapi.core.util.Header.X_SOURCE_SYSTEM;
import static com.epam.digital.data.platform.soapapi.core.audit.AuditTestUtil.BUSINESS_ACTIVITY;
import static com.epam.digital.data.platform.soapapi.core.audit.AuditTestUtil.BUSINESS_ACTIVITY_INSTANCE_ID;
import static com.epam.digital.data.platform.soapapi.core.audit.AuditTestUtil.BUSINESS_PROCESS;
import static com.epam.digital.data.platform.soapapi.core.audit.AuditTestUtil.BUSINESS_PROCESS_DEFINITION_ID;
import static com.epam.digital.data.platform.soapapi.core.audit.AuditTestUtil.BUSINESS_PROCESS_INSTANCE_ID;
import static com.epam.digital.data.platform.soapapi.core.audit.AuditTestUtil.SOURCE_APPLICATION;
import static com.epam.digital.data.platform.soapapi.core.audit.AuditTestUtil.SOURCE_SYSTEM;
import static org.assertj.core.api.Assertions.assertThat;

class MdcAuditSourceInfoProcessorTest {

  private final MdcAuditSourceInfoProcessor auditSourceInfoProcessor =
      new MdcAuditSourceInfoProcessor();

  @BeforeEach
  void beforeEach() {
    MDC.clear();
  }

  @Test
  void expectCorrectMdcSourceInfoInitialization() {
    var headers = new SoapHeaders();
    headers.setSourceSystem(SOURCE_SYSTEM);
    headers.setSourceApplication(SOURCE_APPLICATION);
    headers.setSourceBusinessProcess(BUSINESS_PROCESS);
    headers.setSourceBusinessProcessInstanceId(BUSINESS_PROCESS_INSTANCE_ID);
    headers.setSourceBusinessProcessDefinitionId(BUSINESS_PROCESS_DEFINITION_ID);
    headers.setSourceBusinessActivity(BUSINESS_ACTIVITY);
    headers.setSourceBusinessActivityInstanceId(BUSINESS_ACTIVITY_INSTANCE_ID);

    auditSourceInfoProcessor.initSourceInfo(headers);

    assertThat(MDC.get(X_SOURCE_SYSTEM)).isEqualTo(SOURCE_SYSTEM);
    assertThat(MDC.get(X_SOURCE_APPLICATION)).isEqualTo(SOURCE_APPLICATION);
    assertThat(MDC.get(X_SOURCE_BUSINESS_PROCESS)).isEqualTo(BUSINESS_PROCESS);
    assertThat(MDC.get(X_SOURCE_BUSINESS_PROCESS_INSTANCE_ID)).isEqualTo(BUSINESS_PROCESS_INSTANCE_ID);
    assertThat(MDC.get(X_SOURCE_BUSINESS_PROCESS_DEFINITION_ID)).isEqualTo(BUSINESS_PROCESS_DEFINITION_ID);
    assertThat(MDC.get(X_SOURCE_BUSINESS_ACTIVITY)).isEqualTo(BUSINESS_ACTIVITY);
    assertThat(MDC.get(X_SOURCE_BUSINESS_ACTIVITY_INSTANCE_ID)).isEqualTo(BUSINESS_ACTIVITY_INSTANCE_ID);
  }

  @Test
  void expectCorrectSourceInfoRetrievingFromMdc() {
    MDC.put(X_SOURCE_SYSTEM, SOURCE_SYSTEM);
    MDC.put(X_SOURCE_APPLICATION, SOURCE_APPLICATION);
    MDC.put(X_SOURCE_BUSINESS_PROCESS, BUSINESS_PROCESS);
    MDC.put(X_SOURCE_BUSINESS_PROCESS_INSTANCE_ID, BUSINESS_PROCESS_INSTANCE_ID);
    MDC.put(X_SOURCE_BUSINESS_PROCESS_DEFINITION_ID, BUSINESS_PROCESS_DEFINITION_ID);
    MDC.put(X_SOURCE_BUSINESS_ACTIVITY, BUSINESS_ACTIVITY);
    MDC.put(X_SOURCE_BUSINESS_ACTIVITY_INSTANCE_ID, BUSINESS_ACTIVITY_INSTANCE_ID);

    var actualSourceInfo = auditSourceInfoProcessor.getAuditSourceInfo();

    var expectedSourceInfo = AuditSourceInfo.AuditSourceInfoBuilder.anAuditSourceInfo()
            .system(SOURCE_SYSTEM)
            .application(SOURCE_APPLICATION)
            .businessProcess(BUSINESS_PROCESS)
            .businessProcessDefinitionId(BUSINESS_PROCESS_DEFINITION_ID)
            .businessProcessInstanceId(BUSINESS_PROCESS_INSTANCE_ID)
            .businessActivity(BUSINESS_ACTIVITY)
            .businessActivityInstanceId(BUSINESS_ACTIVITY_INSTANCE_ID)
            .build();
    assertThat(actualSourceInfo).usingRecursiveComparison().isEqualTo(expectedSourceInfo);
  }
}
