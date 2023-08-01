package com.example.batch.processor;

import com.example.batch.entity.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class PersonItemProcessor implements ItemProcessor<Person,Person> {

    private static final Logger log = LoggerFactory.getLogger(PersonItemProcessor.class);
    @Override
    public Person process(Person item) throws Exception {
        final String name = item.getName().toUpperCase();
        final String surname = item.getSurname().toUpperCase();

        final Person transformed = item;
        transformed.setName(name);
        transformed.setSurname(surname);

        log.info("Converting : " + item + " to : "+transformed);
        return transformed;
    }
}
