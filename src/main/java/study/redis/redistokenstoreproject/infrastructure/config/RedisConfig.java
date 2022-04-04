package study.redis.redistokenstoreproject.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@EnableRedisRepositories
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private int redisPort;

    /**
     * // Redis를 연결하기 위해 host와 port를 전달하여 인스턴스를 생성한다.
     *
     * @return RedisConnectionFactory : Redis 인스턴스 연결
     */
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {

        return new LettuceConnectionFactory(redisHost, redisPort);
    }

    /**
     * RedisTemplate을 사용하여 여러가지 원하는 데이터 타입을 간편한게 넣을 수 있다.
     * template을 선언한 후 원하는 타입에 맞는 Operations를 꺼내서 사용한다.
     * @return RedisTemplate : RedisTemplate 인스턴스 연결
     */
    @Bean
    public RedisTemplate<?, ?> redisTemplate() {

        RedisTemplate<byte[], byte[]> redisTemplate = new RedisTemplate<>();
        // RedisTemplate에 LettuceConnectionFactory를 적용해주기 위해 설정한다.
        redisTemplate.setConnectionFactory(this.redisConnectionFactory());

        return redisTemplate;
    }
}
