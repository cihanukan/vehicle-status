package com.softavail.filter;

import io.micronaut.core.async.publisher.Publishers;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.filter.FilterChain;
import io.micronaut.http.filter.HttpFilter;
import io.micronaut.http.annotation.Filter;
import org.apache.log4j.MDC;
import org.reactivestreams.Publisher;

import java.util.UUID;

/**
 * @author Graeme Rocher
 * @since 1.0
 */
@Filter(Filter.MATCH_ALL_PATTERN)
public class FirstFilter implements HttpFilter {

  @Override
  public int getOrder() {
    return - 100;
  }

  @Override
  public Publisher<? extends HttpResponse<?>> doFilter(HttpRequest<?> request, FilterChain chain) {

    String requestId = String.valueOf(UUID.randomUUID());
    MDC.put("RequestId", requestId);

    Publisher<? extends HttpResponse<?>> publisher = chain.proceed(request);

    return Publishers.then(publisher, httpResponse -> {
      MDC.clear();
    });
  }
}