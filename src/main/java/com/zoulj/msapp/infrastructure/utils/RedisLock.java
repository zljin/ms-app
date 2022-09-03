package com.zoulj.msapp.infrastructure.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.core.types.Expiration;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class RedisLock implements AutoCloseable {
    public static final Logger log = LoggerFactory.getLogger(RedisLock.class);
    private RedisTemplate redisTemplate;
    private String key;
    private String value;

    //单位：秒
    private int expireTime;

    public RedisLock(RedisTemplate redisTemplate, String key, int expireTime) {
        this.redisTemplate = redisTemplate;
        this.key = key;
        this.expireTime = expireTime;
        this.value = UUID.randomUUID().toString();
    }

    /**
     * 获取分布式锁
     * @return
     */
    public boolean getLock(){
        RedisCallback<Boolean> redisCallback = redisConnection -> {
            RedisStringCommands.SetOption setOption = RedisStringCommands.SetOption.ifAbsent();
            Expiration expiration = Expiration.seconds(expireTime);
            byte[] rediskey = redisTemplate.getKeySerializer().serialize(key);
            byte[] redisvalue = redisTemplate.getKeySerializer().serialize(value);
            Boolean result = redisConnection.set(rediskey,redisvalue,expiration,setOption);
            return result;
        };
        Boolean result = (Boolean) redisTemplate.execute(redisCallback);
        log.info("获取锁的结果："+result);
        return result;
    }

    /**
     * 释放分布式锁
     * @return
     */
    public boolean unLock(){//lua脚本释放锁
        String scriptlua = "if redis.call('get', KEYS[1]) == ARGV[1] \n" +
                "    then \n" +
                "\t    return redis.call('del', KEYS[1]) \n" +
                "\telse \n" +
                "\t    return 0 \n" +
                "end";
        scriptlua = "\t    return redis.call('del', KEYS[1]) \n";
        RedisScript<Boolean> redisScript = RedisScript.of(scriptlua,Boolean.class);
        List keys = Arrays.asList(key);
        Boolean result = (Boolean) redisTemplate.execute(redisScript,keys,value);
        log.info("释放锁的结果："+result);
        return result;
    }

    @Override
    public void close() throws Exception {//自动关锁
        unLock();
    }
}
