package com.dream11.logger.agent.transformer;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;
import java.util.List;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;

/**
 * References:
 * https://www.javassist.org/tutorial/tutorial.html
 */

@Slf4j
public abstract class BaseTransformer implements ClassFileTransformer {

  protected final Instrumentation instrumentation;
  protected final List<String> classNames;

  public BaseTransformer(List<String> classNames, Instrumentation instrumentation) {
    this.classNames = classNames;
    this.instrumentation = instrumentation;
  }

  public void transform() {
    this.instrumentation.addTransformer(this, true);
  }

  @Override
  public byte[] transform(ClassLoader loader,
                          String className,
                          Class<?> classBeingRedefined,
                          ProtectionDomain protectionDomain,
                          byte[] classfileBuffer) throws IllegalClassFormatException {
    byte[] byteCode = classfileBuffer;

    String dottedClassName = className.replace("/", ".");
    if (!this.classNames.contains(dottedClassName)) {
      return byteCode;
    } else {
      ClassPool cp = ClassPool.getDefault();
      CtClass clazz = null;
      log.debug("Transforming {}", className);
      try {
        clazz = cp.get(dottedClassName);
        transformClass(clazz);
        byteCode = clazz.toBytecode();
      } catch (NotFoundException | CannotCompileException | IOException e) {
        log.error("Exception", e);
      } finally {
        if (clazz != null) {
          clazz.detach();
        }
      }
    }
    return byteCode;
  }

  protected abstract void transformClass(CtClass clazz) throws CannotCompileException, NotFoundException;
}
