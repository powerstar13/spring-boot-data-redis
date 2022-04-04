package study.redis.redistokenstoreproject.application.redis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RedisStringAndExpirationDataDTO {

    private String key;
    private String value;
    private long duration;
}
