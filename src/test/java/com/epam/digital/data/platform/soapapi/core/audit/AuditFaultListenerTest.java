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

import static org.mockito.Mockito.verify;

import java.util.ResourceBundle;
import javax.xml.bind.UnmarshalException;
import org.apache.cxf.common.i18n.Message;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.MessageImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AuditFaultListenerTest {

  private AuditFaultListener auditFaultListener;

  @Mock
  private SoapAuditFacade soapAuditFacade;

  @BeforeEach
  void beforeEach() {
    auditFaultListener = new AuditFaultListener(soapAuditFacade);
  }

  @Test
  void expectFaultIsAudited() {
    auditFaultListener.faultOccurred(
        new Fault(new Message("", (ResourceBundle) null), new UnmarshalException("")),
        "",
        new MessageImpl());

    verify(soapAuditFacade).auditExceptionEvent("javax.xml.bind.UnmarshalException");
  }
}
