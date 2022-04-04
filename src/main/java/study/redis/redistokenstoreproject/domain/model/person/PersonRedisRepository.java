package study.redis.redistokenstoreproject.domain.model.person;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PersonRedisRepository extends CrudRepository<Person, Long> {

    Optional<Person> findByName(String name);
}
