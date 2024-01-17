package springbook.user.sqlservice;

import org.junit.After;
import org.junit.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import springbook.user.sqlservice.updateable.EmbeddedDbSqlRegistry;
import springbook.user.sqlservice.updateable.SqlUpdateFailureException;
import springbook.user.sqlservice.updateable.UpdatableSqlRegistry;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.fail;

public class EmbeddedDbSqlRegistryTest extends AbstractUpdatableSqlRegistryTest {
    EmbeddedDatabase db;

    @Override
    protected UpdatableSqlRegistry createUpdatableSqlRegistry() {
        db = new EmbeddedDatabaseBuilder()
            .setType(EmbeddedDatabaseType.HSQL)
            .addScript("classpath:springbook/user/sqlservice/updatable/sqlRegistrySchema.sql")
            .build();

        EmbeddedDbSqlRegistry embeddedDbSqlRegistry = new EmbeddedDbSqlRegistry();
        embeddedDbSqlRegistry.setDataSource(db);

        return embeddedDbSqlRegistry;
    }

    @Test
    public void transactionalUpdate() {
        checkFindResult("SQL1", "SQL2", "SQL3");

        Map<String, String> sqlmap = new LinkedHashMap<>();
        sqlmap.put("KEY1", "Modified1");
        sqlmap.put("KEY999!@#", "Modified99999");

        try {
            sqlRegistry.updateSql(sqlmap);
            fail();
        } catch (SqlUpdateFailureException e) {}

        checkFindResult("SQL1", "SQL2", "SQL3");
    }

    @After
    public void teardown() {
        this.db.shutdown();
    }
}
