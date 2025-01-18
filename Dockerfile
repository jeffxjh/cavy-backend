FROM tomcat:jre21
ADD cavy-boot/target/cavy-boot.jar //
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -Xms516M -Xmx516M -Dnacos_addr=$LOCALHOST -Drabbitmq_host=$LOCALHOST -Drabbitmq_username=guest -Drabbitmq_password=guest -Drabbitmq_virtualHost=/ -Dredis_host=$LOCALHOST -Dredis_password=123456 -Dredis_port=6379 -Ddb_host=$LOCALHOST -Ddb_port=5432 -Ddb_username=postgres -Ddb_password=123456 -Dloader.path=/cavy/lib -jar /cavy-boot.jar $PARAMS"]


#启动命令
#说明 将env后面的值再配置到jenkins环境变量当中即可.需要注意的是启动命令的-e参数必须在run命令后面
#docker run -p 8011:8011 -e LOCALHOST="192.168.2.2"   --name cavy -d xujh/cavy:latest