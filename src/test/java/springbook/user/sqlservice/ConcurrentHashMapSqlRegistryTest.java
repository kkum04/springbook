package springbook.user.sqlservice;

import springbook.user.sqlservice.updateable.ConcurrentHashMapSqlRegistry;
import springbook.user.sqlservice.updateable.UpdatableSqlRegistry;

public class ConcurrentHashMapSqlRegistryTest extends AbstractUpdatableSqlRegistryTest {
    @Override
    protected UpdatableSqlRegistry createUpdatableSqlRegistry() {
        return new ConcurrentHashMapSqlRegistry();
    }
}