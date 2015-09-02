package ren.wenchao.iconfig.server.callback;

import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ren.wenchao.iconfig.common.face.NodeChangedCallback;

/**
 * @author wenchao.ren
 * 节点数据变更的回调接口实现
 */
public class NodeChangedCallbackImpl implements NodeChangedCallback {

    private final Logger logger = LoggerFactory.getLogger(NodeChangedCallbackImpl.class);

    @Override
    public void onNodeChanged(String path, NodeCache nodeCache) throws Exception {
        logger.info("data of path[{path}] has changed", path);
        ChildData currentData = nodeCache.getCurrentData();
        if (currentData == null) {
            throw new RuntimeException(String.format("node of path: %s do not exists", path));
        }
        String currentDataStr = new String(currentData.getData());
        logger.info("current data is : {}", currentDataStr);

        //todo other process
    }

}
