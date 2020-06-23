package org.goafabric.spring.boot.examplebatch.logic.generic;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.UUID;

@Component
@StepScope
public class GenericJdbcVersionHandler {
    @Value("#{jobParameters[catalogVersion]}") //catalog version passed in via job params
    private String catalogVersion;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    //set catlog version via reflection
    public void setCatalogVersion(Object object) {
        final Field fieldCatalogVersion = ReflectionUtils.findField(object.getClass(), "catalogVersion");
        fieldCatalogVersion.setAccessible(true);
        ReflectionUtils.setField(fieldCatalogVersion, object, catalogVersion);
    }

    public void ensureCatalogVersion(String sql) {
        final String tableName = sql.split("INSERT INTO ")[1].split(" ")[0];
        Assert.notNull(tableName, "tablename should not be null");
        final int count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM " + tableName + " WHERE catalog_version = :catalogVersion",
                new MapSqlParameterSource().addValue("catalogVersion", catalogVersion), Integer.class);
        Assert.isTrue(count == 0 , "Catalog already imported with version: " + catalogVersion);
    }
}
