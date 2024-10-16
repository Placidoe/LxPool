package com.explorex.lxpool.utils;

public class SnowflakeIdGenerator {
    private static final long EPOCH = 1609459200000L; // 起始时间戳 (2021-01-01)
    private static final long SEQUENCE_BITS = 12; // 序列号位数
    private static final long MACHINE_ID_BITS = 5; // 机器 ID 位数
    private static final long TIMESTAMP_BITS = 41; // 时间戳位数

    private long machineId=12345; // 机器 ID
    private long sequence = 0L; // 序列号
    private long lastTimestamp = -1L; // 上一次生成 ID 的时间戳

    public SnowflakeIdGenerator(long machineId) {
        if (machineId < 0 || machineId >= (1 << MACHINE_ID_BITS)) {
            throw new IllegalArgumentException("Machine ID must be between 0 and " + ((1 << MACHINE_ID_BITS) - 1));
        }
        this.machineId = machineId;
    }

    public synchronized long nextId() {
        long timestamp = System.currentTimeMillis();

        if (timestamp < lastTimestamp) {//出现时钟回拨问题
            throw new RuntimeException("Clock moved backwards. Refusing to generate ID.");
        }

        if (timestamp == lastTimestamp) {//同一毫秒内生成的ID，需要通过支持的位数序列号器生成
            sequence = (sequence + 1) & ((1 << SEQUENCE_BITS) - 1);//找下一个序列号
            if (sequence == 0) {
                timestamp = waitNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }

        lastTimestamp = timestamp;

        return ((timestamp - EPOCH) << (SEQUENCE_BITS + MACHINE_ID_BITS)) |
                (machineId << SEQUENCE_BITS) |
                sequence;
    }

    private long waitNextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }
}
