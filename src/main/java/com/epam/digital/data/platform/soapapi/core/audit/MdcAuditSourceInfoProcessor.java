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

import com.epam.digital.data.platform.soapapi.core.util.Header;
import com.epam.digital.data.platform.soapapi.core.util.SoapHeaders;
import com.epam.digital.data.platform.starter.audit.model.AuditSourceInfo;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Component
public class MdcAuditSourceInfoProcessor implements AuditSourceInfoProcessor {

  @Override
  public void initSourceInfo(SoapHeaders soapHeaders) {
    MDC.put(Header.X_SOURCE_SYSTEM, soapHeaders.getSourceSystem());
    MDC.put(Header.X_SOURCE_APPLICATION, soapHeaders.getSourceApplication());
    MDC.put(Header.X_SOURCE_BUSINESS_PROCESS, soapHeaders.getSourceBusinessProcess());
    MDC.put(
        Header.X_SOURCE_BUSINESS_PROCESS_INSTANCE_ID,
        soapHeaders.getSourceBusinessProcessInstanceId());
    MDC.put(
        Header.X_SOURCE_BUSINESS_PROCESS_DEFINITION_ID,
        soapHeaders.getSourceBusinessProcessDefinitionId());
    MDC.put(Header.X_SOURCE_BUSINESS_ACTIVITY, soapHeaders.getSourceBusinessActivity());
    MDC.put(
        Header.X_SOURCE_BUSINESS_ACTIVITY_INSTANCE_ID,
        soapHeaders.getSourceBusinessActivityInstanceId());
  }

  @Override
  public AuditSourceInfo getAuditSourceInfo() {
    return AuditSourceInfo.AuditSourceInfoBuilder.anAuditSourceInfo()
        .system(MDC.get(Header.X_SOURCE_SYSTEM))
        .application(MDC.get(Header.X_SOURCE_APPLICATION))
        .businessProcess(MDC.get(Header.X_SOURCE_BUSINESS_PROCESS))
        .businessProcessDefinitionId(MDC.get(Header.X_SOURCE_BUSINESS_PROCESS_DEFINITION_ID))
        .businessProcessInstanceId(MDC.get(Header.X_SOURCE_BUSINESS_PROCESS_INSTANCE_ID))
        .businessActivity(MDC.get(Header.X_SOURCE_BUSINESS_ACTIVITY))
        .businessActivityInstanceId(MDC.get(Header.X_SOURCE_BUSINESS_ACTIVITY_INSTANCE_ID))
        .build();
  }
}
