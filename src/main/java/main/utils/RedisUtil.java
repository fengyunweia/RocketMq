package main.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author fengyunwei
 * @Desc Redis工具类
 */
@Slf4j
@SuppressWarnings("unchecked")
public final class RedisUtil {

    private static final RedisTemplate<String, Object> redisTemplate = SpringUtil.getBean("leRedisTemplate");


    /*******************存储普通对象操作*********************/

    /**
     * @Desc String数据类型，存入普通对象并设置过期时间
     * @Author yangshuai11
     * @Date 2022.11.07
     */
    public static void set(String key, Object value, long timeout) {
        try {
            redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("Redis存入普通对象时出错！key：" + key + "；value：" + value);
        }
    }

    /**
     * @Desc String数据类型，存入缓存
     * @Author yangshuai11
     * @Date 2022.11.02
     */
    public static void set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
        } catch (Exception e) {
            log.error("Redis存入普通对象时出错！key：" + key + "；value：" + value);
        }
    }

    /**
     * @Desc String数据类型，读取缓存数据
     * @Author yangshuai11
     * @Date 2022.11.02
     */
    public static <T> T get(String key) {
        try {
            return (T) redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            log.error("Redis读取普通对象时出错！key：{},e：{}",key,e);
            return null;
        }
    }

    /*******************存储Hash操作*********************/

    /**
     * @Desc 往Hash中存入数据
     * @Author yangshuai11
     * @Date 2022.11.02
     */
    public static void hPut(String key, String field, Object value) {
        try {
            redisTemplate.opsForHash().put(key, field, value);
        } catch (Exception e) {
            log.error("Redis存入Hash对象时出错！key：" + key + "；field：" + field + "；value：" + value);
        }
    }

    /**
     * @Desc 往Hash中存入多个数据
     * @Author yangshuai11
     * @Date 2022.11.02
     */
    public static void hPutAll(String key, Map<String, Object> values) {
        try {
            redisTemplate.opsForHash().putAll(key, values);
        } catch (Exception e) {
            log.error("Redis存入Hash对象时出错！key：" + key + "；value：" + values);
        }
    }

    /**
     * @Desc 获取Hash中的数据
     * @Author yangshuai11
     * @Date 2022.11.02
     */
    public static <T> T hGet(String key, String field) {
        try {
            return (T) redisTemplate.opsForHash().get(key, field);
        } catch (Exception e) {
            log.error("Redis读取Hash对象时出错！key：" + key + "；field：" + field);
            return null;
        }
    }

    /**
     * @Desc 获取多个Hash中的数据
     * @Author yangshuai11
     * @Date 2022.11.02
     */
    public static <V> List<V> hMultiGet(String key, Collection<Object> fields) {
        try {
            return (List<V>) redisTemplate.opsForHash().multiGet(key, fields);
        } catch (Exception e) {
            log.error("Redis读取Hash对象时出错！key：" + key + "；fields：" + fields);
            return null;
        }
    }

    /*******************存储Set相关操作*********************/

    /**
     * @Desc 往Set中存入数据
     * @Author yangshuai11
     * @Date 2022.11.07
     */
    public static long sSet(String key, Object... values) {
        try {
            Long count = redisTemplate.opsForSet().add(key, values);
            return count == null ? 0 : count;
        } catch (Exception e) {
            log.error("Redis存入Set对象时出错！key：" + key + "；values：" + Arrays.toString(values));
            return 0;
        }
    }

    /**
     * @Desc 读取Set中的全部数据
     * @Author yangshuai11
     * @Date 2022.11.07
     */
    public static <V> Set<V> sGetAll(String key) {
        try {
            return (Set<V>) redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            log.error("Redis读取Set全部对象时出错！key：" + key);
            return null;
        }
    }

    /**
     * @Desc 往Set删除数据
     * @Author yangshuai11
     * @Date 2022.11.07
     */
    public static long sDel(String key, Object... values) {
        try {
            Long count = redisTemplate.opsForSet().remove(key, values);
            return count == null ? 0 : count;
        } catch (Exception e) {
            log.error("Redis删除Set对象时出错！key：" + key + "；values：" + Arrays.toString(values));
            return 0;
        }
    }

    /*******************存储List相关操作*********************/

    /**
     * @Desc 往List中存入数据
     * @Author yangshuai11
     * @Date 2022.11.07
     */
    public static long lPush(String key, Object value) {
        try {
            Long count = redisTemplate.opsForList().rightPush(key, value);
            return count == null ? 0 : count;
        } catch (Exception e) {
            log.error("Redis存入List对象时出错！key：" + key + "；value：" + value);
            return 0;
        }
    }

    /**
     * @Desc 往List中存入多个数据
     * @Author yangshuai11
     * @Date 2022.11.07
     */
    public static long lPushAll(String key, Collection<Object> values) {
        try {
            Long count = redisTemplate.opsForList().rightPushAll(key, values);
            return count == null ? 0 : count;
        } catch (Exception e) {
            log.error("Redis存入List对象时出错！key：" + key + "；values：" + values);
            return 0;
        }
    }

    /**
     * @Desc 往List中存入多个数据
     * @Author yangshuai11
     * @Date 2022.11.07
     */
    public static long lPushAll(String key, Object... values) {
        try {
            Long count = redisTemplate.opsForList().rightPushAll(key, values);
            return count == null ? 0 : count;
        } catch (Exception e) {
            log.error("Redis存入List对象时出错！key：" + key + "；values：" + Arrays.toString(values));
            return 0;
        }
    }

    /**
     * @Desc 从List中获取begin到end之间的元素
     * @Author yangshuai11
     * @Date 2022.11.07
     */
    public static <V> List<V> lGet(String key, int start, int end) {
        try {
            return (List<V>) redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            log.error("Redis读取List对象时出错！key：" + key);
            return null;
        }
    }

    /**
     * @Desc 从List中获取所有元素
     * @Author yangshuai11
     * @Date 2022.11.07
     */
    public static <V> List<V> lGetAll(String key) {
        try {
            return (List<V>) redisTemplate.opsForList().range(key, 0, -1);
        } catch (Exception e) {
            log.error("Redis读取List对象时出错！key：" + key);
            return null;
        }
    }

    /*******************存储Zset相关操作*********************/

    /**
     * @Desc 往ZSet中存入数据
     * @Author yangshuai11
     * @Date 2022.11.10
     */
    public static void zSet(String key, Object value, double score) {
        try {
            redisTemplate.opsForZSet().add(key, value, score);
        } catch (Exception e) {
            log.error("Redis存入ZSet对象时出错！key：" + key + "；value：" + value + "；score：" + score);
        }
    }

    /**
     * @Desc 获取ZSet某集合下的全部数据
     * @Author yangshuai11
     * @Date 2022.11.10
     */
    public static <V> Set<V> zGetAll(String key) {
        try {
            //按照排名先后(从小到大)打印指定区间内的元素, -1为打印全部
            return (Set<V>) redisTemplate.opsForZSet().range(key, 0, -1);
        } catch (Exception e) {
            log.error("Redis获取ZSet的全部文件时出错！key：" + key);
            return null;
        }
    }

    /**
     * @Desc 返回Zset集合内元素在指定分数范围内的排名（从小到大）
     * @Author yangshuai11
     * @Date 2022.11.10
     */
    public static <V> Set<V> zRangeByScore(String key, double startScore, double endScore) {
        try {
            //返回Zset集合内元素在指定分数范围内的排名（从小到大）
            return (Set<V>) redisTemplate.opsForZSet().rangeByScore(key, startScore, endScore);
        } catch (Exception e) {
            log.error("Redis获取ZSet时出错！key：" + key);
            return null;
        }
    }

    /**
     * @Desc 删除ZSet集合中的对象
     * @Author yangshuai11
     * @Date 2022.11.10
     */
    public static Long zRemove(String key, Object... value) {
        try {
            return redisTemplate.opsForZSet().remove(key, value);
        } catch (Exception e) {
            log.error("Redis删除ZSet对象时出错！key：" + key + "；value：" + Arrays.toString(value));
            return 0L;
        }
    }

    /**
     * @Desc 删除ZSet指定索引范围的元素
     * @Author yangshuai11
     * @Date 2022.11.10
     */
    public static Long zRemoveRange(String key, long startIndex, long endIndex) {
        try {
            return redisTemplate.opsForZSet().removeRange(key, startIndex, endIndex);
        } catch (Exception e) {
            log.error("Redis删除ZSet指定索引范围的元素时出错！key：" + key);
            return 0L;
        }
    }

    /**
     * @Desc ZSet删除指定分数范围内的元素
     * @Author yangshuai11
     * @Date 2022.11.10
     */
    public static Long zRemoveRangeByScore(String key, double startScore, double endScore) {
        try {
            return redisTemplate.opsForZSet().removeRangeByScore(key, startScore, endScore);
        } catch (Exception e) {
            log.error("Redis删除ZSet指定索引范围的元素时出错！key：" + key);
            return 0L;
        }
    }


    /*******************通用操作*********************/

    /**
     * @Desc 删除单个key
     * @Author yangshuai11
     * @Date 2022.11.02
     */
    public static Boolean delete(String key) {
        try {
            return redisTemplate.delete(key);
        } catch (Exception e) {
            log.error("Redis删除key时出错！key：" + key);
            return false;
        }
    }

    /**
     * @Desc 删除多个key
     * @Author yangshuai11
     * @Date 2022.11.02
     */
    public static long delete(Collection<String> keys) {
        try {
            Long ret = redisTemplate.delete(keys);
            return ret == null ? 0 : ret;
        } catch (Exception e) {
            log.error("Redis删除key时出错！keys：" + keys);
            return 0;
        }
    }

    /**
     * @Desc 指定失效时间，默认时间为秒
     * @Author yangshuai11
     * @Date 2022.11.02
     */
    public static boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            log.error("Redis设置失效时间时出错！key：" + key);
            return false;
        }
    }

    /**
     * @Desc 指定失效时间，指定时间单位
     * @Author yangshuai11
     * @Date 2022.11.02
     */
    public static boolean expire(String key, long time, TimeUnit timeUnit) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, timeUnit);
            }
            return true;
        } catch (Exception e) {
            log.error("Redis设置失效时间时出错！key：" + key);
            return false;
        }
    }
}
