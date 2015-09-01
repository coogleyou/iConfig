package ren.wenchao.iconfig.core.service;

import org.springframework.stereotype.Service;
import ren.wenchao.iconfig.component.zookeeper.ZkComponent;

import javax.annotation.Resource;

/**
 * @author rollenholt
 */
@Service
public class ApplicationService {

    @Resource
    private ZkComponent zkComponent;
}
