package com.example.batch.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@Component
@StepScope
public class PersonTasklet implements Tasklet {

    @Value("${file.path.to.be.processed}")
    private String processingFilePath;
    @Value("${file.path.was.processed}")
    private String processedFilePath;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        File before = new File(processingFilePath);
        File after = new File(processedFilePath);

        Files.move(before.toPath(), after.toPath(), StandardCopyOption.REPLACE_EXISTING);

        return RepeatStatus.FINISHED;
    }
}
