package study.redis.redistokenstoreproject.domain.model.person;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PersonFindSpecification {

    private final PersonRedisRepository personRedisRepository;

    /**
     * Redis에서 Person 데이터 가져오기
     * @param id : 가져올 데이터의 key
     * @return Person : 가져온 Person 데이터
     */
    public Person findById(Long id) {

        return personRedisRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Found Data > Person"));
    }

    /**
     * Redis에서 Person 데이터 가져오기
     * @param name : 가져올 데이터의 name
     * @return Person : 가져온 Person 데이터
     */
    public Person findByNameOrElseNull(String name) {

        return personRedisRepository.findByName(name).orElse(null);
    }
}
