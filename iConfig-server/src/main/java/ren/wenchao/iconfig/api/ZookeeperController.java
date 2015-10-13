package ren.wenchao.iconfig.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
import java.util.List;

/**
 * Created by wenchao.ren on 2015/9/1.
 */
@Controller
@RequestMapping(value = "/iconfig/zookeeper")
public class ZookeeperController {

    @RequestMapping(value = "/node/{path}", method = RequestMethod.GET)
    @ResponseBody
    public List<Object> queryNodeData(String path) {
        return Collections.emptyList();
    }

}
