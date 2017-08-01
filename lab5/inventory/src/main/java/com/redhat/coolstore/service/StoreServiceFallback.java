package com.redhat.coolstore.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.netflix.hystrix.HystrixInvokableInfo;
import com.netflix.ribbon.hystrix.FallbackHandler;
import com.redhat.coolstore.model.Store;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.UnpooledByteBufAllocator;
import rx.Observable;

import java.io.IOException;
import java.util.Map;

public class StoreServiceFallback implements FallbackHandler<ByteBuf> {
    @Override
    public Observable getFallback(HystrixInvokableInfo<?> hystrixInvokableInfo, Map<String, Object>  requestProps) {

        String location = (String)requestProps.get("location");

        Store fallbackStore = new Store(location, false, "FALLBACK");

        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter writer = mapper.writer();
        ByteBuf byteBuf = UnpooledByteBufAllocator.DEFAULT.buffer();
        ByteBufOutputStream bos = new ByteBufOutputStream(byteBuf);
        try {
            writer.writeValue(bos, fallbackStore);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Observable.just(byteBuf);
    }
}
