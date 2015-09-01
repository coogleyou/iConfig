package ren.wenchao.iconfig.zookeeper;

import com.google.common.base.Suppliers;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.Watcher;

import java.util.List;

/**
 * @author rollenholt
 */
public class ZkComponent {

    private final CuratorFramework client;

    public ZkComponent(final String connectString) {
        client = Suppliers.memoize(() -> {
            RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
            CuratorFramework client1 = CuratorFrameworkFactory.builder()
                    .connectString(connectString)
                    .retryPolicy(retryPolicy)
                    .connectionTimeoutMs(1000)
                    .sessionTimeoutMs(5000).build();
            client1.start();
            return client1;
        }).get();
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

    public List<String> getChildren(String path) throws Exception {
        return client.getChildren().forPath(path);
    }

    public List<String> watchedGetChildren(CuratorFramework client, String path, Watcher watcher)
            throws Exception {
        return client.getChildren().usingWatcher(watcher).forPath(path);
    }
}
