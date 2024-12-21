# 工程简介



# 快速开始

1. 需要设置下列环境变量

````
--env mysql.host=172.27.208.1
--env mysql.username=root
--env mysql.password=123456
--env minio-weburl=http://172.27.208.1:9000/
--env minio-endpoint=http://172.27.208.1:9000/
--env minio-port=9000
--env nacos.addr=172.27.208.1:8848
--env rabbitmq.host=172.27.208.1
--env rabbitmq.port=5672
--env redis.host=172.27.208.1
--env redis.port=6379


-Drabbitmq_host=gull.rmq.cloudamqp.com
-Drabbitmq_username=yaxusosp
-Drabbitmq_password=zJjIiIpISf6mVMI6ZeaBlBsrG0vIxJKs
-Drabbitmq_virtualHost=yaxusosp
-Dredis_host=apn1-viable-slug-34095.upstash.io
-Dredis_password=b1e8d8fb94604eefa9293ae38576a0ce
-Dredis_port=34095

````
mysql_host=192.168.2.11
mysql_port=6309
mysql_username=root
mysql_password=123456
2.
# 延伸阅读

Thanks for [JetBrains](https://www.jetbrains.com/?from=Cavy)'s `free JetBrains Open Source license(s)`
 
[![LOGO](https://github.com/jeffxjh/Imgur/blob/main/jetbrains.png?raw=true)](https://www.jetbrains.com/?from=Cavy)


开发环境参数配置
````
-Drabbitmq_host=192.168.2.11
-Drabbitmq_username=guest
-Drabbitmq_password=guest
-Drabbitmq_virtualHost=/
-Dredis_host=192.168.2.11
-Dredis_password=123456
-Dredis_port=6379
-Dmysql_host=192.168.2.11
-Dmysql_port=3306
-Dmysql_username=root
-Dmysql_password=123456
````



-Dminio_endpoint=http://193.149.161.195:9000/
-Dminio_weburl=http://193.149.161.195:9000/
-Dmysql_host=192.168.232.129
-Drabbitmq_host=192.168.232.129
-Dredis_host=192.168.232.129
-Dnacos_addr=192.168.232.129:8848
-Des_address=192.168.232.129:9200

# jar包瘦身
依赖分离
将依赖包抽离到lib目录
启动命令变为
java -jar -Dloader.path=./lib cavy-boot.jar


### Docker开启远程API
用vim编辑器修改docker.service文件
````
vi /usr/lib/systemd/system/docker.service
````
需要修改的部分：
````
ExecStart=/usr/bin/dockerd -H fd:// --containerd=/run/containerd/containerd.sock
````
修改后的部分：
````
ExecStart=/usr/bin/dockerd -H tcp://0.0.0.0:2375 -H unix://var/run/docker.sock
````
### 让Docker支持http上传镜像
````
echo '{ "insecure-registries":["192.168.2.11:5000"] }' > /etc/docker/daemon.json
````
修改配置后需要使用如下命令使配置生效
````
systemctl daemon-reload
````
重新启动Docker服务
````
systemctl stop docker
systemctl start docker
````
开启防火墙的Docker构建端口
````
firewall-cmd --zone=public --add-port=2375/tcp --permanent
firewall-cmd --reload
````
### docker启动命令
````
docker run -p 8011:8011 --name cavy -e rabbitmq_host=192.168.2.11 -e rabbitmq_username=guest -e rabbitmq_password=guest -e rabbitmq_virtualHost=/ -e redis_host=192.168.2.11 -e redis_password=123456 -e redis_port=6379 -e mysql_host=192.168.2.11 -e mysql_port=3306 -e mysql_username=root -e mysql_password=123456 -d cavy-backend/cavy-boot:1.0.0-SNAPSHOT


简化版命令 需要docker-maven-plugin使用
docker run -p 8011:8011 -v /opt/cavy-backend/lib:/cavy/lib --name cavy -d 镜像id --restart=always
````
### 前端接口文档
http://localhost:8011/cavy/springdoc/swagger-ui/index.html#/


### todo
数据字典缓存
通过系统启动时缓存字典
再通过添加注解的形式加dto的字段做转换 fieldcode=>fieldname
再监听字典增删改接口更新缓存

|                            |                                                              |                                              |
| -------------------------- | ------------------------------------------------------------ | -------------------------------------------- |
| Pg Source Type             | Taret [MySQL Type](https://dev.mysql.com/doc/refman/8.0/en/data-types.html) | Comment                                      |
| INT                        | INT                                                          |                                              |
| SMALLINT                   | SMALLINT                                                     |                                              |
| BIGINT                     | BIGINT                                                       |                                              |
| SERIAL                     | INT                                                          | Sets AUTO_INCREMENT in its table definition. |
| SMALLSERIAL                | SMALLINT                                                     | Sets AUTO_INCREMENT in its table definition. |
| BIGSERIAL                  | BIGINT                                                       | Sets AUTO_INCREMENT in its table definition. |
| BIT                        | BIT                                                          |                                              |
| BOOLEAN                    | TINYINT(1)                                                   |                                              |
| REAL                       | FLOAT                                                        |                                              |
| DOUBLE PRECISION           | DOUBLE                                                       |                                              |
| NUMERIC                    | DECIMAL                                                      |                                              |
| DECIMAL                    | DECIMAL                                                      |                                              |
| MONEY                      | DECIMAL(19,2)                                                |                                              |
| CHAR                       | CHAR/LONGTEXT                                                |                                              |
| NATIONAL CHARACTER         | CHAR/LONGTEXT                                                |                                              |
| VARCHAR                    | VARCHAR/MEDIUMTEXT/LONGTEXT                                  |                                              |
| NATIONAL CHARACTER VARYING | VARCHAR/MEDIUMTEXT/LONGTEXT                                  |                                              |
| DATE                       | DATE                                                         |                                              |
| TIME                       | TIME                                                         |                                              |
| TIMESTAMP                  | DATETIME                                                     |                                              |
| INTERVAL                   | TIME                                                         |                                              |
| BYTEA                      | LONGBLOB                                                     |                                              |
| TEXT                       | LONGTEXT                                                     |                                              |
| CIDR                       | VARCHAR(43)                                                  |                                              |
| INET                       | VARCHAR(43)                                                  |                                              |
| MACADDR                    | VARCHAR(17)                                                  |                                              |
| UUID                       | VARCHAR(36)                                                  |                                              |
| XML                        | LONGTEXT                                                     |                                              |
| JSON                       | LONGTEXT                                                     |                                              |
| TSVECTOR                   | LONGTEXT                                                     |                                              |
| TSQUERY                    | LONGTEXT                                                     |                                              |
| ARRAY                      | LONGTEXT                                                     |                                              |
| POINT                      | POINT                                                        |                                              |
| LINE                       | LINESTRING                                                   |                                              |
| LSEG                       | LINESTRING                                                   |                                              |
| BOX                        | POLYGON                                                      |                                              |
| PATH                       | LINESTRING                                                   |                                              |
| POLYGON                    | POLYGON                                                      |                                              |
| CIRCLE                     | POLYGON                                                      |                                              |
| TXID_SNAPSHOT              | VARCHAR                                                      |                                              |



Spring4.2之后，可以使用TransactionalEventListener监听事务提交，并在调用方发送event。这种方式需要维护过多的事件及事件处理器，可维护性较差，相对而言上面第一种方案的函数式输入会简单一点。

业务实现：

@Service
@Slf4j
public class UserService extends ServiceImpl<UserDao, User> {
@Autowired
ApplicationEventPublisher eventPublisher;

    @Transactional
    public void add(User user){
        super.save(user);
        eventPublisher.publishEvent(new UserAddEvent(user.getId()));
    }
}
自定义事件：

@Data
public class UserAddEvent extends ApplicationEvent {

    private Integer userId;
 
    public UserAddEvent(Integer userId) {
        this.userId = userId;
    }
}
监听器实现：

@Slf4j
@Component
public class UserListener {

    @Autowired
    EmailService emailService;
 
    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, classes = UserAddEvent.class)
    public void onUserAddEvent(UserAddEvent event) {
        emailService.sendEmail(event.getUserId());
    }
}