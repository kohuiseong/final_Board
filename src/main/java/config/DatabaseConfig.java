package config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

@Configuration // 스프링은 @Configuration이 선언된 클래스를 자바 기반의 설정 파일로 인식 한다.
@PropertySource("classpath:/applicaition.properties") // 해당 클래스에서의 참조할 properties의 경로를 선언 한다.
public class DatabaseConfig {

    @Autowired // Bean 으로 등록된 인스턴스를 클래스에 주입하는데 사용한다. 나중에는 Lombok 이라는 라이브러리를 이용해 스프링에서 권장하는 생성자 주입 방식을 사용

    // ApplicationContext
    // 스프링 컨테이너중 하나, 컨테이너는 사전적 의미로 무언가를 담는 용기 또는 그릇을 의미
    // 스프링 컨테이너는 빈의 생성과 사용, 관계 생명주기 등을 관리한다.
    // 빈은 쉽게 설명해서 자바 객체 이다. 예를 들어 프로젝트에 100개의 클래스가 있다고 가정하면 클래스들이 서로에 대한 의존성이 높다고 했을때 결합도가 높다
    // 라는 표현을 한다. 이러한 문제를 컨테이너에서 빈을 주입 받는 방법으로 해결 할 수 있다. 즉 클래스간의 의존성을 낮출 수 있다.
    private ApplicationContext context;

    // @Bean
    // Configration 클래스의 메서드 레벨에만 선언이 가능
    // Bean이 선언된 객체는 스프링 컨테이너에 의해 관리되는 Bean 으로 등록된다.
    // 해당 어노테이션은 인자로 몇 가지 속성을 지정 할 수 있다.

    // @Configuration
    // 해당 어노테이션은 인자에 prefix 속성을 선언 할 수 있다. prefix는 접두사, 즉 머리를 의미 한다.
    // prefix에 spring.datasource.hikari를 선언했다 -> @PropertySource에 선언된 파일(application.properties)에서 prefix에 해당하는
    // spring.datasource.hikari로 시작하는 설정을 모두 읽어 해당 메서드에 매칭 하는 개념

    // hikariConfig
    // 히카리 CP 객체를 생성한다. 히카리 CP는 커넥션풀 라이브러리 중 하나
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    public HikariConfig hikariConfig() {
        return new HikariConfig();
    }

    // 데이터 소스 객체를 생성
    // 순수 JDBC는 SQL을 실행 할 떄마다 커넥션을 맺고 끊는 I/O 작업을 하는데, 이 작업은 상당한 양의 리소스를 잡아 먹는다
    // 커넥션 풀은 커넥션 객체를 생성해두고 DB에 접근 하는 사용자에게 미리 생성 해둔 커넥션을 제공 했다가 다시 돌려받는 방법
    // datasource 는 커넥션 풀을 지원하기 위한 인터페이스 이다.
    @Bean
    public DataSource dataSource() {
        return new HikariDataSource(hikariConfig());
    }

    // SqlSession
    // SqlSessionFactoryBean은 FactoryBean는 DB 커넥션과 SQL 실행에 대한 모든 것을 갖는 객체이다.
    // SqlSessionFactoryBean 은 FactoryBean 인터페이스의 구현 클래스로, MyBatis와 스프링의 연동 모듈로 사용 된다.
    // 쉽게 말해 factoryBean 객체는 데이터 소스를 참조하며, XML Mapper(SQL 쿼리 작성 파일)의 경로와 설정 파일 경로 등 정보를 갖는 객체이다.
    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource());
        return factoryBean.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSession() throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory());
    }
}

/**
 * sqlSession
 * 1. SqlSessionTemplate 은 마이바티스 스프링 연동 모듈의 핵심이다.
 * 2. SqlSessionTemplate 은 SqlSession을 구현 하고 코드에서 SqlSession을 대체한다.
 * 3. SqlSessionTemplate은 쓰레드에 안전하고, 여러 개의 DAO나 Mapper에서 공유 할 수 있다.
 * 4. 필요한 시점에서 세션을 닫고, 커밋 또는 롤백 하는 것을 포함한 세션의 생명주기를 관리한다.
 */
