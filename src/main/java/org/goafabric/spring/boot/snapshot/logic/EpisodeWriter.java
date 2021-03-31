package org.goafabric.spring.boot.snapshot.logic;

import org.goafabric.spring.boot.snapshot.domain.EpisodeIndex;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class EpisodeWriter implements ItemWriter<EpisodeIndex> {
    @Override
    public void write(List<? extends EpisodeIndex> list) throws Exception {
        //write episodeIndex to elastic
    }
}
