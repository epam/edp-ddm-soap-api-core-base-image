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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import feign.codec.EncodeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JacksonFormattedQueryMapEncoderTest {

  private final ObjectMapper objectMapper = new ObjectMapper();
  private JacksonFormattedQueryMapEncoder jacksonFormattedQueryMapEncoder;

  @BeforeEach
  void beforeEach() {
    jacksonFormattedQueryMapEncoder = new JacksonFormattedQueryMapEncoder(objectMapper);
  }

  @Test
  void shouldRemoveQueryParamsWithNullValue() {
    var paramsBefore = Maps.newHashMap();
    paramsBefore.put("firstName", null);
    paramsBefore.put("limit", "10");
    paramsBefore.put("lastName", null);

    var paramsAfter = jacksonFormattedQueryMapEncoder.encode(paramsBefore);

    assertThat(paramsAfter).hasSize(1).containsEntry("limit", "10");
  }

  @Test
  void shouldGetEncodingErrorWhenConvertListToMap() {
    var encodeException = assertThrows(EncodeException.class,
        () -> jacksonFormattedQueryMapEncoder.encode(Lists.newArrayList()));

    assertThat(encodeException.getMessage()).isEqualTo("Failure encoding object into query map");
  }
}