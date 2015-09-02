package ren.wenchao.iconfig.common.face;


import org.apache.curator.framework.recipes.cache.NodeCache;

/**
 * @author wenchao.ren
 *         节点数据变更的回调接口
 */
public interface NodeChangedCallback {

    void onNodeChanged(String path, NodeCache nodeCache) throws Exception;
}
