# demo-spring-cloud

스프링 클라우드 마이크로서비스 데모용 프로젝트 입니다. 멀티모듈로 구성되어 있으며 구성된 프로젝트들은 다음과 같습니다.

- eureka-server
- config-service
- gateway
- user-service
- order-service
- catalog-service

## 프로젝트 모듈 소개

### eureka-server

마이크로서비스 디스커버리 서버 입니다. 마이크로서비스를 관장하는 서버 입니다.

### config-service

원격 환경설정 서비스 입니다. 수 많은 마이크로 서비스들의 환경설정을 원격으로 관리하며 동적으로 실시간으로 관리할 수 있습니다.

### gateway

각 마이크로 서비스를 분배하는 역할을 수행 합니다.

### user-service

회원 관련 마이크로 서비스 입니다.

### order-service

주문 관련 마이크로 서비스 입니다.

### catalog-service

카탈로그 관련 마이크로 서비스 입니다.