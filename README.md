# soap-api-core

This library contains **all** the business logic of the Soap API microservice which is to be generated with `service-generation-utility`. The Soap API microservice, perfoming like a Proxy, is to accept SOAP requests, transform them to REST requests and send them to `Rest API` microservice.

# Related components
* `model-core`
* `Rest API` microservice

# Usage
* `@org.springframework.cloud.openfeign.FeignClient` to be introduced as a REST client
* `IEndpointHandler` to be implemented for accepting SOAP requests, marshalling and calling the mentioned REST client

# Deployment
The library is delivered as a docker image with all dependencies inside.

### License
soap-api-core is Open Source software released under the Apache 2.0 license.