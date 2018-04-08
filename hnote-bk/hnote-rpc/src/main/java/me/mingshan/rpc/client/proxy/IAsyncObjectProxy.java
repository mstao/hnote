package me.mingshan.rpc.client.proxy;

import me.mingshan.rpc.client.RPCFuture;


public interface IAsyncObjectProxy {
    public RPCFuture call(String funcName, Object... args);
}