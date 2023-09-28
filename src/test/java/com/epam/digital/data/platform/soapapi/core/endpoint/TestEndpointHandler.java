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

package com.epam.digital.data.platform.soapapi.core.endpoint;

import com.epam.digital.data.platform.model.core.file.FileResponseDto;
import com.epam.digital.data.platform.soapapi.core.converter.HeadersProvider;
import com.epam.digital.data.platform.soapapi.core.restclients.TestRestApiClient;
import com.epam.digital.data.platform.soapapi.core.util.SoapHeaders;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public class TestEndpointHandler implements IEndpointHandler {

  private final TestRestApiClient restApiClient;
  private final HeadersProvider headersProvider;

  public TestEndpointHandler(TestRestApiClient restApiClient, HeadersProvider headersProvider) {
    this.restApiClient = restApiClient;
    this.headersProvider = headersProvider;
  }

  public List<Object> search(
      @WebParam(name = "searchConditions") Object searchConditions,
      @WebParam(header = true, name = "headers") SoapHeaders headers) {
    Map<String, Object> headersMap = headersProvider.createHttpHeaders(headers);
    return restApiClient.search(searchConditions, headersMap);
  }

  public FileResponseDto readFile(
          @WebParam(name = "id") UUID id,
          @WebParam(name = "fileId") String fileId,
          @WebParam(header = true, name = "headers") SoapHeaders headers) {
    Map<String, Object> headersMap = headersProvider.createHttpHeaders(headers);
    return restApiClient.readFile(id, fileId, headersMap);
  }
}
