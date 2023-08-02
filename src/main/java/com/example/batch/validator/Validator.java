package com.example.batch.validator;

import com.example.batch.entity.Person;

import java.util.regex.Pattern;

public class Validator {
    public static boolean isPersonValid(Person person){
        return isIdValid(person.getId())
                && isEmailValid(person.getEmail())
                && isNameValid(person.getName())
                && isNameValid(person.getSurname())
                && isNameValid(person.getGender());
    }

    private static boolean isIdValid(int id){
        return id >= 0;
    }

    private static boolean isNameValid(String name){
        name.trim();
        String regex = "^[A-Za-z\\-\s']+$";
        return Pattern.matches(regex,name);
    }

    private static boolean isEmailValid(String email){
        email.trim();
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return Pattern.matches(regex, email);
    }

}
