package com.iotiq.application.service.converter;

public interface Converter<S, T> {
    void convert(S source, T target);

    T convert(S source);
}
