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
public class VertxEventLoopContextTransformer extends BaseTransformer {

  public VertxEventLoopContextTransformer(Instrumentation instrumentation) {
    super(Collections.singletonList("io.vertx.core.impl.EventLoopContext"), instrumentation);
  }

  @Override
  protected void transformClass(CtClass contextClazz) throws CannotCompileException, NotFoundException {
    ClassPool pool = ClassPool.getDefault();
    CtClass CtObject = pool.get("java.lang.Object");
    CtClass CtHandler = pool.get("io.vertx.core.Handler");

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
      CtMethod executeMethod = contextClazz.getDeclaredMethod(
          "execute",
          new CtClass[] {CtObject, CtHandler}
      );
      log.debug("Found method execute {} on {}", executeMethod, contextClazz);
      executeMethod.insertBefore("{"
          + "  io.vertx.core.Handler handler = (io.vertx.core.Handler) com.dream11.logger.agent.patch.vertx.WrapperHandler.create($2);"
//            + "  System.out.println(\"Scheduling Immed on \" from task: \" + this.get(\"currentTask\") +  \"to task: \" + handler);"
//            + "  new Throwable().printStackTrace(System.out);"
          + "  $2 = handler;"
          + "}");
    } catch (javassist.NotFoundException e) {
      log.debug("Could not find method execute on {}", contextClazz);
    }


    try {
      CtMethod executeAsyncMethod = contextClazz.getDeclaredMethod(
          "executeAsync",
          new CtClass[] {CtHandler}
      );
      log.debug("Found method executeAsync {} on {}", executeAsyncMethod, contextClazz);
      executeAsyncMethod.insertBefore("{"
          + "  io.vertx.core.Handler handler = (io.vertx.core.Handler) com.dream11.logger.agent.patch.vertx.WrapperHandler.create($1);"
          + "  $1 = handler;"
          + "}");
    } catch (javassist.NotFoundException e) {
      log.debug("Could not find method executeAsync on {}", contextClazz);
    }
  }
}
