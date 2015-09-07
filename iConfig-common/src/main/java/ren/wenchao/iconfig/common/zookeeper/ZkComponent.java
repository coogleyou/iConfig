package ren.wenchao.iconfig.common.zookeeper;

import com.google.common.base.Suppliers;
import com.google.common.base.Throwables;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.springframework.stereotype.Component;
import ren.wenchao.iconfig.common.face.NodeChangedListener;

/**
 * @author rollenholt
 */
@Component
public class ZkComponent {

    private final CuratorFramework client;

    public ZkComponent() {
        client = Suppliers.memoize(() -> {
            RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
            CuratorFramework client1 = CuratorFrameworkFactory.builder()
                    .connectString(ZkConstrants.zkConnectString)
                    .retryPolicy(retryPolicy)
                    .connectionTimeoutMs(1000)
                    .sessionTimeoutMs(5000).build();
            client1.start();
            return client1;
        }).get();
    }

    public CuratorFramework client() {
        return client;
    }

    /**
     * 创建持久节点
     * @param path 节点路径
     * @param payload 节点数据
     */
    public void create(String path, byte[] payload) {
        try {
            client.create().withMode(CreateMode.PERSISTENT).forPath(path, payload);
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }
    }

    /**
     * 创建临时节点
     *
     * @param path    节点路径
     * @param payload 节点数据
     */
    public void createEphemeral(String path, byte[] payload) throws Exception {
        client.create().withMode(CreateMode.EPHEMERAL).forPath(path, payload);
    }

    /**
     * 创建临时顺序节点
     *
     * @param path    节点路径
     * @param payload 节点数据
     */
    public void createEphemeralSequential(String path, byte[] payload) throws Exception {
        client.create().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(path, payload);
    }

    public void setData(String path, byte[] payload) throws Exception {
        client.setData().forPath(path, payload);
    }

    public void setDataAsync(String path, byte[] payload) throws Exception {
        client.getCuratorListenable().addListener((client1, event) -> {
            // examine event for detail
        });
        client.setData().inBackground().forPath(path, payload);
    }

    public void setDataAsyncWithCallback(String path, byte[] payload, BackgroundCallback callback)
            throws Exception {
        client.setData().inBackground(callback).forPath(path, payload);
    }

    public void delete(String path) throws Exception {
        client.delete().forPath(path);
    }

    public void guaranteedDelete(String path) throws Exception {
        client.delete().guaranteed().forPath(path);
    }

    public byte[] getData(String path) throws Exception {
        return client.getData().forPath(path);
    }

    public void watchNodeData(String path, boolean dataIsCompressed, NodeChangedListener callback) throws Exception{
        NodeCache nodeCache = new NodeCache(client, path, dataIsCompressed);
        nodeCache.start(true);
        nodeCache.getListenable().addListener(() -> callback.onNodeChanged(path, nodeCache));
    }

}
