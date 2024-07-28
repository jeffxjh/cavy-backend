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
echo '{ "insecure-registries":["192.168.3.101:5000"] }' > /etc/docker/daemon.json
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
````
