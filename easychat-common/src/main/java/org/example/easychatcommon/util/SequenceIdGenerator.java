package org.example.easychatcommon.util;

import java.util.concurrent.atomic.AtomicInteger;

//顺序id生成器 线程安全
public abstract class SequenceIdGenerator {

    private static final AtomicInteger ID = new AtomicInteger();

    //自增
    public static int nextId() {
        return ID.incrementAndGet();
    }
}

