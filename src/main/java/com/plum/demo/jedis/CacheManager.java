package com.plum.demo.jedis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.*;

//Jedis, Redisson
@Component
public class CacheManager {
    // JedisPool (ip, port) -> jedis pool -> resources -> jedis -> operations
    @Autowired
    private JedisPool jedisPool;

    //Get String value for key
    public Optional<String> get(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            String value = jedis.get(key);
            return Optional.of(value);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    //Get String value for key
    public Optional<Boolean> exists(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            Boolean value = jedis.exists(key);
            return Optional.of(value);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    // Set Key and Value with expiry time
    public boolean set(String key, String value, int seconds) {
        try (Jedis jedis = jedisPool.getResource()) {
            String result = jedis.set(key, value);
            if (!Objects.isNull(result) && seconds > 0) {
                jedis.expire(key, seconds);
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    //Set Key and Value With Expiry Time
    public boolean set(String key, String value) {
        try (Jedis jedis = jedisPool.getResource()) {
            String result = jedis.set(key, value);
            if (!Objects.isNull(result)) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    //Push Elements to list from right side(tail end)
    public boolean pushToListFromEnd(String key, String... values) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.rpush(key, values);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    //Push Elements to list from left side(Start end)
    public boolean pushToListFromFront(String key, String... values) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.lpush(key, values);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //Get Complete List from redis
    public List<String> getCompleteList(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.lrange(key, 0, -1);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    //Get a range of values from List
    public List<String> getRangeOfValuesFromList(String key, int start, int end) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.lrange(key, start, end);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    //Pop Elements from Right Side(Tail End)
    public List<String> popElementsFromRight(String key, int count) {
        try (Jedis jedis = jedisPool.getResource()) {
            //Count is not of elements we want to remove from list
            return jedis.rpop(key, count);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    //Pop Elements from Lst Side(Head End)
    public List<String> popElementsFromLeft(String key, int count) {
        try (Jedis jedis = jedisPool.getResource()) {
            //Count is not of elements we want to remove from list
            return jedis.lpop(key, count);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    //Trim List with given range
    public boolean trimList(String key, int start, int end) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.ltrim(key, start, end);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Find Length of List
    public long getSizeOfList(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.llen(key);
        } catch (Exception e) {
            return 0L;
        }
    }

    //Set fields in Hash Map
    public boolean setHash(String key, String field, String value) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.hset(key, field, value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //Get filed from HashMap
    public Optional<String> getHash(String key, String field) {
        try (Jedis jedis = jedisPool.getResource()) {
            return Optional.of(jedis.hget(key, field));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    //Get all fields from Hash Map
    public Map<String, String> getAllHash(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.hgetAll(key);
        } catch (Exception e) {
            return new HashMap<>();
        }
    }

    // Sorted Set Operations
    // Add members to Sorted Set
    public boolean addToSortedSet(String key, double score, String member) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.zadd(key, score, member);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //Get Sorted Set
    public List<String> getSortedSet(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.zrange(key, 0, -1);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    //Remove elements from Sorted Set
    public boolean removeFromSortedSet(String key, String... members) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.zrem(key, members);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    // Set Operations
    // Add members to Sorted Set
    public boolean addToSet(String key, String... members) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.sadd(key, members);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //Get Sorted Set
    public Set<String> getSet(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.smembers(key);
        } catch (Exception e) {
            return new HashSet<>();
        }
    }

    //Remove member from Sorted Set
    public boolean removeFromSet(String key, String... members) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.srem(key, members);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
