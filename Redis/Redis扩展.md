### Redis扩展

#### 1.主从复制模式搭建

##### 2.4.1 搭建步骤

只需要在从服务器的redis.conf中添加如下配置即可

~~~powershell
slaveof 主服务器IP 主服务器端口
~~~

##### 2.4.2 手动主从切换

步骤：

a. 选择一台从服务器作为新的主服务器，在其上面执行slaveof no one

b. 在其他从服务器上执行slaveof 新的主服务器IP 新的主服务器端口



手动主从切换弊端：

a. 不知道什么时候主服务器会宕机

b. 切换耗时且容易出错



#### 2. spring boot连接sentinel

操作步骤如下：

a. 设置每个redis实例的访问密码和访问主服务器的密码

~~~shell
# 设置密码
requirepass 123456
# 设置访问主服务器密码
masterauth 123456
~~~

b. 设置每个redis实例允许通过网络访问

~~~shell
bind 0.0.0.0
~~~

c. 在sentinel中配置redis主服务器访问密码，并允许通过网络访问sentinel

~~~shell
#设置redis主服务器访问密码
sentinel auth-pass mymaster 123456
#允许通过网络访问sentinel
bind 0.0.0.0
~~~

d. 在application.yml中配置sentinel信息和redis访问密码

~~~shell
spring:
  redis:
    password: 123456
    sentinel:
      # 这里要和sentinel.conf中配置的redis主服务器名称一致
      master: mymaster
      # 每个sentinel实例
      nodes: 192.168.25.199:26381,192.168.25.199:26382,192.168.25.199:26383
~~~

e. 测试

~~~java
@SpringBootTest(classes = RedisApplication.class)
@RunWith(SpringRunner.class)
public class RedisTest {
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testSentinel(){
        redisTemplate.boundValueOps("name").set("zhangsanfeng");
        Object name = redisTemplate.boundValueOps("name").get();
        System.out.println(name);
    }
}
~~~



#### 3. QPS、RT、TPS

**响应时间(RT)**
响应时间是指系统对请求作出响应的时间。
直观上看，这个指标与人对软件性能的主观感受是非常一致的，因为它完整地记录了整个计算机系统处
理请求的时间。由于一个系统通常会提供许多功能，而不同功能的业务逻辑也千差万别，因而不同功能
的响应时间也不尽相同。
在讨论一个系统的响应时间时，通常是指该系统所有功能的**平均响应时间**或者所有功能的**最大响应时间**。

**吞吐量TPS**
吞吐量是指**系统**在单位时间内处理请求的数量。

**每秒查询率QPS**
每秒查询率QPS是对一个**特定的查询服务器**在规定时间内所处理流量多少的衡量标准



#### 4. redis内置集群搭建步骤

a. 准备6个redis实例，用干净的redis(不包含dump.db，如果有删除后再复制)复制6份即可

b. 修改每个redis实例，按照预定好的端口进行分配，修改的配置如下

~~~shell
port 分配的端口
dir "当前redis实例所在的完整路径"
cluster-enabled yes #默认是no
~~~

c. 启动6个redis实例

d. 执行如下命令，开始创建集群

~~~shell
./redis-cli -p 端口(6个实例中的任何一个端口都可以) --cluster create masterhost1:masterport1 masterhost2:masterport2 masterhost3:masterport3 slavehost1:slaveport1 slavehost2:slaveport2 slavehost3:slaveport3 --cluster-replicas n #n指的是每个redis主服务器多少个从服务器，这里选择1即可
~~~

e. 创建过程中提示输入的时候，输入yes即可，注意一定是yes三个字母

f. 测试集群

~~~shell
./redis-cli -p 端口(6个实例中的任何一个端口都可以) -c 
~~~



####5. spring boot 连接redis集群

a. application.yml中配置集群节点信息

~~~shell
spring:
  redis:
    # redis集群配置
    cluster:
      nodes: 192.168.25.199:7001,192.168.25.199:7002,192.168.25.199:7003,
              192.168.25.199:7004,192.168.25.199:7005,192.168.25.199:7006
~~~

b. 测试

~~~java
@SpringBootTest(classes = RedisApplication.class)
@RunWith(SpringRunner.class)
public class RedisTest {
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testSentinel(){
        redisTemplate.boundValueOps("name").set("zhangsanfeng");
        Object name = redisTemplate.boundValueOps("name").get();
        System.out.println(name);
    }

    @Test
    public void testCluster(){
        redisTemplate.boundValueOps("name").set("zhangcuishan");
        Object name = redisTemplate.boundValueOps("name").get();
        System.out.println(name);
    }
}
~~~

