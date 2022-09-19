package com.softavail.common.log;

import brave.Tracer;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LoggingHandler {

    private final Tracer trace;

}
