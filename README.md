# apigateway-service
SpringCloudGateway

#API GATEWAY 구현 
- 여러 개의 서비스는 각각 다른 Port와 다른 uri를 가짐
- 사용자 측에서 3개의 Service 호출이 필요한 경우 각각 다른 uri에 요청하는 것은 매우 번거로움 
- 따라서 Client 측은 API GATEWAY 쪽에만 필요한 서비스에 요청을 보내면 API GATEWAY가 해당 서비스를 호출 해주도록 구성 
  .이러한 환경 구축에 따라 인증, 권한 부여, 서비스 검색 통합, 부하 분산 등에 장점이 생김 
  
#Spring Cloude Server 
- tomcat이 아닌 Netty라는 비동기 서버가 작동 됨
