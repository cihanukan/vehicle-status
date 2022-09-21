package com.softavail.filter;

import com.softavail.constant.ApplicationConstant;
import io.micronaut.core.async.publisher.Publishers;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.filter.FilterChain;
import io.micronaut.http.filter.HttpFilter;
import io.micronaut.http.annotation.Filter;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.reactivestreams.Publisher;

import java.util.UUID;

@Filter(Filter.MATCH_ALL_PATTERN)
public class FirstFilter implements HttpFilter {

  private static final Logger log = LogManager.getLogger(FirstFilter.class);
  @Override
  public int getOrder() {
    return - 100;
  }

  @Override
  public Publisher<? extends HttpResponse<?>> doFilter(HttpRequest<?> request, FilterChain chain) {

    String requestId = String.valueOf(UUID.randomUUID());
    MDC.put(ApplicationConstant.REQUEST_ID, requestId);
    log.debug(request);

    Publisher<? extends HttpResponse<?>> publisher = chain.proceed(request);

    return Publishers.then(publisher, httpResponse -> {
      log.debug(httpResponse);
      MDC.clear();
    });
  }
}