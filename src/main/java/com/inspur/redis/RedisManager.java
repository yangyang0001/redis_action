package com.inspur.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * User: YANG
 * Date: 2019/6/19-16:42
 * Description: No Description
 */
public class RedisManager {

	private static JedisPoolConfig poolConfig = new JedisPoolConfig();
	private static JedisPool jedisPool = null;
	static {
		poolConfig.setMaxIdle(10);			//最大空闲连接数
		poolConfig.setMaxTotal(100);		//最大连接数
		poolConfig.setMaxWaitMillis(1000);	//最长等待时间
		poolConfig.setTestOnBorrow(true);
		jedisPool = new JedisPool(poolConfig,"192.168.120.150", 6379);	//初始化redisPool,通过他得到redis
	}

	public static Jedis getJedis(){
		return jedisPool.getResource();
	}

}
