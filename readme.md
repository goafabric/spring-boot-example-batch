# Documentation
https://docs.spring.io/spring-batch/docs/current/reference/html/index.html
https://docs.spring.io/spring-batch/docs/current/reference/html/job.html# configureJob
https://docs.spring.io/spring-batch/docs/current/reference/html/readersAndWriters.html# xmlReadingWriting

# SQLS
select * from catalogs.batch_job_execution
select * from catalogs.toy_catalog
select * from catalogs.person_catalog

# docker compose
go to /src/deploy/docker and do "./stack up"

# run jvm multi image
docker run --pull always --name spring-boot-example-batch --rm goafabric/spring-boot-example-batch:3.0.2-SNAPSHOT

# run native image
docker run --pull always --name spring-boot-example-batch-native --rm goafabric/spring-boot-example-batch-native:3.0.2-SNAPSHOT -Xmx32m

# run native image arm
docker run --pull always --name --name spring-boot-example-batch-native --rm goafabric/spring-boot-example-batch-native-arm64v8:3.0.2-SNAPSHOT -Xmx32m

