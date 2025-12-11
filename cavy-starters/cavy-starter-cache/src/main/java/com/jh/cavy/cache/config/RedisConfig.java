package com.jh.cavy.cache.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;


@EnableCaching
@Configuration
public class RedisConfig {

    @Value("${spring.data.redis.host:127.0.0.1}")
    private String host;
    @Value("${spring.data.redis.database:0}")
    private Integer database;
    @Value("${spring.data.redis.port:6379}")
    private Integer port;
    @Value("${spring.data.redis.password:}")
    private String pwd;
    @Value("${spring.data.redis.cluster.nodes:}")
    private String clusterNodes;
    @Value("${spring.data.redis.cluster.max-redirects:3}")
    private int maxRedirects;

    @Primary
    @Bean(name = "jedisPoolConfig")
    @ConfigurationProperties(prefix = "spring.data.redis.pool")
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxWait(Duration.ofMillis(10000));
        return jedisPoolConfig;
    }

    // ========== 集群模式：仅当 mode=cluster 时加载 ==========
    @Bean(name="redisConnectionFactory")
    @ConditionalOnProperty(name = "spring.data.redis.mode", havingValue = "cluster")
    @Primary // 集群模式下优先使用该 Bean
    public JedisConnectionFactory clusterRedisConnectionFactory(JedisPoolConfig jedisPoolConfig) {
        // 1. 构建集群配置
        RedisClusterConfiguration clusterConfig = new RedisClusterConfiguration();
        Set<RedisNode> nodeSet = new HashSet<>();
        String[] nodes = clusterNodes.split(",");
        for (String node : nodes) {
            String[] hostAndPort = node.split(":");
            nodeSet.add(new RedisNode(hostAndPort[0], Integer.parseInt(hostAndPort[1])));
        }
        clusterConfig.setClusterNodes(nodeSet);
        clusterConfig.setMaxRedirects(maxRedirects);

        // 2. 设置密码
        if (!StringUtils.hasText(pwd)) {
            clusterConfig.setPassword(pwd);
        }

        // 3. 构建客户端配置
        JedisClientConfiguration jedisClientConfig = JedisClientConfiguration.builder()
                                                             .usePooling()
                                                             .poolConfig(jedisPoolConfig)
                                                             .build();

        return new JedisConnectionFactory(clusterConfig, jedisClientConfig);
    }

    // ========== 单机模式：仅当 mode=standalone 时加载 ==========
    @Bean(name="redisConnectionFactory")
    @ConditionalOnProperty(name = "spring.data.redis.mode", havingValue = "standalone", matchIfMissing = true)
    @Primary // 单机模式下优先使用该 Bean
    public JedisConnectionFactory standaloneRedisConnectionFactory(JedisPoolConfig jedisPoolConfig) {
        RedisStandaloneConfiguration standaloneConfig = new RedisStandaloneConfiguration();
        standaloneConfig.setHostName(host);
        standaloneConfig.setDatabase(database);
        if (!StringUtils.hasText(pwd)) {
            standaloneConfig.setPassword(pwd);
        }
        standaloneConfig.setPort(port);
        JedisClientConfiguration.JedisPoolingClientConfigurationBuilder jpcb =
                (JedisClientConfiguration.JedisPoolingClientConfigurationBuilder) JedisClientConfiguration.builder();
        jpcb.poolConfig(jedisPoolConfig);
        JedisClientConfiguration jedisClientConfiguration = jpcb.build();
        return new JedisConnectionFactory(standaloneConfig, jedisClientConfiguration);
    }

    /**
     * 配置redisTemplate针对不同key和value场景下不同序列化的方式
     * <p>
     * <p>
     * <p>
     * <p>
     * DefaultTyping有四个选项：
     * <p>
     * JAVA_LANG_OBJECT: 当对象属性类型为Object时生效；
     * <p>
     * OBJECT_AND_NON_CONCRETE: 当对象属性类型为Object或者非具体类型（抽象类和接口）时生效；
     * <p>
     * NON_CONCRETE_AND+_ARRAYS: 同上,另外所有的数组元素的类型都是非具体类型或者对象类型；
     * <p>
     * NON_FINAL: 对所有非final类型或者非final类型元素的数组。
     *
     * @param factory Redis连接工厂
     * @return
     */
    @Primary
    @Bean(name = "redisTemplate")
    public RedisTemplate<String, Object> redisTemplate(@Qualifier("redisConnectionFactory") RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        // GenericJackson2JsonRedisSerializer 序列化器在存值是保存了class信息，反序列化可以直接强转为对象
        // template.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        template.setKeySerializer(stringRedisSerializer);
        template.setHashKeySerializer(stringRedisSerializer);
        // Jackson2JsonRedisSerializer 序列化器 存储是纯json格式，反序列化是map，必须在创建时指定classType
        // 这里采用指定默认序列化的方式，默认也会存class类型，也可以反序列化强转为对象类型
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.WRAPPER_ARRAY);
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(objectMapper,Object.class);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }

    @Bean("lockluascript")
    public DefaultRedisScript<Long> getlockRedisScript() {
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        redisScript.setLocation(new ClassPathResource("lua/lock.lua"));
        redisScript.setResultType(Long.class);
        return redisScript;
    }

    @Bean("unlockluascript")
    public DefaultRedisScript<Long> getunLockRedisScript() {
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        redisScript.setLocation(new ClassPathResource("lua/unlock.lua"));
        redisScript.setResultType(Long.class);
        return redisScript;
    }


}