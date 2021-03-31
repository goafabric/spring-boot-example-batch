package org.goafabric.spring.boot.snapshot.logic;

import org.goafabric.spring.boot.snapshot.domain.Snapshot;
import org.springframework.batch.item.database.JdbcCursorItemReader;

public class EpisodeSnapshotReader extends JdbcCursorItemReader<Snapshot> {

    @Override
    public void afterPropertiesSet() {
        this.setSql("select referenceid from ais_snapshot where type = 'episode'");
        //warning!: ais_snapshot has to have an postgres index for types
    }

}
