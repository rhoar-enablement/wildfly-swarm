package com.redhat.coolstore.util;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationInfo;
import org.flywaydb.core.api.callback.BaseFlywayCallback;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.sql.Connection;
import java.util.logging.Logger;

@Singleton
@Startup
public class DataBaseMigrationStartup extends BaseFlywayCallback {


    private Logger logger = Logger.getLogger(DataBaseMigrationStartup.class.getName());


    @PostConstruct
    private void startup() {
        logger.info("-------- STARTING DATABASE MIGRATION");

        Flyway flyway = new Flyway();

        String dbConnUrl = System.getProperty("swarm.datasources.data-sources.InventoryDS.connection-url");
        String dbUser = System.getProperty("swarm.datasources.data-sources.InventoryDS.user-name");
        String dbPassword = System.getProperty("swarm.datasources.data-sources.InventoryDS.password");
        flyway.setDataSource(dbConnUrl, dbUser, dbPassword);

        flyway.migrate();

        logger.info("--------- DATABASE MIGRATION DONE");
    }

    @Override
    public void afterEachMigrate(Connection connection, MigrationInfo info) {
        logger.info("Migration Step: " + info + " Connection: " + connection);
    }

}
