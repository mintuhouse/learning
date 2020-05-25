package com.dream11.logger.agent.transformer;

import java.lang.instrument.Instrumentation;
import java.util.Collections;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class VertxContextImplTransformer extends BaseTransformer {

  public VertxContextImplTransformer(Instrumentation instrumentation) {
    super(Collections.singletonList("io.vertx.core.impl.ContextImpl"), instrumentation);
  }

  @Override
  protected void transformClass(CtClass contextClazz) throws CannotCompileException, NotFoundException {
    ClassPool pool = ClassPool.getDefault();
    CtClass CtObject = pool.get("java.lang.Object");
    CtClass CtHandler = pool.get("io.vertx.core.Handler");
    CtClass CtExecutor = pool.get("java.util.concurrent.Executor");
    CtClass CtTaskQueue = pool.get("io.vertx.core.impl.TaskQueue");
    CtClass CtPoolMetrics = pool.get("io.vertx.core.spi.metrics.PoolMetrics");


    try {
      CtMethod executeBlockingMethod = contextClazz.getDeclaredMethod(
          "executeBlocking",
          new CtClass[] {CtHandler, CtHandler, CtExecutor, CtTaskQueue, CtPoolMetrics}
      );
      log.debug("Found method executeBlocking {} on {}", executeBlockingMethod, contextClazz);
      executeBlockingMethod.insertBefore("{"
          + "  io.vertx.core.Handler blockingHandler = (io.vertx.core.Handler) com.dream11.logger.agent.patch.vertx.WrapperHandler.create($1);"
          + "  $1 = blockingHandler;"
          + "}");
    } catch (javassist.NotFoundException e) {
      log.debug("Could not find method executeBlocking on {}", contextClazz);
    }

    try {
      CtMethod executeTaskMethod = contextClazz.getDeclaredMethod(
          "executeTask",
          new CtClass[] {CtObject, CtHandler}
      );
      log.debug("Found method executeTask {} on {}", executeTaskMethod, contextClazz);
      executeTaskMethod.insertBefore("{"
//          + "  System.out.println(\"executingTask: \" + $2);"
          + "}");
    } catch (javassist.NotFoundException e) {
      log.debug("Could not find method executeTask on {}", contextClazz);
    }
  }
}
