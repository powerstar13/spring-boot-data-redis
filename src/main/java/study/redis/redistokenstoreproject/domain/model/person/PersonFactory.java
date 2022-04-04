package study.redis.redistokenstoreproject.domain.model.person;

public interface PersonFactory {

    Person createPerson(String name, Integer age);
}
