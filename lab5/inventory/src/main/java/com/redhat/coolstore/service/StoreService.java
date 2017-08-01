package com.redhat.coolstore.service;

import com.netflix.ribbon.Ribbon;
import com.netflix.ribbon.RibbonRequest;
import com.netflix.ribbon.proxy.annotation.*;
import io.netty.buffer.ByteBuf;

@ResourceGroup( name="store" )
public interface StoreService {

    StoreService INSTANCE = Ribbon.from(StoreService.class);

    @TemplateName("storeClosed")
    @Http(
            method = Http.HttpMethod.GET,
            uri = "/api/store/{location}"
    )
    @Hystrix(
            fallbackHandler = StoreServiceFallback.class
    )
    RibbonRequest<ByteBuf> storeClosed(@Var("location") String location);

}