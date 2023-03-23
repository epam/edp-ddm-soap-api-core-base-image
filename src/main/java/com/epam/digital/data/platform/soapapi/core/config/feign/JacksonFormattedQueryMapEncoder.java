/*
 * Copyright 2023 EPAM Systems.
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

package com.epam.digital.data.platform.soapapi.core.config.feign;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.QueryMapEncoder;
import feign.codec.EncodeException;

import java.util.Map;
import java.util.Objects;

class JacksonFormattedQueryMapEncoder implements QueryMapEncoder {

  private final ObjectMapper objectMapper;

  public JacksonFormattedQueryMapEncoder(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override
  public Map<String, Object> encode(Object o) {
    try {
      TypeReference<Map<String, Object>> reference = new TypeReference<>() {
      };
      var params = objectMapper.convertValue(o, reference);
      params.values().removeIf(Objects::isNull);
      return params;
    } catch (Exception e) {
      throw new EncodeException("Failure encoding object into query map", e);
    }
  }
}
