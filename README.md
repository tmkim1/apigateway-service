# apigateway-service
SpringCloudGateway

#API GATEWAY 구현 
- 여러 개의 서비스는 각각 다른 port와 다른 URI를 가짐
- 사용자 측에서 3개의 Service 호출이 필요한 경우 각각 다른 uri에 요청하는 것은 매우 번거로움 
- 따라서 Client 측은 API GATEWAY 쪽에만 필요한 서비스에 요청을 보내면 API GATEWAY가 해당 서비스를 호출 해주도록 구성 
  .이러한 환경 구축에 따라 인증, 권한 부여, 서비스 검색 통합, 부하 분산 등에 장점이 생김 
  
#Spring Cloude Server 
- Tomcat이 아닌 Netty라는 비동기 서버가 작동 됨


                                -> fisrtService
Client -> Spring Cloud GateWay 
                                -> secondService
                                
Spring Cloud Gateway 
- predicate: 조건 분기 (Client 요청에 따라 predicate에 정의된 조건에 맞는 service를 호출한다. 
  <details>
  <summary>predicate 형태</summary>
  <pre>
  1. yml파일 설정인 경우
  <code>
  spring:
   application:
     name: apigateway-service
   cloud:
     gateway:
       routes:
         - id: first-service
           uri: http://localhost:8081/
           predicates:
             - Path=/first-service/**
         - id: second-service
           uri: http://localhost:8082/
           predicates:
             - Path=/second-service/**
  </code>
  </pre>
  
  <pre>
  2. JAVA Code로 설정하는 경우
  <code>
  @Configuration
  public class FilterConfig {
      // r -> r.path("/first-service/**" 해당 관련 요청이 들어오면
      // .uri("http://localhost:8081") 설정한 uri 호출

      @Bean
      public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
          return builder.routes()
                  .route(r -> r.path("/first-service/**")
                              .filters(f -> f.addRequestHeader("first-request","first-request-header")
                                             .addResponseHeader("first-response","first-response-header"))
                              .uri("http://localhost:8081"))
                  .route(r -> r.path("/second-service/**")
                          .filters(f -> f.addRequestHeader("second-request","second-request-header")
                                  .addResponseHeader("second-response","second-response-header"))
                          .uri("http://localhost:8082"))
                  .build();
      }
  }
  </code>
  </pre>
  </details>
  
   



