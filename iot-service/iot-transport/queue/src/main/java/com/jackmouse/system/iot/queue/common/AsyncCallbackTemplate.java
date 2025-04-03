package com.jackmouse.system.iot.queue.common;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import com.jackmouse.system.iot.queue.JmQueueMsg;

import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.Consumer;

/**
 * @ClassName AsyncCallbackTemplate
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/1 20:59
 * @Version 1.0
 **/
public class AsyncCallbackTemplate {
    public static <T> void withCallback(ListenableFuture<T> future, Consumer<T> onSuccess,
                                        Consumer<Throwable> onFailure, Executor executor) {
        FutureCallback<T> callback = new FutureCallback<T>() {
            @Override
            public void onSuccess(T result) {
                try {
                    onSuccess.accept(result);
                } catch (Throwable th) {
                    onFailure.accept(th);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                onFailure.accept(t);
            }
        };
        Futures.addCallback(future, callback, Objects.requireNonNullElseGet(executor, MoreExecutors::directExecutor));
    }

    public static <T> void withCallbackAndTimeout(ListenableFuture<T> handle,
                                                  Consumer<T> onSuccess,
                                                  Consumer<Throwable> onFailure,
                                                  long timeoutInMillis,
                                                  ScheduledExecutorService timeoutExecutor,
                                                  Executor callbackExecutor) {
        FutureCallback<T> callback = new FutureCallback<T>() {
            @Override
            public void onSuccess(T result) {
                try {
                    onSuccess.accept(result);
                } catch (Throwable th) {
                    onFailure(th);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                onFailure.accept(t);
            }
        };
        Futures.addCallback(handle, callback, Objects.requireNonNullElseGet(callbackExecutor, MoreExecutors::directExecutor));
    }
}
