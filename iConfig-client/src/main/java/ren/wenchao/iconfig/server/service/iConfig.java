package ren.wenchao.iconfig.server.service;

import com.google.common.collect.Maps;

import java.util.concurrent.ConcurrentMap;

/**
 * @author rollenholt
 * 最终业务系统使用的类
 */
public class iConfig {

    private static final ConcurrentMap<String, String> allConfig = Maps.newConcurrentMap();
}
