package ren.wenchao.iconfig.controller;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ren.wenchao.iconfig.common.zookeeper.ZkComponent;
import ren.wenchao.iconfig.server.service.IConfig;

import javax.annotation.Resource;

/**
 * @author rollenholt
 * just for test
 */
@Controller
@RequestMapping(value = "/client/test")
public class ClientTestController {

    @Resource
    private ZkComponent zkComponent;

    private final Supplier<IConfig> supplier = Suppliers.memoize(() -> {
        IConfig iConfig = new IConfig();
        iConfig.setConfigFiles(Lists.newArrayList("/iApplication"));
        iConfig.setZkComponent(zkComponent);
        iConfig.init();
        return iConfig;
    });

    @RequestMapping(value = "/hotDeployment", method = RequestMethod.GET)
    @ResponseBody
    public String testHotDeployment(){
        IConfig iConfig = supplier.get();
        String value = iConfig.get("key");
        return value;
    }
}
