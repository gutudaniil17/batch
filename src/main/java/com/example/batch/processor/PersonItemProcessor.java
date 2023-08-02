package com.example.batch.processor;

import com.example.batch.entity.Person;
import com.example.batch.validator.Validator;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.validator.ValidationException;

public class PersonItemProcessor implements ItemProcessor<Person,Person> {
    @Override
    public Person process(Person item) throws Exception {
        if(Validator.isPersonValid(item)){
            return item;
        }else {
            System.out.println(item);
            throw new ValidationException("Provided person is not valid");
        }
    }
}
