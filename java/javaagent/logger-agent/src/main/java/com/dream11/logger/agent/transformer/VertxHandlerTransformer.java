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
public class VertxHandlerTransformer extends BaseTransformer {

  public VertxHandlerTransformer(Instrumentation instrumentation) {
    super(Collections.singletonList("io.vertx.core.net.impl.VertxHandler"), instrumentation);
  }

  @Override
  protected void transformClass(CtClass contextClazz) throws CannotCompileException, NotFoundException {
    ClassPool pool = ClassPool.getDefault();
    CtClass CtHandler = pool.get("io.vertx.core.Handler");

    try {
      CtMethod addHandlerMethod = contextClazz.getDeclaredMethod(
          "addHandler",
          new CtClass[] {CtHandler}
      );
      log.debug("Found method addHandler {} on {}", addHandlerMethod, contextClazz);
      addHandlerMethod.insertBefore("{"
//          + "  System.out.println(\"addHandler: \" + $1);"
          + "}");
    } catch (javassist.NotFoundException e) {
      log.debug("Could not find method addHandler on {}", contextClazz);
    }


    try {
      CtMethod removeHandlerMethod = contextClazz.getDeclaredMethod(
          "removeHandler",
          new CtClass[] {CtHandler}
      );
      log.debug("Found method removeHandler {} on {}", removeHandlerMethod, contextClazz);
      removeHandlerMethod.insertBefore("{"
//          + "  System.out.println(\"removeHandler: \" + $1);"
          + "}");
    } catch (javassist.NotFoundException e) {
      log.debug("Could not find method removeHandler on {}", contextClazz);
    }


    try {
      CtMethod channelReadMethod = contextClazz.getDeclaredMethod("channelRead");
      log.debug("Found method channelRead {} on {}", channelReadMethod, contextClazz);
      channelReadMethod.insertBefore("{"
//          + "  System.out.println(\"channelRead: \" + messageHandler + conn);"
          + "}");
    } catch (javassist.NotFoundException e) {
      log.debug("Could not find method channelRead on {}", contextClazz);
    }

    try {
      CtMethod setConnectionMethod = contextClazz.getDeclaredMethod("setConnection");
      log.debug("Found method setConnection {} on {}", setConnectionMethod, contextClazz);
      setConnectionMethod.insertAfter("{"
//          + "  System.out.println(\"setConnection: \" + $1);"
          + "  io.vertx.core.Handler handler = (io.vertx.core.Handler) com.dream11.logger.agent.patch.vertx.WrapperHandler.create(messageHandler);"
          + "  messageHandler = handler;"
          + "}");
    } catch (javassist.NotFoundException e) {
      log.debug("Could not find method setConnection on {}", contextClazz);
    }

  }
}
