package com.ge.pmms.utils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class CacheUtils {

  public static HttpServletRequest getSpringRequest() {
    HttpServletRequest curRequest = ((ServletRequestAttributes) RequestContextHolder
        .currentRequestAttributes()).getRequest();
    return curRequest;
  }
  private static ApplicationContext context;

  public static ApplicationContext getContext() {
    return context;
  }

  public static Object getBean(String beanId) throws Exception {
    Object bean = context.getBean(beanId);
    if (bean == null) return null;
    return bean;
  }

  public static void setContext(ApplicationContext ctx) {
    context = ctx;
  }
}
