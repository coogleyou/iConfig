package ren.wenchao.iconfig.common.conponent.server;

import com.google.common.base.Joiner;
import com.google.common.base.Throwables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriTemplate;
import ren.wenchao.iconfig.common.util.HostUtil;
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

    private final Joiner joiner = Joiner.on("|");

    private final Logger logger = LoggerFactory.getLogger(ServerRegister.class);

    public void register(String teamCode, String applicationCode) {
        String hostName = HostUtil.getHostName();
        String hostIp = HostUtil.getHostIp();
        int port = fetchPort();
        String nodeValue = joiner.join(hostIp, port);
        UriTemplate uriTemplate = new UriTemplate(ZkConstrants.applicationServerListParentNodePath);
        String applicationServerListParentNodePath = uriTemplate.expand(teamCode, applicationCode).toString();
        try {
            zkComponent.createEphemeral(applicationServerListParentNodePath + nodeValue, hostName.getBytes());
        } catch (Exception e) {
            logger.error("主机注册失败:{}", e.getMessage(), e);
            throw Throwables.propagate(e);
        }
    }

    private int fetchPort() {
        String serverInfo = servletContext.getServerInfo();
        if (serverInfo.startsWith("Apache Tomcat/")) {
            int port = new TomcatServer(servletContext).getPort();
            return port;
        }
        throw new RuntimeException("未知的服务器类型");
    }
}
