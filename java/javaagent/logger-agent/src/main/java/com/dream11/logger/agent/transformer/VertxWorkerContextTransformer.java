package com.dream11.logger.agent.transformer;

import java.lang.instrument.Instrumentation;
import java.util.Collections;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class VertxWorkerContextTransformer extends BaseTransformer {

  //TODO: Implement with proper definitions
  public VertxWorkerContextTransformer(Instrumentation instrumentation) {
    super(Collections.singletonList("io.vertx.core.impl.WorkerContext"), instrumentation);
  }

  @Override
  protected void transformClass(CtClass contextClazz) throws CannotCompileException, NotFoundException {
    ClassPool pool = ClassPool.getDefault();
    CtClass CtObject = pool.get("java.lang.Object");
    CtClass CtHandler = pool.get("io.vertx.core.Handler");
    CtClass CtPoolMetrics = pool.get("io.vertx.core.spi.metrics.PoolMetrics");

    CtMethod mGetRequestData = CtNewMethod.make("public Object getRequestData(){"
        + "  return this.get(\"requestData\");"
        + "}", contextClazz);
    contextClazz.addMethod(mGetRequestData);

    CtMethod mSetRequestData = CtNewMethod.make("public void setRequestData(Object requestData){"
//        + "  System.out.println(\"Setting requestData:\" + requestData);"
        + "  this.put(\"requestData\", requestData);"
        + "}", contextClazz);
    contextClazz.addMethod(mSetRequestData);

    try {
      CtMethod wrapTaskMethod = contextClazz.getDeclaredMethod(
          "wrapTask",
          new CtClass[] {CtObject, CtHandler, CtPoolMetrics}
      );
      log.debug("Found method wrapTask {} on {}", wrapTaskMethod, contextClazz);
      wrapTaskMethod.insertBefore("{"
          + "  io.vertx.core.Handler handler = (io.vertx.core.Handler) com.dream11.logger.agent.patch.vertx.WrapperHandler.create($2);"
          + "  $2 = handler;"
          + "}");
    } catch (javassist.NotFoundException e) {
      log.debug("Could not find method wrapTask on {}", contextClazz);
    }
  }
}
