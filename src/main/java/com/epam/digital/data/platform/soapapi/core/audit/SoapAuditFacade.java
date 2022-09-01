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

import com.epam.digital.data.platform.soapapi.core.service.TraceProvider;
import com.epam.digital.data.platform.starter.audit.model.EventType;
import com.epam.digital.data.platform.starter.audit.service.AbstractAuditFacade;
import com.epam.digital.data.platform.starter.audit.service.AuditService;
import java.time.Clock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SoapAuditFacade extends AbstractAuditFacade {

  private final Logger log = LoggerFactory.getLogger(SoapAuditFacade.class);

  private static final String SOAP_REQUEST = "SOAP request. Method: ";
  private static final String EXCEPTION = "EXCEPTION";

  private final TraceProvider traceProvider;
  private final AuditSourceInfoProcessor auditSourceInfoProcessor;

  public SoapAuditFacade(
      AuditService auditService,
      @Value("${spring.application.name:soap-api}") String appName,
      Clock clock,
      TraceProvider traceProvider,
      AuditSourceInfoProcessor auditSourceInfoProcessor) {
    super(auditService, appName, clock);
    this.traceProvider = traceProvider;
    this.auditSourceInfoProcessor = auditSourceInfoProcessor;
  }

  public void auditSoapRetrieveRequest(String methodName, String action, String step) {
    var event =
        createBaseAuditEvent(
                EventType.USER_ACTION, SOAP_REQUEST + methodName, traceProvider.getRequestId())
            .setSourceInfo(auditSourceInfoProcessor.getAuditSourceInfo());

    var context = auditService.createContext(action, step, null, null, null, null);
    event.setContext(context);

    log.info("Sending {} {} event to Audit", step, action);
    auditService.sendAudit(event.build());
  }

  public void auditExceptionEvent(String action) {
    var event =
        createBaseAuditEvent(EventType.USER_ACTION, EXCEPTION, traceProvider.getRequestId())
            .setSourceInfo(auditSourceInfoProcessor.getAuditSourceInfo());

    var context = auditService.createContext(action, null, null, null, null, null);
    event.setContext(context);

    log.info("Sending Exception event to Audit");
    auditService.sendAudit(event.build());
  }
}
