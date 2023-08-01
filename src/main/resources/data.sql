drop table if exists people;
create table people(
                       id int default (1),
                       name varchar(50),
                       surname varchar(50),
                       email varchar(50),
                       gender varchar(10),
                       primary key (id)
);