package ren.wenchao.iconfig.controller;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ren.wenchao.iconfig.common.conponent.server.TomcatServer;
import ren.wenchao.iconfig.common.zookeeper.ZkComponent;
import ren.wenchao.iconfig.server.service.IConfig;
import ren.wenchao.iconfig.common.conponent.server.ServerRegister;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.ServletContext;

/**
 * @author rollenholt
 *         just for test
 */
@Controller
@RequestMapping(value = "/client/test")
public class ClientTestController {

    @Resource
    private ZkComponent zkComponent;

    @Resource
    private ServerRegister serverRegister;

    @Resource
    private ServletContext servletContext;

    private final Supplier<IConfig> supplier = Suppliers.memoize(() -> {
        IConfig iConfig = new IConfig();
        iConfig.setConfigFiles(Lists.newArrayList("/iApplication"));
        iConfig.setZkComponent(zkComponent);
        iConfig.init();
        return iConfig;
    });

    @RequestMapping(value = "/hotDeployment", method = RequestMethod.GET)
    @ResponseBody
    public String testHotDeployment() {
        IConfig iConfig = supplier.get();
        String value = iConfig.get("key");
        return value;
    }

    @RequestMapping(value = "/registerServer", method = RequestMethod.GET)
    @ResponseBody
    public String registerServer() {
        serverRegister.register("wenchao.ren", "application1");
        return "ok";
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public void test(){
        String serverInfo = servletContext.getServerInfo();
    }

    @PostConstruct
    public void init(){
        String serverInfo = servletContext.getServerInfo();
        if (serverInfo.startsWith("Apache Tomcat/")){
            int port = new TomcatServer(servletContext).getPort();
            System.out.println(port);
        }
        throw new RuntimeException("未知的服务器类型");
    }
}
