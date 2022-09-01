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

import static com.epam.digital.data.platform.soapapi.core.audit.AuditTestUtil.BUSINESS_ACTIVITY;
import static com.epam.digital.data.platform.soapapi.core.audit.AuditTestUtil.BUSINESS_ACTIVITY_INSTANCE_ID;
import static com.epam.digital.data.platform.soapapi.core.audit.AuditTestUtil.BUSINESS_PROCESS;
import static com.epam.digital.data.platform.soapapi.core.audit.AuditTestUtil.BUSINESS_PROCESS_DEFINITION_ID;
import static com.epam.digital.data.platform.soapapi.core.audit.AuditTestUtil.BUSINESS_PROCESS_INSTANCE_ID;
import static com.epam.digital.data.platform.soapapi.core.audit.AuditTestUtil.SOURCE_APPLICATION;
import static com.epam.digital.data.platform.soapapi.core.audit.AuditTestUtil.SOURCE_SYSTEM;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.epam.digital.data.platform.soapapi.core.service.TraceProvider;
import com.epam.digital.data.platform.starter.audit.model.AuditEvent;
import com.epam.digital.data.platform.starter.audit.model.AuditSourceInfo;
import com.epam.digital.data.platform.starter.audit.model.EventType;
import com.epam.digital.data.platform.starter.audit.service.AuditService;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SoapAuditFacadeTest {

  private static final LocalDateTime CURR_TIME = LocalDateTime.of(2021, 4, 1, 11, 50);
  private static final String APP_NAME = "appName";
  private static final String METHOD_NAME = "method";
  private static final String ACTION = "read";
  private static final String STEP = "before";
  private static final String REQUEST_ID = "1";

  private static final Clock clock =
      Clock.fixed(CURR_TIME.atZone(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());

  private SoapAuditFacade soapAuditFacade;

  @Mock
  private AuditService auditService;
  @Mock
  private AuditSourceInfoProcessor auditSourceInfoProcessor;
  @Mock
  private TraceProvider traceProvider;

  private AuditSourceInfo mockSourceInfo;

  @BeforeEach
  void beforeEach() {
    soapAuditFacade =
        new SoapAuditFacade(auditService, APP_NAME, clock, traceProvider, auditSourceInfoProcessor);

    when(traceProvider.getRequestId()).thenReturn(REQUEST_ID);
    mockSourceInfo =
        AuditSourceInfo.AuditSourceInfoBuilder.anAuditSourceInfo()
            .system(SOURCE_SYSTEM)
            .application(SOURCE_APPLICATION)
            .businessProcess(BUSINESS_PROCESS)
            .businessProcessDefinitionId(BUSINESS_PROCESS_DEFINITION_ID)
            .businessProcessInstanceId(BUSINESS_PROCESS_INSTANCE_ID)
            .businessActivity(BUSINESS_ACTIVITY)
            .businessActivityInstanceId(BUSINESS_ACTIVITY_INSTANCE_ID)
            .build();
    when(auditSourceInfoProcessor.getAuditSourceInfo())
            .thenReturn(mockSourceInfo);
  }

  @Test
  void expectRetrieveRequestIsAudited() {
    Map<String, Object> mockContext = Collections.singletonMap("key", "value");
    when(auditService.createContext(ACTION, STEP, null, null, null, null))
            .thenReturn(mockContext);

    soapAuditFacade.auditSoapRetrieveRequest(METHOD_NAME, ACTION, STEP);

    verify(auditService).createContext(ACTION, STEP, null, null, null, null);
    ArgumentCaptor<AuditEvent> auditEventCaptor = ArgumentCaptor.forClass(AuditEvent.class);
    verify(auditService).sendAudit(auditEventCaptor.capture());

    AuditEvent actualEvent = auditEventCaptor.getValue();

    var expectedEvent = AuditEvent.AuditEventBuilder.anAuditEvent()
            .application(APP_NAME)
            .name("SOAP request. Method: method")
            .requestId(REQUEST_ID)
            .sourceInfo(mockSourceInfo)
            .userInfo(null)
            .currentTime(clock.millis())
            .eventType(EventType.USER_ACTION)
            .context(mockContext)
            .build();

    assertThat(actualEvent).usingRecursiveComparison().isEqualTo(expectedEvent);
  }

  @Test
  void expectSoapExceptionIsAudited() {
    Map<String, Object> mockContext = Collections.singletonMap("key", "value");
    when(auditService.createContext(ACTION, null, null, null, null, null))
            .thenReturn(mockContext);

    soapAuditFacade.auditExceptionEvent(ACTION);

    verify(auditService).createContext(ACTION, null, null, null, null, null);
    ArgumentCaptor<AuditEvent> auditEventCaptor = ArgumentCaptor.forClass(AuditEvent.class);
    verify(auditService).sendAudit(auditEventCaptor.capture());

    AuditEvent actualEvent = auditEventCaptor.getValue();

    var expectedEvent =
        AuditEvent.AuditEventBuilder.anAuditEvent()
            .application(APP_NAME)
            .name("EXCEPTION")
            .requestId(REQUEST_ID)
            .sourceInfo(mockSourceInfo)
            .userInfo(null)
            .currentTime(clock.millis())
            .eventType(EventType.USER_ACTION)
            .context(mockContext)
            .build();

    assertThat(actualEvent).usingRecursiveComparison().isEqualTo(expectedEvent);
  }
}
