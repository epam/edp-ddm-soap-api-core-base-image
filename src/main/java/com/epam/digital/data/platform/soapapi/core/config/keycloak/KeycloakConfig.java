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

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.token.TokenManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakConfig {

  private final KeycloakProperties keycloakProperties;

  public KeycloakConfig(KeycloakProperties keycloakProperties) {
    this.keycloakProperties = keycloakProperties;
  }

  @Bean
  public Keycloak serviceAccountKeycloakConfig() {
    return KeycloakBuilder.builder()
            .serverUrl(keycloakProperties.getServerUrl())
            .realm(keycloakProperties.getServiceAccount().getRealm())
            .clientId(keycloakProperties.getServiceAccount().getClientId())
            .clientSecret(keycloakProperties.getServiceAccount().getClientSecret())
            .grantType(keycloakProperties.getServiceAccount().getGrantType())
            .username(keycloakProperties.getServiceAccount().getUsername())
            .password(keycloakProperties.getServiceAccount().getPassword())
            .build();
  }

  @Bean
  public TokenManager serviceAccountTokenManager(Keycloak serviceAccountKeycloakConfig) {
    return serviceAccountKeycloakConfig.tokenManager();
  }
}
