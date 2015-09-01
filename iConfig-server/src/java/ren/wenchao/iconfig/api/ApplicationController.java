package ren.wenchao.iconfig.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ren.wenchao.iconfig.core.pojo.vo.ApplicationConfigInfo;
import ren.wenchao.iconfig.core.pojo.vo.ConfigedApplication;
import ren.wenchao.iconfig.core.service.ApplicationService;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * @author rollenholt
 */
@Controller
@RequestMapping(value = "/iconfig/application")
public class ApplicationController {

    @Resource
    private ApplicationService applicationService;

    /**
     * 获取接入iConfig的所有的application
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public List<ConfigedApplication> configedApplications() {
        return Collections.emptyList();
    }

    @RequestMapping(value = "/{applicationCode}", method = RequestMethod.GET)
    @ResponseBody
    public ApplicationConfigInfo applicationConfigInfo(String applicationCode) {
        return new ApplicationConfigInfo();
    }
}
