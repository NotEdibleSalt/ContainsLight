package com.ss.contains.light.util;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

/**
 * 雪花id工具
 *
 * @author NotEdibleSalt
 * @version 1.0
 */
public class SnowFlakeUtils {

    /**
     * 机器id
     */
    private static final long workerId = 0L;
    /**
     * 机房id
     */
    private static final long datacenterId = 0L;

    private static final Snowflake snowflake = IdUtil.getSnowflake(workerId, datacenterId);

    /**
     * 生成id
     *
     * @return java.lang.String
     * @author NotEdibleSalt
     **/
    public static String getId() {
        return String.valueOf(snowflake.nextId());
    }

    /**
     * 生成带有指定前缀的id
     *
     * @param prefix 前缀
     * @return java.lang.String
     * @author NotEdibleSalt
     **/
    public static String createStr(String prefix) {

        return prefix + SnowFlakeUtils.getId();
    }

}

