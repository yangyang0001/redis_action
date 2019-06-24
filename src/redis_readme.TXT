1.下载redis的windows版本
2.直接安装msi文件即可
3.配置环境变量Path下添加安装的根路径


----------spring整合的时候注意
1.D:/Program Files/Java/maven/repository/redis/clients/jedis/2.4.2/这个版本的redis否则报错
2.spring-data-redis-1.2.0.RELEASE-sources.jar注意版本的选择!
3.太新的可能不兼容东西


###########################################redis持久化######################################
RDB模式和AOF模式！
通过配置appendonly = yes/no 来区分哪种模式!
appendonly = no 就是 RDB模式, 如果appendonly = yes,就是AOF模式

rdb模式是redis默认使用的持久化模式!
rdb持久化模式对应的持久化文件(配置文件中):dbfilename dump.rdb
rdb模式中对应持久化策略是:
#每900秒有1个key发生变化,就持久化!
save 900 1
#每300秒有10个key发生变化,就持久化!
save 300 10
#每60秒有10000个key发生变化,就持久化!
save 60 10000

AOF模式
appendonly = yes 就开启了AOF持久化模式了
aof持久化模式对应的持久化文件(配置文件中):appendfilename "appendonly.aof"
aof持久化对应的策略:
#key有变化就持久化
appendfsync always
#每秒同步一次
# appendfsync everysec
#不同步
# appendfsync no

######配置重写的策略
#表示当前aof文件大小超过上一次aof文件大小的百分之多少的时候会进行重写。如果之前没有重写过，以启动时aof文件大小为准
auto-aof-rewrite-percentage 100
#限制允许重写最小aof文件大小，也就是文件大小小于64mb的时候，不需要进行优化
auto-aof-rewrite-min-size 64mb





定期定时保存rdb文件和aof文件即可!






redis集群:
I.单机集群配置
II.多机集群配置











