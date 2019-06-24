package com.inspur.redis.lua;

import com.inspur.redis.RedisManager;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

/**
 * User: YANG
 * Date: 2019/6/19-23:11
 * Description: No Description
 */
public class LuaDemoTest {

	public static void main(String[] args){
		Jedis jedis = RedisManager.getJedis();

		String luaString = "local pinCiCount = redis.call('incr', KEYS[1]);\n" +
				"if tonumber(pinCiCount) == 1 then\n" +
				"\tredis.call('expire', KEYS[1], ARGV[1]);\n" +
				"\treturn 1;\n" +
				"elseif tonumber(pinCiCount) < tonumber(ARGV[2]) then\n" +
				"\treturn 1;\n" +
				"else\n" +
				"\treturn 0;\n" +
				"end;";

		//添加参数
		List<String> keys = new ArrayList<String>();
		keys.add("ip:limit:192.168.120.150");
		List<String> argvs = new ArrayList<String>();
		argvs.add("6000");
		argvs.add("10");

		Object object = jedis.eval(luaString, keys, argvs);
		System.out.println(object);

	}
}
