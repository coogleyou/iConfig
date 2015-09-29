package ren.wenchao.iconfig.common.conponent.server;

import com.google.common.base.Throwables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriTemplate;
import ren.wenchao.iconfig.common.pojo.AppServerInfo;
import ren.wenchao.iconfig.common.pojo.Constants;
import ren.wenchao.iconfig.common.util.Jsons;
import ren.wenchao.iconfig.common.util.ServerUtil;
import ren.wenchao.iconfig.common.zookeeper.ZkComponent;
import ren.wenchao.iconfig.common.zookeeper.ZkConstrants;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

/**
 * @author rollenholt
 */
@Component
public class ServerRegister {

    @Resource
    private ZkComponent zkComponent;

    @Resource
    private ServletContext servletContext;

    private final Logger logger = LoggerFactory.getLogger(ServerRegister.class);

    public void register(String teamCode, String applicationCode, String profileType) {
        AppServerInfo appServerInfo = ServerUtil.collectAppServerInfo(servletContext);

        String nodeName = Constants.joiner.join(appServerInfo.getHostIp(), appServerInfo.getPort());
        String nodeValue = Jsons.toJson(appServerInfo);

        UriTemplate uriTemplate = new UriTemplate(ZkConstrants.applicationServerListParentNodePath);
        String applicationServerListParentNodePath = uriTemplate.expand(teamCode, applicationCode, profileType).toString();

        try {
            zkComponent.createEphemeral(applicationServerListParentNodePath + nodeName, nodeValue.getBytes());
        } catch (Exception e) {
            logger.error("主机注册失败:{}", e.getMessage(), e);
            throw Throwables.propagate(e);
        }
    }

}
