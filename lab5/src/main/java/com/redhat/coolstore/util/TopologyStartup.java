package com.redhat.coolstore.util;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationInfo;
import org.flywaydb.core.api.callback.BaseFlywayCallback;
import org.wildfly.swarm.topology.Topology;
import org.wildfly.swarm.topology.TopologyListener;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.naming.NamingException;
import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Singleton
@Startup
public class TopologyStartup {


    private Logger logger = Logger.getLogger(TopologyStartup.class.getName());


    @PostConstruct
    private void startup() throws NamingException {

        logger.info("-------- LISTENING FOR TOPOLOGY CHANGES");
        Topology topology = Topology.lookup();

        topology.addListener(t -> {
            logger.info("-------- TOPOLOGY CHANGED. NEW TOPOLOGY: ");
            t.asMap().forEach((s, entries) ->
                    entries.forEach(entry ->
                            System.out.println("service: " + s + " host: " + entry.getAddress() +
                                    " port: " + entry.getPort() + " tags: " + entry.getTags())));
        });
    }


}
