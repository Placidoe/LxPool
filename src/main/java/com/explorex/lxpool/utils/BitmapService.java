package com.explorex.lxpool.utils;

import com.google.common.collect.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
  public class BitmapService {
      @Autowired
      private RedisTemplate<String, String> redisTemplate;
  
      // 设置指定位置的位值
      public void setBit(String key, long offset, boolean value) {
          redisTemplate.opsForValue().setBit(key, offset, value);
      }
  
      // 获取指定位置的位值
      public Boolean getBit(String key, long offset) {
          return redisTemplate.opsForValue().getBit(key, offset);
      }
  
      // 统计指定范围内的位值为 true 的个数
      public Long bitCount(String key) {
          return redisTemplate.execute((RedisCallback<Long>) connection -> connection.bitCount(key.getBytes()));
      }
  
      public Long bitCount(String key, long start, long end) {
          return redisTemplate.execute((RedisCallback<Long>) connection -> connection.bitCount(key.getBytes(), start, end));
      }
  
      // 首签时间
      public Long bitPos(String key, boolean bit) {
          return redisTemplate.execute((RedisCallback<Long>) connection -> connection.bitPos(key.getBytes(), bit));
      }
  
      public Long bitPos(String key, boolean bit, Long start, Long end) {
          Range<Long> range = Range.closed(start, end);
          return redisTemplate.execute((RedisCallback<Long>) connection -> connection.bitPos(key.getBytes(), bit));
      }
  
      // 连续签到/累计签到
      public Long bitOp(RedisStringCommands.BitOperation op, String destination, String... keys) {
          byte[][] keyBytes = new byte[keys.length][];
          for (int i = 0; i < keys.length; i++) {
              keyBytes[i] = keys[i].getBytes();
          }
          return redisTemplate.execute((RedisCallback<Long>) connection -> connection.bitOp(op, destination.getBytes(), keyBytes));
      }
  }
