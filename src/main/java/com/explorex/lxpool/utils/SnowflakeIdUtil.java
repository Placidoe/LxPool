package com.explorex.lxpool.utils;

public class SnowflakeIdUtil {
    private static final long EPOCH = 1609459200000L; // 起始时间戳 (2021-01-01)
    private static final long SEQUENCE_BITS = 12; // 序列号位数
    private static final long MACHINE_ID_BITS = 5; // 机器 ID 位数
    private static final long TIMESTAMP_BITS = 41; // 时间戳位数

    private static final SnowflakeIdGenerator generator = new SnowflakeIdGenerator(0); // 假设机器 ID 为 0

    public static long nextId() {
        return generator.nextId();
    }
}
