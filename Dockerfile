FROM tomcat:jre21
ADD cavy-boot/target/cavy-boot.jar //
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -Xms516M -Xmx516M -Dnacos_addr=$LOCALHOST -Drabbitmq_host=$LOCALHOST -Drabbitmq_username=guest -Drabbitmq_password=guest -Drabbitmq_virtualHost=/ -Dredis_host=$LOCALHOST -Dredis_password=123456 -Dredis_port=6379 -Ddb_host=$LOCALHOST -Ddb_port=5432 -Ddb_username=postgres -Ddb_password=123456 -Dloader.path=/cavy/lib -jar /cavy-boot.jar $PARAMS"]
