package org.goafabric.spring.boot.snapshot.logic;

import org.goafabric.spring.boot.snapshot.domain.EpisodeIndex;
import org.goafabric.spring.boot.snapshot.domain.Snapshot;
import org.springframework.batch.item.ItemProcessor;

public class EpisodeProcessor implements ItemProcessor<Snapshot, EpisodeIndex> {
    @Override
    public EpisodeIndex process(Snapshot snapshot) throws Exception {
        //select * from episode where id = snapshot.referenceid => via jdbcTemplate
        //read, map, vooodo, create EpisodeIndex
        //return episodeIndex;
        return null;
    }
}
