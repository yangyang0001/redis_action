--
-- Created by IntelliJ IDEA.
-- User: Administrator
-- Date: 2019/6/19
-- Time: 23:35
-- To change this template use File | Settings | File Templates.
--
local pinCiCount = redis.call('incr', KEYS[1]);
--第一次进来设置超时时间
--进来的次数不超过给定的次数就返回1
--KEYS[1] 键
--ARGV[1] 超时时间 seconds
--ARGV[2] 超时时间内不能超过的访问次数
if tonumber(pinCiCount) == 1 then
	redis.call('expire', KEYS[1], ARGV[1]);
	return 1;
elseif tonumber(pinCiCount) < tonumber(ARGV[2]) then
	return 1;
else
	return 0;
end;

