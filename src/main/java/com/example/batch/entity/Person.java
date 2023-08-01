package com.example.batch.entity;

import jakarta.persistence.*;
import lombok.*;

/*create table people(
        id int not null auto_increment,
        name varchar(50),
        surname varchar(50),
        email varchar(50),
        gender varchar(10),
        primary key (id)
        )*/
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "people")
public class Person {
    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "email")
    private String email;
    @Column(name = "gender")
    private String gender;
}

