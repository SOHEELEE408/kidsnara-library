package com.kidsnara.library.config.database;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Map;

public class RoutingDataSource extends AbstractRoutingDataSource {

    public RoutingDataSource(Map<Object,Object> datasourceMap) {
        super.setTargetDataSources(datasourceMap);
        super.setDefaultTargetDataSource(datasourceMap.get(DatabaseRole.PRIMARY));
    }

    @Override
    protected Object determineCurrentLookupKey() {
        DatabaseRole databaseRole = TransactionSynchronizationManager.isCurrentTransactionReadOnly()
                ? DatabaseRole.SECONDARY : DatabaseRole.PRIMARY;
        System.out.println("ROLE: "+ databaseRole.toString());
        return databaseRole;
    }
}
