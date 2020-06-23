//uncomment if you want to avoid usage of batch_job tables
/*
package org.goafabric.spring.boot.examplebatch.configuration;

import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

//meta data is stored in memory, no need for spring batch meta data tables inside database
@Component
public class NoPersistenceBatchConfigurer extends DefaultBatchConfigurer {
    @Override
    public void setDataSource(DataSource dataSource) {
    }
}
*/

