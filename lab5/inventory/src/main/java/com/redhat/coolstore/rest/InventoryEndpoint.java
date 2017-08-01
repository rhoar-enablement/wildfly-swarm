package com.redhat.coolstore.rest;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.redhat.coolstore.model.Inventory;
import com.redhat.coolstore.model.Store;
import com.redhat.coolstore.service.InventoryService;
import com.redhat.coolstore.service.StoreService;
import io.netty.buffer.ByteBufInputStream;
import org.wildfly.swarm.topology.Advertise;

import java.io.IOException;

@RequestScoped
@Advertise("inventory")
@Path("/inventory")
public class InventoryEndpoint {

    private final StoreService storeService;

    @Context
    private UriInfo uriInfo;


    @Inject
    private InventoryService inventoryService;

    public InventoryEndpoint() {
        this.storeService = StoreService.INSTANCE;
    }

    @GET
    @Path("/{itemId}")
    @Produces(MediaType.APPLICATION_JSON)
    public void getAvailability(@PathParam("itemId") String itemId,
                                @Suspended AsyncResponse asyncResponse) {
        Inventory i = inventoryService.getInventory(itemId);
        String myAddress = uriInfo.getBaseUri().toASCIIString();

        storeService.storeClosed(i.getLocation()).toObservable().subscribe(
                (result) -> {
                    try {
                        ObjectMapper mapper = new ObjectMapper();
                        ObjectReader reader = mapper.reader();
                        JsonFactory factory = new JsonFactory();
                        JsonParser parser = factory.createParser(new ByteBufInputStream(result));
                        Store store = reader.readValue(parser, Store.class);
                        Boolean isOpen = store.isOpen();
                        String storeAddress = store.getAddress();

                        i.setLocation(i.getLocation() + " [STORE IS " + (isOpen ? "OPEN" : "CLOSED") +
                                " inventory:" + myAddress +
                                " store:" + storeAddress + "]");
                        asyncResponse.resume(i);
                    } catch (IOException e) {
                        System.err.println("ERROR: " + e.getLocalizedMessage());
                        asyncResponse.resume(e);
                    }
                },
                (err) -> {
                    System.err.println("ERROR: " + err.getLocalizedMessage());
                    asyncResponse.resume(err);
                });

        System.out.println("Received request for inventory for itemId=" + itemId);


    }


}
