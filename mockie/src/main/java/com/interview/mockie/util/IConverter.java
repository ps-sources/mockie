package com.interview.mockie.util;

public interface IConverter<U,V> {
    U from(V u);
    V to(U v);
}
