package com.example.batch.config;

import com.example.batch.entity.Person;
import com.example.batch.listener.JobCompletionNotificationListener;
import com.example.batch.processor.PersonItemProcessor;
import com.example.batch.repository.PersonRepository;
import com.example.batch.tasklet.PersonTasklet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class BatchConfig {

    private PersonRepository repository;

    private Logger log = LoggerFactory.getLogger(BatchConfig.class);

    @Bean
    public FlatFileItemReader<Person> reader() {
        return new FlatFileItemReaderBuilder<Person>()
                .name("personItemReader")
                .resource(new ClassPathResource("sample-data.csv"))
                .linesToSkip(1)
                .delimited()
                .names(new String[]{"id", "name", "surname", "email", "gender"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<Person>() {{
                    setTargetType(Person.class);
                }})
                .build();
    }

    @Bean
    public PersonItemProcessor processor() {
        return new PersonItemProcessor();
    }

    @Bean
    public RepositoryItemWriter<Person> writer(CrudRepository<Person, Integer> repository) {
        return new RepositoryItemWriterBuilder<Person>()
                .repository(repository)
                .methodName("save")
                .build();
    }


    @Bean
    public Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager, DataSource dataSource) {
        return new StepBuilder("step1", jobRepository)
                .<Person, Person>chunk(20, transactionManager)
                .reader(reader())
                .processor(processor())
                .writer(writer(repository))
                .build();
    }

    @Bean
    public Step moveFile(JobRepository jobRepository, PlatformTransactionManager transactionManager, PersonTasklet tasklet) {
        return new StepBuilder("moveFile", jobRepository)
                .tasklet(tasklet, transactionManager)
                .build();
    }

    @Bean
    public Job importPeopleJob(JobRepository jobRepository, JobCompletionNotificationListener listener, Step step1, Step moveFile) {
        return new JobBuilder("importPeopleJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1)
                .next(moveFile)
                .end().build();
    }
}
