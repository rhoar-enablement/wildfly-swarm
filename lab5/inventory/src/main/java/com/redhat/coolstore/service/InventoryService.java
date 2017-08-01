package com.redhat.coolstore.service;


import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.redhat.coolstore.model.Inventory;
import io.netty.buffer.ByteBufInputStream;

import java.util.Map;

@Stateless
public class InventoryService {

    @PersistenceContext
    private EntityManager em;


    public InventoryService() {

    }

    public Inventory getInventory(String itemId) {
        Inventory inventory = em.find(Inventory.class, itemId);
        return inventory;
    }

}
