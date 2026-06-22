package com.ohgiraffers.lxp.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * JPA 감사(@CreatedDate/@LastModifiedDate) 활성화.
 *
 * <p>메인 애플리케이션 클래스가 아닌 전용 설정으로 분리한 이유: @EnableJpaAuditing이 메인 클래스에 있으면
 * JPA를 로드하지 않는 슬라이스 테스트(@WebMvcTest)가 auditing 빈 생성에서 "JPA metamodel must not be empty"로 실패한다.
 * 이 설정은 컴포넌트 스캔 경로에 있어 앱 전체 및 @SpringBootTest에는 그대로 적용되고, @WebMvcTest는 스캔하지 않는다.
 */
@Configuration
@EnableJpaAuditing
public class JpaAuditingConfig {
}
