package io.onhigh.bot.secretary.gateway.tg.repository;

import io.onhigh.bot.secretary.gateway.tg.model.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person,Long> {
}
