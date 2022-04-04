package study.redis.redistokenstoreproject.application.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.redis.redistokenstoreproject.application.redis.dto.PersonDTO;
import study.redis.redistokenstoreproject.application.redis.dto.RedisStringAndExpirationDataDTO;
import study.redis.redistokenstoreproject.application.redis.dto.RedisStringDataDTO;
import study.redis.redistokenstoreproject.domain.model.person.Person;
import study.redis.redistokenstoreproject.domain.model.person.PersonFactory;
import study.redis.redistokenstoreproject.domain.model.person.PersonFindSpecification;
import study.redis.redistokenstoreproject.domain.model.person.PersonRedisRepository;
import study.redis.redistokenstoreproject.infrastructure.util.RedisTemplateUtil;

@Service
@RequiredArgsConstructor
public class RedisApplicationService {

    private final RedisTemplateUtil redisTemplateUtil;
    private final PersonRedisRepository personRedisRepository;
    private final PersonFactory personFactory;
    private final PersonFindSpecification personFindSpecification;

    /**
     * Redis에 String 데이터 저장하기
     * @param request : 저장할 데이터
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveStringData(RedisStringDataDTO request) {

        redisTemplateUtil.saveStringData(request.getKey(), request.getValue());
    }

    /**
     * Redis에 만료 시간이 있는 String 데이터 저장하기
     * @param request : 저장할 데이터
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveStringAndExpirationData(RedisStringAndExpirationDataDTO request) {

        redisTemplateUtil.saveStringAndExpirationData(request.getKey(), request.getValue(), request.getDuration());
    }

    /**
     * Redis에서 String 데이터 가져오기
     * @param key : 가져올 데이터의 key
     * @return String : 가져온 데이터의 value
     */
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public String findStringData(String key) {

        return redisTemplateUtil.findStringData(key);
    }

    /**
     * Redis에서 데이터 삭제하기
     * @param key : 삭제할 데이터의 key
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteData(String key) {

        redisTemplateUtil.deleteData(key);
    }

    /**
     * Redis에 Person 데이터 저장하기
     * @param request : 저장할 데이터
     * @return Person : 저장된 Person 데이터
     */
    @Transactional(rollbackFor = Exception.class)
    public Person savePersonData(PersonDTO request) {

        Person person = personFactory.createPerson(request.getName(), request.getAge()); // 생성할 Person 데이터 준비

        return personRedisRepository.save(person);
    }

    /**
     * Redis에서 Person 데이터 가져오기
     * @param id : 가져올 데이터의 key
     * @return Person : 가져온 Person 데이터
     */
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Person findById(Long id) {

        return personFindSpecification.findById(id);
    }

    /**
     * Redis에서 Person 데이터 가져오기
     * @param name : 가져올 데이터의 name
     * @return Person : 가져온 Person 데이터
     */
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Person findByName(String name) {

        return personFindSpecification.findByNameOrElseNull(name);
    }

    /**
     * Redis에서 Person 데이터 삭제하기
     * @param person : 삭제할 person 데이터
     */
    @Transactional(rollbackFor = Exception.class)
    public void deletePersonData(Person person) {

        personRedisRepository.delete(person);
    }
}
