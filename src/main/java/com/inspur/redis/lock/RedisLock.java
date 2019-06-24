package com.inspur.redis.lock;

import com.inspur.redis.RedisManager;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.List;
import java.util.UUID;

/**
 * redis 锁的实现
 *
 * User: YANG
 * Date: 2019/6/19-16:55
 * Description: No Description
 */
public class RedisLock {

	/**
	 * 获得锁
	 * @param key
	 * @param timeout
	 * @return
	 * @throws InterruptedException
	 */
	public String getLock(String key, int timeout) throws InterruptedException {
		Jedis jedis = RedisManager.getJedis();
		String value = UUID.randomUUID().toString();
		long endtime = System.currentTimeMillis() + timeout;

		while(System.currentTimeMillis() < endtime){
			if(jedis.setnx(key, value) == 1L){
				jedis.expire(key, timeout);
				return value;
			}
			Thread.sleep(100);
		}
		return null;
	}

	/**
	 * 释放锁
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean releaseLock(String key, String value){
		Jedis jedis = RedisManager.getJedis();

		while(true) {
			jedis.watch(key);   //开启类似于lock的机制:保证当前的可以在下面的事务中进行完成,不允许其他线程碰这个key
			//判断当前获得的key 的值是否是同一个值
			if(value.equals(jedis.get(key))){
				Transaction transaction = jedis.multi();
				transaction.del(key);
				List<Object> list = transaction.exec();
				if(list == null){
					continue;
				}
				return true;
			}
			jedis.unwatch();
			break;
		}

		return false;
	}

	public static void main(String[] args) throws Exception{
		RedisLock redisLock = new RedisLock();
		String lockId = redisLock.getLock("aaa", 10000);
		System.out.println("lockId ---------------->:" + lockId);
		if(lockId != null){
			System.out.println("首次获得lock成功");
		} else {
			System.out.println("首次获得锁失败");
		}
		System.out.println("----------------------------------------------------------------");
		String lockId2 = redisLock.getLock("aaa", 10000);
		if(lockId2 != null){
			System.out.println("再次获得lock成功");
		} else {
			System.out.println("再次获得锁失败");
		}
	}
}
