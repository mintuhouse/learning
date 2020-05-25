package com.dream11.logger.agent.patch.vertx;

import io.vertx.core.Context;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import java.lang.reflect.InvocationTargetException;

public class WrapperHandler<E> implements Handler<E> {

  // TODO: Keep track of parent instead of requestData - consider impact on GC;
  private final Object requestData;
  private final Handler<E> delegate;

  public WrapperHandler(Object requestData, Handler<E> delegate) {
    this.requestData = requestData;
    this.delegate = delegate;
  }

  public static <E> WrapperHandler<E> create(Handler<E> handler) {
    Context context = Vertx.currentContext();
    Object requestData = null;
    if (context != null) {
      try {
        requestData = context.getClass().getMethod("getRequestData", new Class[0]).invoke(context, new Object[0]);
      } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
        e.printStackTrace();
      }
    }
    return new WrapperHandler<>(requestData, handler);
  }

  // TODO: Will there be concurrent access in cases where executeBlocking is called on WrappedHandler?
  public void handle(E event) {
    Context context = Vertx.currentContext();
    Object previousRequestData = context.get("requestData");
    if (context != null) {
      try {
        if (requestData != null) {
          context.put("requestData", requestData);
        }
//        System.out.println("Running   handle task: " + this + " requestData: " + requestData + " on event: " + event);
        delegate.handle(event);
//        System.out.println("Completed handle task: " + this);
      } finally {
        if (previousRequestData != null) {
          context.put("requestData", previousRequestData);
        } else {
          context.remove("requestData");
        }
      }
    } else {
      delegate.handle(event);
    }
  }
}

