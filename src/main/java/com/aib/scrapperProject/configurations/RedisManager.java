package com.aib.scrapperProject.configurations;

import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPooled;

@Configuration
public class RedisManager {

    private final JedisPooled jedis;


    public RedisManager(){
        this.jedis = new JedisPooled("localhost",6379);
    }

    public void saveContent(String url, String content, int timeInSeconds){
        jedis.setex(url,timeInSeconds, content);
    }

    public String getContent(String url){
        return jedis.get(url);
    }

    public boolean isContentChanged(String url, String newObject){
        String oldObject = jedis.get(url);
        return oldObject == null || !oldObject.equals(newObject);
    }

    public void close(){
        jedis.close();
    }

}
