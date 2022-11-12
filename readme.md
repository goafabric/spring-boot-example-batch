#Documentation
https://docs.spring.io/spring-batch/docs/current/reference/html/index.html
https://docs.spring.io/spring-batch/docs/current/reference/html/job.html#configureJob
https://docs.spring.io/spring-batch/docs/current/reference/html/readersAndWriters.html#xmlReadingWriting

#SQLS
select * from catalogs.batch_job_execution
select * from catalogs.toy_catalog
select * from catalogs.person_catalog

#docker compose
go to /src/deploy/docker and do "./stack up"

#run jvm multi image
docker pull goafabric/spring-boot-example-batch:3.0.0-RC2 && docker run --name spring-boot-example-batch --rm -p50900:50900 goafabric/spring-boot-example-batch:3.0.0-RC2

#run native image
docker pull goafabric/spring-boot-example-batch-native:3.0.0-RC2 && docker run --name spring-boot-example-batch-native --rm -p50900:50900 goafabric/spring-boot-example-batch-native:3.0.0-RC2 -Xmx32m

#run native image arm
docker pull goafabric/spring-boot-example-batch-native-arm64v8:3.0.0-RC2 && docker run --name spring-boot-example-batch-native-arm64v8 --rm -p50900:50900 goafabric/spring-boot-example-batch-native-arm64v8:3.0.0-RC2 -Xmx32m

