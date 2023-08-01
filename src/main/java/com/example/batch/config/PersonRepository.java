package com.example.batch.config;

import com.example.batch.entity.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends CrudRepository<Person,Integer> {
}
