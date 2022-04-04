package study.redis.redistokenstoreproject.infrastructure.dao;

import org.springframework.stereotype.Component;
import study.redis.redistokenstoreproject.domain.model.person.Person;
import study.redis.redistokenstoreproject.domain.model.person.PersonFactory;

@Component
public class PersonFactoryImpl implements PersonFactory {

    @Override
    public Person createPerson(String name, Integer age) {

        if (name == null || name.trim().equals("")) throw new RuntimeException("이름을 써 주세요.");
        if (age != null && age < 0) throw new RuntimeException("나이의 값이 올바르지 않습니다.");

        return Person.builder()
            .name(name)
            .age(age)
            .build();
    }
}
