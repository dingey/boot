package com.d.config;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.net.SocketTimeoutException;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisDataException;

@EnableCaching
@SpringBootConfiguration
public class RedisConfig extends CachingConfigurerSupport {

	private final Logger logger = LoggerFactory.getLogger(RedisConfig.class);

	@Value("${spring.redis.host}")
	private String host;

	@Value("${spring.redis.port}")
	private int port;

	@Value("${spring.redis.timeout}")
	private int timeout;

	@Value("${spring.redis.password}")
	private String password;

	@Value("${spring.redis.database}")
	private int database;

	@Value("${spring.redis.pool.max-idle}")
	private int maxIdle;

	@Value("${spring.redis.pool.min-idle}")
	private int minIdle;

	@Value("${spring.redis.expired}")
	private long expired;

	private boolean available = true;

	@Autowired
	private StringRedisTemplate srt;

	@PostConstruct
	public void initMethod() {
		try {
			srt.opsForValue().get("echo");
			logger.info("缓存redis初始化成功。");
		} catch (Exception e) {
			available = false;
			Throwable cause = e.getCause().getCause();
			if (cause instanceof SocketTimeoutException) {
				logger.error("缓存redis初始化失败,原因【{}】。", "连接超时" + cause.getMessage());
			} else if (cause instanceof JedisDataException) {
				logger.error("缓存redis初始化失败,原因【{}】。", "密码错误" + cause.getMessage());
			} else {
				logger.error("缓存redis初始化失败,原因【{}】。", cause.getMessage());
			}
		}
	}

	/**
	 * 注解@Cache key生成规则
	 */
	@Bean
	public KeyGenerator keyGenerator() {
		return new KeyGenerator() {
			@Override
			public Object generate(Object target, Method method,
					Object... params) {
				StringBuilder sb = new StringBuilder();
				sb.append(target.getClass().getName());
				sb.append(method.getName());
				if (params != null && params.length > 0) {
					for (Object obj : params) {
						sb.append(obj.toString());
					}
				}
				return sb.toString();
			}
		};
	}

	@Bean
	public CacheManager cacheManager(RedisTemplate<?, ?> redisTemplate) {
		RedisCacheManager redisCacheManager = new RedisCacheManager(
				redisTemplate);
		redisCacheManager.setDefaultExpiration(expired);// 设置缓存过期时间秒
		return redisCacheManager;
	}

	// @Bean
	public RedisTemplate<Serializable, Serializable> redisTemplate(
			RedisConnectionFactory factory) {
		RedisTemplate<Serializable, Serializable> redisTemplate = new RedisTemplate<Serializable, Serializable>();
		Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(
				Object.class);
		ObjectMapper om = new ObjectMapper();
		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		serializer.setObjectMapper(om);
		redisTemplate.setValueSerializer(serializer);
		redisTemplate.setConnectionFactory(redisConnectionFactory());
		return redisTemplate;
	}

	/**
	 * redis连接的基础设置
	 * 
	 * @Description:
	 * @return
	 */
	// @Bean
	public JedisConnectionFactory redisConnectionFactory() {
		JedisConnectionFactory factory = new JedisConnectionFactory();
		factory.setHostName(host);
		factory.setPort(port);
		factory.setPassword(password);
		factory.setDatabase(database);// 存储的库
		factory.setTimeout(timeout);// 设置连接超时时间
		factory.setUsePool(true);
		factory.setPoolConfig(jedisPoolConfig());
		return factory;
	}

	/**
	 * 连接池配置
	 * 
	 * @Description:
	 * @return
	 */
	// @Bean
	public JedisPoolConfig jedisPoolConfig() {
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxIdle(maxIdle);
		jedisPoolConfig.setMinIdle(minIdle);
		return jedisPoolConfig;
	}

	/**
	 * redis数据操作异常处理 这里的处理：在日志中打印出错误信息，但是放行
	 * 保证redis服务器出现连接等问题的时候不影响程序的正常运行，使得能够出问题时不用缓存
	 * 
	 * @return
	 */
	@Bean
	@Override
	public CacheErrorHandler errorHandler() {
		CacheErrorHandler cacheErrorHandler = new CacheErrorHandler() {
			@Override
			public void handleCacheGetError(RuntimeException e, Cache cache,
					Object key) {
				if (available)
					logger.error("redis异常：key=[{}]", key, e);
			}

			@Override
			public void handleCachePutError(RuntimeException e, Cache cache,
					Object key, Object value) {
				if (available)
					logger.error("redis异常：key=[{}]", key, e);
			}

			@Override
			public void handleCacheEvictError(RuntimeException e, Cache cache,
					Object key) {
				if (available)
					logger.error("redis异常：key=[{}]", key, e);
			}

			@Override
			public void handleCacheClearError(RuntimeException e, Cache cache) {
				if (available)
					logger.error("redis异常：", e);
			}
		};
		return cacheErrorHandler;
	}

}