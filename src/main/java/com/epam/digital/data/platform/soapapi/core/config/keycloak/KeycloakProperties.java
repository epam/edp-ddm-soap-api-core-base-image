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

package com.epam.digital.data.platform.soapapi.core.config.keycloak;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("keycloak")
public class KeycloakProperties {

  private String serverUrl;
  private UserAuthorizationProperties serviceAccount;

  public String getServerUrl() {
    return serverUrl;
  }

  public void setServerUrl(String serverUrl) {
    this.serverUrl = serverUrl;
  }

  public UserAuthorizationProperties getServiceAccount() {
    return serviceAccount;
  }

  public void setServiceAccount(UserAuthorizationProperties serviceAccount) {
    this.serviceAccount = serviceAccount;
  }

  public static class UserAuthorizationProperties {
    private String realm;
    private String username;
    private String password;
    private String clientId;
    private String clientSecret;
    private String grantType;

    public String getRealm() {
      return realm;
    }

    public void setRealm(String realm) {
      this.realm = realm;
    }

    public String getUsername() {
      return username;
    }

    public void setUsername(String username) {
      this.username = username;
    }

    public String getPassword() {
      return password;
    }

    public void setPassword(String password) {
      this.password = password;
    }

    public String getClientId() {
      return clientId;
    }

    public void setClientId(String clientId) {
      this.clientId = clientId;
    }

    public String getClientSecret() {
      return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
      this.clientSecret = clientSecret;
    }

    public String getGrantType() {
      return grantType;
    }

    public void setGrantType(String grantType) {
      this.grantType = grantType;
    }
  }
}
