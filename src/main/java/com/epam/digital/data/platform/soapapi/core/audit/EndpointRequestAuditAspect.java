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

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import com.epam.digital.data.platform.soapapi.core.util.SoapHeaders;


@Aspect
@Component
public class EndpointRequestAuditAspect {

  // action
  private static final String SEARCH = "SEARCH ENTITY";

  // step
  private static final String BEFORE = "BEFORE";
  private static final String AFTER = "AFTER";

  private final SoapAuditFacade soapAuditFacade;
  private final AuditSourceInfoProcessor auditSourceInfoProcessor;

  public EndpointRequestAuditAspect(SoapAuditFacade soapAuditFacade,
                                    AuditSourceInfoProcessor auditSourceInfoProcessor) {
    this.soapAuditFacade = soapAuditFacade;
    this.auditSourceInfoProcessor = auditSourceInfoProcessor;
  }

  @Pointcut("@within(javax.jws.WebService)")
  public void withinWebServicePointcut() {}

  @Around("withinWebServicePointcut() && args(searchConditions, headers)")
  Object auditReadRequest(ProceedingJoinPoint joinPoint, Object searchConditions, SoapHeaders headers)
      throws Throwable {
    auditSourceInfoProcessor.initSourceInfo(headers);

    String methodName = joinPoint.getSignature().getName();

    soapAuditFacade.auditSoapRetrieveRequest(methodName, SEARCH, BEFORE);
    Object result = joinPoint.proceed();
    soapAuditFacade.auditSoapRetrieveRequest(methodName, SEARCH, AFTER);
    return result;
  }
}
