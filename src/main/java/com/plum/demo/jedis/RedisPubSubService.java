package com.plum.demo.jedis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

@Service
@Slf4j
public class RedisPubSubService {

    @Autowired
    private JedisPool jedisPool;

    public void subscribe(String channel, JedisPubSub jedisPubSub) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.subscribe(jedisPubSub, channel);
        } catch(Exception exception) {
            log.error("Exception occurred while subscribing to channel: {}", channel);
        }
    }

    public void unsubscribe(JedisPubSub jedisPubSub) {
        jedisPubSub.unsubscribe();
    }

    public void publishMessage(String channel, String message) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.publish(channel, message);
        } catch (Exception exception) {
            log.error("Exception occurred while publishing message: {} to channel: {}", message, channel);
        }
    }
}
