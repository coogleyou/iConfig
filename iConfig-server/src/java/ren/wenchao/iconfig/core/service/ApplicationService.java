package ren.wenchao.iconfig.core.service;

import org.springframework.stereotype.Service;
import ren.wenchao.iconfig.common.zookeeper.ZkComponent;
import ren.wenchao.iconfig.core.dao.ApplicationDao;
import ren.wenchao.iconfig.core.pojo.vo.ApplicationConfigInfo;
import ren.wenchao.iconfig.core.pojo.vo.ConfigedApplication;

import javax.annotation.Resource;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;

/**
 * @author rollenholt
 */
@Service
public class ApplicationService {

    @Resource
    private ZkComponent zkComponent;

    @Resource
    private ApplicationDao applicationDao;

    /**
     * 根据用户名查询这个用户所拥有权限的接入iconfig的应用列表
     *
     * @param userName 企业内用户的唯一用户名
     * @return 返回查询到的应用信息
     */
    public List<ConfigedApplication> queryConfigedApplications(String userName) {
        checkArgument(!isNullOrEmpty(userName));
        return applicationDao.queryConfigedApplications(userName);
    }

    public ApplicationConfigInfo queryApplicationConfigInfo(String applicationCode) {
        checkArgument(!isNullOrEmpty(applicationCode));
        //todo communicate with zk
        return new ApplicationConfigInfo();
    }
}
