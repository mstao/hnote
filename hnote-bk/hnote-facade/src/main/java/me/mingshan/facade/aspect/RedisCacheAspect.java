package me.mingshan.facade.aspect;


import me.mingshan.facade.annotation.RedisCache;
import me.mingshan.facade.annotation.RedisEvict;
import me.mingshan.facade.config.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.TimeoutUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
public class RedisCacheAspect {
    public static final Logger logger = LoggerFactory.getLogger(RedisCacheAspect.class);

    @Autowired 
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 从Redis获取缓存的数据或者将数据缓存到Redis
     * @param pjp
     * @return 获取到的数据
     * @throws Throwable 
     */
    @Around("execution(* com.lightblog.service..*Impl.select*(..))" +
            "|| execution(* com.lightblog.service..*Impl.get*(..))" +
            "|| execution(* com.lightblog.service..*Impl.find*(..))" +
            "|| execution(* com.lightblog.service..*Impl.search*(..))")
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Object cache(ProceedingJoinPoint pjp) throws Throwable {
        // 生成key
        String key = getKey(pjp);

        if (logger.isDebugEnabled()){
            logger.debug("已生成key = {}" + key);
        }

        // 得到目标方法
        Method targetMethod = getTargetMethod(pjp);
        // 得到被代理的方法上的注解
        Class<?> modelType = targetMethod.getAnnotation(RedisCache.class).type();
        String hashName = modelType.getName();

        // 利用Redis的Hash数据类型（散列）
        HashOperations opsForHash = redisTemplate.opsForHash();
        // 检查redis中是否有缓存
        String value = (String) opsForHash.get(hashName, key);

        // 最终返回结果
        Object result = null;
        // 判断缓存是否命中
        if (value != null) {
            // 缓存命中
            if (logger.isDebugEnabled()) {
                logger.debug("缓存命中, value = {}", value);
            }

            // 得到被代理方法的返回值类型
            Class<?> returnType = ((MethodSignature) pjp.getSignature()).getReturnType();

            // 反序列化从缓存中拿到的json
            result = deserialize(value, returnType, modelType);

            if (logger.isDebugEnabled()) {
                logger.debug("反序列化结果 = {}", result);
            }
        } else {
            // 缓存未命中
            if (logger.isDebugEnabled()) {
                logger.debug("缓存未命中");
            }

            // 跳过缓存,到后端查询数据
            result = pjp.proceed(pjp.getArgs());
            // 序列化查询结果
            String jsonStr = serialize(result);

            // 获取设置的缓存时间
            int timeout = targetMethod.getAnnotation(RedisCache.class).expire();
            // 如果没有设置过期时间,则无限期缓存(默认-1) 
            if (timeout <= 0) {
                opsForHash.put(hashName, key, jsonStr);  
            } else {
                final TimeUnit unit = TimeUnit.SECONDS;
                final long rawTimeout = TimeoutUtils.toMillis(timeout, unit);
                // 设置缓存时间  
                redisTemplate.execute(new RedisCallback<Object>() {
                    @Override
                    public Object doInRedis(RedisConnection redisConn) throws DataAccessException {
                        // 配置文件中指定了这是一个String类型的连接
                        // 所以这里向下强制转换一定是安全的
                        StringRedisConnection conn = (StringRedisConnection) redisConn;
                        // 判断hash名是否存在
                        // 如果不存在，创建该hash并设置过期时间
                        if (!conn.exists(hashName)) {
                            conn.hSet(hashName, key, jsonStr);
                            conn.expire(hashName, rawTimeout);
                        } else {
                            conn.hSet(hashName, key, jsonStr);
                        }

                        return null;
                    }
                });
            }
        }

        return result;
    }

    /**
     * 在方法调用前清除缓存，然后调用业务方法
     * @param jp
     * @return 获取到的数据
     * @throws Throwable
     */
    @Around("execution(* com.lightblog.service..*Impl.delete*(..))" +
            "|| execution(* com.lightblog.service..*Impl.remove*(..))")
    @SuppressWarnings("rawtypes")
    public Object evictCache(ProceedingJoinPoint pjp) throws Throwable {
        // 得到目标的方法
        Method targetMethod = getTargetMethod(pjp);

        // 得到被代理的方法上的注解
        Class modelType = targetMethod.getAnnotation(RedisEvict.class).type();

        if (logger.isDebugEnabled()) {
            logger.debug("清空缓存:{}", modelType.getName());
        }

        // 清除对应缓存
        redisTemplate.delete(modelType.getName());

        return pjp.proceed(pjp.getArgs());
    }

    /**
     * 更新缓存的数据
     * @return 新获取的数据
     */
    @Around("execution(* com.lightblog.service..*Impl.update*(..))" +
            "|| execution(* com.lightblog.service..*Impl.insert*(..))" +
            "|| execution(* com.lightblog.service..*Impl.save*(..))")
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Object updateCache(ProceedingJoinPoint pjp) throws Throwable {
        // 生成key
        String key = getKey(pjp);

        if (logger.isDebugEnabled()){
            logger.debug("已生成key = {}" + key);
        }

        // 得到目标方法
        Method targetMethod = getTargetMethod(pjp);
        // 得到被代理的方法上的注解
        Class<?> modelType = targetMethod.getAnnotation(RedisCache.class).type();
        String hashName = modelType.getName();

        // 利用Redis的Hash数据类型（散列）
        HashOperations opsForHash = redisTemplate.opsForHash();

        // 跳过缓存,到后端查询数据
        Object result = pjp.proceed(pjp.getArgs());
        // 序列化查询结果
        String jsonStr = serialize(result);
        if (logger.isDebugEnabled()) {
            logger.debug("序列化结果 = {}", result);
        }

        // 获取设置的缓存时间
        int timeout = targetMethod.getAnnotation(RedisCache.class).expire();
        // 如果没有设置过期时间,则无限期缓存(默认-1) 
        if (timeout <= 0) {
            opsForHash.put(hashName, key, jsonStr);  
        } else {
            final TimeUnit unit = TimeUnit.SECONDS;
            final long rawTimeout = TimeoutUtils.toMillis(timeout, unit);
            // 设置缓存时间  
            redisTemplate.execute(new RedisCallback<Object>() {
                @Override
                public Object doInRedis(RedisConnection redisConn) throws DataAccessException {
                    // 配置文件中指定了这是一个String类型的连接
                    // 所以这里向下强制转换一定是安全的
                    StringRedisConnection conn = (StringRedisConnection) redisConn;
                    // 判断hash名是否存在
                    // 如果不存在，创建该hash并设置过期时间
                    if (!conn.exists(hashName)) {
                        conn.hSet(hashName, key, jsonStr);
                        conn.expire(hashName, rawTimeout);
                    } else {
                        conn.hSet(hashName, key, jsonStr);
                    }

                    return null;
                }
            });
        }

        return result;
    }

    /**
     * 得到目标方法
     * @param pjp
     * @return 目标方法
     * @throws SecurityException 
     * @throws NoSuchMethodException 
     */
    private Method getTargetMethod(ProceedingJoinPoint pjp) throws NoSuchMethodException,
                                            SecurityException {
        Signature sig = pjp.getSignature();
        if (!(sig instanceof MethodSignature)) {
            throw new IllegalArgumentException("该注解只能用于方法");
        }
        MethodSignature msig = (MethodSignature) sig;
        Object target = pjp.getTarget();
        Method targetMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
        return targetMethod;
    }

    /**
     * 通过类名，方法名和参数来获取对应的key
     * @param pjp
     * @return 生成的key
     */
    private String getKey(ProceedingJoinPoint pjp) {
        // 获取类名
        String clazzName = pjp.getTarget().getClass().getName();
        // 获取方法名
        String methodName = pjp.getSignature().getName();
        // 方法参数
        Object[] args = pjp.getArgs();
        // 生成key
        return generateKey(clazzName, methodName, args);
    }

    /**
     * 生成缓存需要的key
     * @param clazzName
     * @return 生成的key
     */
    private String generateKey(String clazzName, String methodName, Object[] args) {
        StringBuffer sb = new StringBuffer(clazzName);
        sb.append(Constants.ELIMITER);
        sb.append(methodName);
        sb.append(Constants.ELIMITER);
        if (args != null) {
            for (Object arg : args) {
                sb.append(arg);
                sb.append(Constants.ELIMITER);
            }
        }

        // 去除最后一个分隔符
        sb.replace(sb.length() - 1, sb.length(), Constants.ELIMITER);
        return sb.toString();
    }

    /**
     * 序列化数据
     * @param source
     * @return json字符串
     */
    private String serialize(Object source) {
        return JSON.toJSONString(source);
    }

    /**
     * 反序列化
     * @param source
     * @param clazz
     * @param modelType
     * @return 反序列化的数据
     */
    private Object deserialize(String source, Class<?> clazz, Class<?> modelType) {
        // 判断是否为List
        if (clazz.isAssignableFrom(List.class)) {
            return JSON.parseArray(source, modelType);
        }

        // 正常反序列化
        return JSON.parseObject(source, clazz);
    }
}
