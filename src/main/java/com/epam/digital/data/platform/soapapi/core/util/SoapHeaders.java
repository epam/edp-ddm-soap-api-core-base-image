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

package com.epam.digital.data.platform.soapapi.core.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@JsonInclude(JsonInclude.Include.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
public class SoapHeaders {
  @XmlElement(name = Header.X_ACCESS_TOKEN)
  @JsonProperty(Header.X_ACCESS_TOKEN)
  private String accessToken;

  @XmlElement(name = Header.X_DIGITAL_SIGNATURE)
  @JsonProperty(Header.X_DIGITAL_SIGNATURE)
  private String digitalSignature;

  @XmlElement(name = Header.X_DIGITAL_SIGNATURE_DERIVED)
  @JsonProperty(Header.X_DIGITAL_SIGNATURE_DERIVED)
  private String digitalSignatureDerived;

  @XmlElement(name = Header.X_SOURCE_SYSTEM)
  @JsonProperty(Header.X_SOURCE_SYSTEM)
  private String sourceSystem;

  @XmlElement(name = Header.X_SOURCE_APPLICATION)
  @JsonProperty(Header.X_SOURCE_APPLICATION)
  private String sourceApplication;

  @XmlElement(name = Header.X_SOURCE_BUSINESS_PROCESS)
  @JsonProperty(Header.X_SOURCE_BUSINESS_PROCESS)
  private String sourceBusinessProcess;

  @XmlElement(name = Header.X_SOURCE_BUSINESS_PROCESS_INSTANCE_ID)
  @JsonProperty(Header.X_SOURCE_BUSINESS_PROCESS_INSTANCE_ID)
  private String sourceBusinessProcessInstanceId;

  @XmlElement(name = Header.X_SOURCE_BUSINESS_PROCESS_DEFINITION_ID)
  @JsonProperty(Header.X_SOURCE_BUSINESS_PROCESS_DEFINITION_ID)
  private String sourceBusinessProcessDefinitionId;

  @XmlElement(name = Header.X_SOURCE_BUSINESS_ACTIVITY)
  @JsonProperty(Header.X_SOURCE_BUSINESS_ACTIVITY)
  private String sourceBusinessActivity;

  @XmlElement(name = Header.X_SOURCE_BUSINESS_ACTIVITY_INSTANCE_ID)
  @JsonProperty(Header.X_SOURCE_BUSINESS_ACTIVITY_INSTANCE_ID)
  private String sourceBusinessActivityInstanceId;

  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  public String getDigitalSignature() {
    return digitalSignature;
  }

  public void setDigitalSignature(String digitalSignature) {
    this.digitalSignature = digitalSignature;
  }

  public String getDigitalSignatureDerived() {
    return digitalSignatureDerived;
  }

  public void setDigitalSignatureDerived(String digitalSignatureDerived) {
    this.digitalSignatureDerived = digitalSignatureDerived;
  }

  public String getSourceSystem() {
    return sourceSystem;
  }

  public void setSourceSystem(String sourceSystem) {
    this.sourceSystem = sourceSystem;
  }

  public String getSourceApplication() {
    return sourceApplication;
  }

  public void setSourceApplication(String sourceApplication) {
    this.sourceApplication = sourceApplication;
  }

  public String getSourceBusinessProcess() {
    return sourceBusinessProcess;
  }

  public void setSourceBusinessProcess(String sourceBusinessProcess) {
    this.sourceBusinessProcess = sourceBusinessProcess;
  }

  public String getSourceBusinessProcessInstanceId() {
    return sourceBusinessProcessInstanceId;
  }

  public void setSourceBusinessProcessInstanceId(String sourceBusinessProcessInstanceId) {
    this.sourceBusinessProcessInstanceId = sourceBusinessProcessInstanceId;
  }

  public String getSourceBusinessProcessDefinitionId() {
    return sourceBusinessProcessDefinitionId;
  }

  public void setSourceBusinessProcessDefinitionId(String sourceBusinessProcessDefinitionId) {
    this.sourceBusinessProcessDefinitionId = sourceBusinessProcessDefinitionId;
  }

  public String getSourceBusinessActivity() {
    return sourceBusinessActivity;
  }

  public void setSourceBusinessActivity(String sourceBusinessActivity) {
    this.sourceBusinessActivity = sourceBusinessActivity;
  }

  public String getSourceBusinessActivityInstanceId() {
    return sourceBusinessActivityInstanceId;
  }

  public void setSourceBusinessActivityInstanceId(String sourceBusinessActivityInstanceId) {
    this.sourceBusinessActivityInstanceId = sourceBusinessActivityInstanceId;
  }
}
