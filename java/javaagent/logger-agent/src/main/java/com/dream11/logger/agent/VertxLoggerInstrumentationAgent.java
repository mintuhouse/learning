package com.dream11.logger.agent;

import com.dream11.logger.agent.transformer.VertxContextImplTransformer;
import com.dream11.logger.agent.transformer.VertxEventLoopContextTransformer;
import com.dream11.logger.agent.transformer.VertxHandlerTransformer;
import com.dream11.logger.agent.transformer.VertxWorkerContextTransformer;
import java.lang.instrument.Instrumentation;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class VertxLoggerInstrumentationAgent {
  public static void premain(final String agentArgs, final Instrumentation instrumentation) {
    try {
      CtClass wrapperHandlerClass = ClassPool.getDefault().get("com.dream11.logger.agent.patch.vertx.WrapperHandler");
      Class clazz = VertxLoggerInstrumentationAgent.class;
      wrapperHandlerClass.toClass(clazz.getClassLoader(), clazz.getProtectionDomain());

      new VertxContextImplTransformer(instrumentation).transform();
      new VertxEventLoopContextTransformer(instrumentation).transform();
      new VertxWorkerContextTransformer(instrumentation).transform();
      new VertxHandlerTransformer(instrumentation).transform();

    } catch (CannotCompileException | NotFoundException e) {
      e.printStackTrace();
    }
  }

  public static void agentmain(final String agentArgs, final Instrumentation instrumentation) {
    premain(agentArgs, instrumentation);
  }
}