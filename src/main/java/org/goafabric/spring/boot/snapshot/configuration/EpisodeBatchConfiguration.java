package org.goafabric.spring.boot.snapshot.configuration;

import org.goafabric.spring.boot.examplebatch.logic.JobCompletionListener;
import org.goafabric.spring.boot.snapshot.domain.EpisodeIndex;
import org.goafabric.spring.boot.snapshot.domain.Snapshot;
import org.goafabric.spring.boot.snapshot.logic.EpisodeProcessor;
import org.goafabric.spring.boot.snapshot.logic.EpisodeSnapshotReader;
import org.goafabric.spring.boot.snapshot.logic.EpisodeWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

public class EpisodeBatchConfiguration {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job episodeJob(JobCompletionListener listener) {
        return jobBuilderFactory.get("episodeJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener).flow(episodeStep()).end()
                .build();
    }

    //@Bean
    public Step episodeStep() {
        return stepBuilderFactory.get("episodeStep")
                .<Snapshot, EpisodeIndex> chunk(10)//defines how much data is written at a time
                .reader(new EpisodeSnapshotReader())
                .processor(new EpisodeProcessor())
                .writer(new EpisodeWriter())
                .build();
    }

}
