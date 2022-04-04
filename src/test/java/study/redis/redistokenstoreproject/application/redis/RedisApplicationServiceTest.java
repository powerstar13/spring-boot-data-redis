package study.redis.redistokenstoreproject.application.redis;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import study.redis.redistokenstoreproject.application.redis.dto.PersonDTO;
import study.redis.redistokenstoreproject.application.redis.dto.RedisStringAndExpirationDataDTO;
import study.redis.redistokenstoreproject.application.redis.dto.RedisStringDataDTO;
import study.redis.redistokenstoreproject.domain.model.person.Person;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RedisApplicationServiceTest {

    @Autowired
    private RedisApplicationService redisApplicationService;

    @Test
    @Order(1)
    void String데이터_저장_후_조회하여_검증() {

        String key = "myKey";
        String value = "myStringValue";

        RedisStringDataDTO redisStringDataDTO = RedisStringDataDTO.builder()
            .key(key)
            .value(value)
            .build();

        redisApplicationService.saveStringData(redisStringDataDTO);

        String savedValue = redisApplicationService.findStringData(key);

        assertEquals(value, savedValue);
    }

    @Test
    @Order(2)
    void 만료시간을_포함하여_String데이터_저장_후_조회하여_검증() throws InterruptedException {

        String key = "myExpirationKey";
        String value = "myStringValue";
        long duration = 5;

        RedisStringAndExpirationDataDTO redisStringAndExpirationDataDTO = RedisStringAndExpirationDataDTO.builder()
            .key(key)
            .value(value)
            .duration(duration)
            .build();

        redisApplicationService.saveStringAndExpirationData(redisStringAndExpirationDataDTO);

        String savedValue = redisApplicationService.findStringData(key);

        assertEquals(value, savedValue);

        Thread.sleep(5000);

        String foundValue = redisApplicationService.findStringData(key);

        assertNull(foundValue);
    }

    @Test
    @Order(3)
    void 저장된_String데이터_삭제하고_검증() {

        String key = "myKey";

        String foundValue = redisApplicationService.findStringData(key);

        assertNotNull(foundValue);

        redisApplicationService.deleteData(key);

        String verifyFoundValue = redisApplicationService.findStringData(key);

        assertNull(verifyFoundValue);
    }

    @Test
    @Order(4)
    void Person데이터_저장_후_고유번호로_조회해보고_이름으로도_조회하여_검증() {

        String personName = "홍준성";
        Integer personAge = 32;

        PersonDTO personDTO = PersonDTO.builder()
            .name(personName)
            .age(personAge)
            .build();

        Person person = redisApplicationService.savePersonData(personDTO);

        Person foundPersonById = redisApplicationService.findById(person.getId());
        Person foundPersonByName = redisApplicationService.findByName(personName);

        assertEquals(personName, foundPersonById.getName());
        assertEquals(personAge, foundPersonById.getAge());
        assertEquals(personName, foundPersonByName.getName());
        assertEquals(personAge, foundPersonByName.getAge());
    }

    @Test
    @Order(5)
    void 저장된_Person데이터_삭제하고_검증() {

        String personName = "홍준성";

        Person foundPersonByName = redisApplicationService.findByName(personName);

        assertNotNull(foundPersonByName);

        redisApplicationService.deletePersonData(foundPersonByName);

        Person verifyDeletePerson = redisApplicationService.findByName(personName);

        assertNull(verifyDeletePerson);
    }
}