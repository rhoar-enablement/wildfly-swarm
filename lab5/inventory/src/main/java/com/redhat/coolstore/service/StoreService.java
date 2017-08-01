package com.redhat.coolstore.service;

import com.netflix.ribbon.Ribbon;
import com.netflix.ribbon.RibbonRequest;
import com.netflix.ribbon.proxy.annotation.Http;
import com.netflix.ribbon.proxy.annotation.ResourceGroup;
import com.netflix.ribbon.proxy.annotation.TemplateName;
import com.netflix.ribbon.proxy.annotation.Var;
import io.netty.buffer.ByteBuf;

@ResourceGroup( name="store" )
public interface StoreService {

    StoreService INSTANCE = Ribbon.from(StoreService.class);

    @TemplateName("storeClosed")
    @Http(
            method = Http.HttpMethod.GET,
            uri = "/api/store/{location}"
    )
    RibbonRequest<ByteBuf> storeClosed(@Var("location") String location);

}