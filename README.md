### ch01 ~ ch09 REVIEW HOME PROJECT 
1. 셔비스를 실행 가능한 형태로 설정(packaging jar 파일 생성)
1. 각 챕터별 테스트 가이드 확인 & 프로세스 확인
1. 프로세스 플로우 도중 사용된 개념은 분석 & 이해

### ch02
1. 유효성을 검사할 클래스에 검사 규칙을 선언
1. 유효성 검사를 수행하는 컨트롤러 메서드에 검사를 수행한다는 것을 지정

### CH10
1. add reactive programming 
1. test reacter 

### VCN
* multi modules - built
	1. vcn-api
	1. vcn-domain	
	1. vcn-service
	1. vcn-security
	1. vcn-web
	1. vcn
* multi modules build
	1. 프로젝트의 전체 모듈을 하나의 애플리케이션으로 빌드
	1. clean package > war 파일을 생성
	1. SpringBootApplication 이 포함된 폴더의 war파일로 서버 기동 
* 업무망 진행

### 리액티브 프로그래밍
* pom에 사용할 웹 프레임워크를 선택하여 의존성 하나만 추가해야 함 
* 스프링 MVC 
	* spring-boot-starter-web
* 스프링 WebFlux
	* spring-boot-starter-webflux

스프링MVC, WebFlux 혼용 관련 게시글 
https://mangkyu.tistory.com/257
