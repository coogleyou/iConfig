package ren.wenchao.iconfig.core.dao;

import org.springframework.stereotype.Repository;
import ren.wenchao.iconfig.core.pojo.vo.ConfigedApplication;

import java.util.List;

/**
 * @author rollenholt
 */
@Repository
public interface ApplicationDao {


    /**
     * 根据用户名查询这个用户所拥有权限的接入iconfig的应用列表
     *
     * @param userName 企业内用户的唯一用户名
     * @return 返回查询到的应用信息
     */
    List<ConfigedApplication> queryConfigedApplications(String userName);

    String queryApplicationName(String applicationCode);

    void joinIConfig(String applicationCode);
}
