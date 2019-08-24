package br.com.site2019.reviewtutorial.configuration;

import br.com.site2019.reviewtutorial.model.Review;
import br.com.site2019.reviewtutorial.model.Summary;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfiguration {

    private Integer maxTotal;
    private Integer maxIdle;
    private Integer minIdle;
    private Integer port;

    private String hostname;

    public RedisConfiguration(@Value("${redis.jedis.pool.max-total}") Integer maxTotal,
                              @Value("${redis.jedis.pool.max-idle}") Integer maxIdle,
                              @Value("${redis.jedis.pool.min-idle}") Integer minIdle,
                              @Value("${redis.port}") Integer port,
                              @Value("${redis.hostname}") String hostname) {

        this.maxTotal = maxTotal;
        this.maxIdle = maxIdle;
        this.minIdle = minIdle;
        this.port = port;

        this.hostname = hostname;
    }

    @Bean
    public JedisClientConfiguration jedisClientConfiguration() {

        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxTotal(maxTotal);
        poolConfig.setMaxIdle(maxIdle);
        poolConfig.setMinIdle(minIdle);

        return JedisClientConfiguration.builder()
                .usePooling()
                .poolConfig(poolConfig)
                .build();
    }

    @Bean
    public JedisConnectionFactory jedisConnectionFactory(JedisClientConfiguration jedisClientConfiguration) {

        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
        redisConfig.setHostName(hostname);
        redisConfig.setPort(port);

        return new JedisConnectionFactory(redisConfig, jedisClientConfiguration);
    }

    @Bean
    public RedisTemplate<String, Review> reviewDB(JedisConnectionFactory jedisConnectionFactory) {

        RedisTemplate<String, Review> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);

        return redisTemplate;
    }

    @Bean
    public RedisTemplate<String, Summary> summaryDB(JedisConnectionFactory jedisConnectionFactory) {

        RedisTemplate<String, Summary> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);

        return redisTemplate;
    }
}
