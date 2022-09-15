package com.kidsnara.library.config.database;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class RoutingDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        DatabaseRole databaseRole = TransactionSynchronizationManager.isCurrentTransactionReadOnly()
                ? DatabaseRole.SECONDARY : DatabaseRole.PRIMARY;
        System.out.println("ROLE: "+ databaseRole.toString());
        return databaseRole;
    }
}
