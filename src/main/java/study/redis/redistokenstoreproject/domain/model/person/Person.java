package study.redis.redistokenstoreproject.domain.model.person;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

// value : Redis의 keyspace 값으로 사용된다. timeToLive : 만료 시간을 seconds 단위로 설정할 수 있다. 기본값은 만료 시간이 없는 -1L이다.
@RedisHash(value = "people", timeToLive = 5)
@Getter
@Builder
public class Person {

    @Id // Redis Key값이 고유번호로 사용되며, null로 세팅하면 랜덤값이 설정된다. keyspace와 합쳐서 Redis에 저장된 최종 키 값은 keyspace:id가 된다.
    private Long id;

    @Indexed // findBy를 통해 해당 값으로 데이터를 조회하기 위해서 Indexed 어노테이션을 사용한다.
    private String name;

    private Integer age;
}
