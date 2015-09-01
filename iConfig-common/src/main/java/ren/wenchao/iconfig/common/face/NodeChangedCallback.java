package ren.wenchao.iconfig.common.face;


import org.apache.curator.framework.recipes.cache.NodeCache;

/**
 * Created by wenchao.ren on 2015/9/1.
 */
public interface NodeChangedCallback {

    void onNodeChanged(String path, NodeCache nodeCache) throws Exception;
}
