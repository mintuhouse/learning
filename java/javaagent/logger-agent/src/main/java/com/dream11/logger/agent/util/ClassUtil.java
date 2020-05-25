package com.dream11.logger.agent.util;

import java.lang.instrument.Instrumentation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClassUtil {

  public static final Class<?> getClass(String className, Instrumentation instrumentation) {
    Class<?> targetCls = null;

    // see if we can get the class using forName
    try {
      targetCls = Class.forName(className);
      log.debug("Found class via forName: {}", targetCls);
      return targetCls;
    } catch (Exception ex) {
      log.error("Class [{}] not found with Class.forName");
    }

    // otherwise iterate all loaded classes and find what we want
    for (Class<?> clazz : instrumentation.getAllLoadedClasses()) {
      if (clazz.getName().equals(className)) {
        targetCls = clazz;
        log.debug("Found class via iteration: {}", targetCls);
        return targetCls;
      }
    }

    throw new RuntimeException("Failed to find class [" + className + "]");
  }
}
