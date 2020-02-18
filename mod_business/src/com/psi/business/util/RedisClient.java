package com.psi.business.util;

import java.util.List;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisClient
{
  private JedisPool pool;
  private boolean connected;

  public RedisClient()
  {
    this("localhost", 6379);
  }

  public RedisClient(String host, int port)
  {
    try
    {
      this.pool = new JedisPool(new JedisPoolConfig(), host, port);
      this.connected = true;
    }
    catch (Exception e)
    {
      this.connected = false;
    }
  }

  public boolean connected() {
    Jedis b = null;
    try {
      b = borrow();
      return b.isConnected();
    } catch (Exception e) {
      return false;
    } finally {
      if (b != null) revert(b);
    }
  }

  public void operate(Callback callback)
  {
    Jedis jedis = borrow();
    try {
      callback.execute(jedis);
    } finally {
      revert(jedis);
    }
  }

  public String get(String key) {
    Jedis jedis = borrow();
    try {
      return jedis.get(key);
    } finally {
      revert(jedis);
    }
  }

  public String set(String key, String value)
  {
    Jedis jedis = borrow();
    try {
      return jedis.set(key, value);
    } finally {
      revert(jedis);
    }
  }

  public String setex(String key, int seconds, String value) {
    Jedis jedis = borrow();
    try {
      return jedis.setex(key, seconds, value);
    } finally {
      revert(jedis);
    }
  }

  public void del(String key) {
    Jedis jedis = borrow();
    try {
      jedis.del(key);
    } finally {
      revert(jedis);
    }
  }

  public byte[] bGetJ(String key)
  {
    Jedis jedis = borrow();
    try {
      byte[] value = jedis.get(key.getBytes());
      if (value != null) return value;
      return null;
    } finally {
      revert(jedis);
    }
  }

  public String bSetJ(String key, byte[] value) {
    Jedis jedis = borrow();
    try {
      return jedis.set(key.getBytes(), value);
    } finally {
      revert(jedis);
    }
  }

  public boolean exists(String key) {
    Jedis jedis = borrow();
    try {
      return jedis.exists(key).booleanValue();
    } finally {
      revert(jedis);
    }
  }

  public List<String> mget(String[] keys) {
    Jedis jedis = borrow();
    try {
      return jedis.mget(keys);
    } finally {
      revert(jedis);
    }
  }

  public String info() {
    Jedis jedis = borrow();
    try {
      return jedis.info();
    } finally {
      revert(jedis);
    }
  }

  public Set<String> sCopy(String key, String new_key)
  {
    Jedis jedis = borrow();
    try {
      Set<String> oldSets = jedis.smembers(key);
      for (String str : oldSets) {
        jedis.sadd(new_key, new String[] { str });
      }
      return oldSets;
    } finally {
      revert(jedis);
    }
  }

  public void sClear(String key, String oldKey) {
    Jedis jedis = borrow();
    try {
      Set<String> oldSets = jedis.smembers(key);
      for (String str : oldSets) {
        jedis.del(oldKey + ":" + str);
      }
      jedis.del(key);
    } finally {
      revert(jedis);
    }
  }

  public Set<String> sMove(String key, String new_key) {
    Jedis jedis = borrow();
    try {
      Set<String> oldSets = jedis.smembers(key);
      for (String str : oldSets) {
        jedis.smove(key, new_key, str);
      }
      return oldSets;
    } finally {
      revert(jedis);
    }
  }

  public void destory()
  {
    this.pool.destroy();
  }

  public Jedis borrow() {
    return this.pool.getResource();
  }

  public void revert(Jedis jedis) {
    jedis.close();
  }

  public static abstract interface Callback
  {
    public abstract void execute(Jedis paramJedis);
  }
}