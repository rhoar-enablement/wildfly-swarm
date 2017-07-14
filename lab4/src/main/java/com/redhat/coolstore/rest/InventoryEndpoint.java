package com.redhat.coolstore.rest;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.redhat.coolstore.model.Inventory;
import com.redhat.coolstore.service.InventoryService;
import org.wildfly.swarm.spi.runtime.annotations.ConfigurationValue;

import java.util.Optional;

@Path("/inventory")
public class InventoryEndpoint {

    @Inject
    private InventoryService inventoryService;

    @Inject
    @ConfigurationValue("stores.closed")
    private Optional<String> storesClosed;

    @GET
    @Path("/{itemId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Inventory getAvailability(@PathParam("itemId") String itemId) {
        Inventory i = inventoryService.getInventory(itemId);
        for (String store : storesClosed.orElse("").split(",")) {
            if (store.equalsIgnoreCase(i.getLocation())) {
                i.setQuantity(0);
            }
        }
        return i;
    }
}