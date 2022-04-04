package study.redis.redistokenstoreproject.infrastructure.util;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class RedisTemplateUtil {

    /**
     * Redis 데이터 타입을 Serialize / Deserialize 해주는 인터페이스
     * [데이터 타입 : 메서드]
     * String : opsForValue
     * List : opsForList
     * Set : opsForSet
     * ZSet : opsForZSet
     * Hash : opsForHash
     */
    private final StringRedisTemplate stringRedisTemplate;

    /**
     * Redis에서 String 데이터 가져오기
     * @param key : 가져올 데이터의 key
     * @return String : 가져온 데이터의 value
     */
    public String findStringData(String key) {

        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        return valueOperations.get(key);
    }

    /**
     * Redis에 String 데이터 저장하기
     * @param key : 저장할 데이터의 key
     * @param value : 저장할 데이터의 value
     */
    public void saveStringData(String key, String value) {

        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        valueOperations.set(key, value);
    }

    /**
     * Redis에 만료 시간이 있는 String 데이터 저장하기
     * @param key : 저장할 데이터의 key
     * @param value : 저장할 데이터의 value
     * @param duration : 저장할 데이터의 지속 시간
     */
    public void saveStringAndExpirationData(String key, String value, long duration) {

        Duration expireDuration = Duration.ofSeconds(duration);

        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        valueOperations.set(key, value, expireDuration);
    }

    /**
     * Redis에서 데이터 삭제하기
     * @param key : 삭제할 데이터의 key
     */
    public void deleteData(String key) {

        stringRedisTemplate.delete(key);
    }
}
