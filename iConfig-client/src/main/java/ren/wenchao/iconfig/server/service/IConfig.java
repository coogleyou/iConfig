package ren.wenchao.iconfig.server.service;

import com.google.common.base.Splitter;
import com.google.common.base.Throwables;
import com.google.common.collect.Maps;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ren.wenchao.iconfig.common.zookeeper.ZkComponent;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicReference;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;

/**
 * @author rollenholt
 * 最终业务系统使用的类
 */
public class IConfig {

    /**
     * 所有在iConfig中注册的properties配置文件名称集合
     */
    private List<String> configFiles;

    private ZkComponent zkComponent;

    private AtomicReference<Boolean> isFirstFetch = new AtomicReference<>(true);

    private static final ConcurrentMap<String, String> allConfig = Maps.newConcurrentMap();

    private static final Logger logger = LoggerFactory.getLogger(IConfig.class);

    /**
     * factory init-method
     */
    public void init() {
        try {
            for (String configFile : configFiles) {
                fetchAndListene(configFile);
            }
        } catch (Exception e) {
            logger.error("初始化IConfig出现异常: {}", e.getMessage(), e);
            throw Throwables.propagate(e);
        }
    }

    /**
     * 根据在iConfig中配置的属性的key获取对应的值
     *
     * @param key 属性的key
     * @return 返回配置的值, 如果配置的值为""或者为null 那么抛出RuntimeException
     */
    public String get(String key) {
        checkArgument(!isNullOrEmpty(key), "查询配置信息时key不能为空或者null");
        String value = allConfig.get(key);
        if (isNullOrEmpty(value)) {
            logger.error("根据key:[{}]没有在iConfig配置中心中找到对应的配置", key);
            throw new RuntimeException("根据key:[" + key + "]没有在iConfig配置中心中找到对应的配置");
        }
        return value;
    }

    private void fetchAndListene(String path) throws Exception {
        CuratorFramework client = zkComponent.client();
        // make fileName equals with node path
        NodeCache nodeCache = new NodeCache(client, path, false);
        // 第一次启动的时候就把值缓存在cache中
        nodeCache.start(true);
        nodeCache.getListenable().addListener(() -> {
            logger.info("data of path[{path}] has changed", path);
            Map<String, String> configMap = processNodeData(path, nodeCache);
            allConfig.putAll(configMap);
        });
        if (isFirstFetch.compareAndSet(true, false)) {
            Map<String, String> configMap = processNodeData(path, nodeCache);
            allConfig.putAll(configMap);
        }
    }

    private Map<String, String> processNodeData(String path, NodeCache nodeCache) {
        ChildData currentData = nodeCache.getCurrentData();
        if (currentData == null) {
            throw new RuntimeException(String.format("node of path: %s do not exists", path));
        }
        String currentDataStr = new String(currentData.getData());
        logger.info("current data is : {}", currentDataStr);
        List<String> strings = Splitter.on("=").splitToList(currentDataStr);
        Map<String, String> map = Maps.newHashMap();
        map.put(strings.get(0), strings.get(1));
        return map;
    }

    public void setConfigFiles(List<String> configFiles) {
        this.configFiles = configFiles;
    }

    public void setZkComponent(ZkComponent zkComponent) {
        this.zkComponent = zkComponent;
    }
}
