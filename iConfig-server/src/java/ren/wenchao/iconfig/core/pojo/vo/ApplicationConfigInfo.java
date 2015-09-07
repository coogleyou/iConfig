package ren.wenchao.iconfig.core.pojo.vo;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Map;

/**
 * @author rollenholt
 */
public class ApplicationConfigInfo {

    /**
     * 应用编号
     */
    private String applicationCode;

    /**
     * 应用名称
     */
    private String applicationName;

    /**
     * resources 目录下的配置信息
     * key为resources目录下的properties文件名称
     * value为properties文件的内容
     */
    private Map<String, String> resourcesDirConfigInfo;

    /**
     * prefile 目录下面的配置信息
     * key为resources目录下的properties文件名称
     * value为properties文件的内容
     */
    private Map<String, String> prefileDirConfigInfo;

    public String getApplicationCode() {
        return applicationCode;
    }

    public void setApplicationCode(String applicationCode) {
        this.applicationCode = applicationCode;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public Map<String, String> getResourcesDirConfigInfo() {
        return resourcesDirConfigInfo;
    }

    public void setResourcesDirConfigInfo(Map<String, String> resourcesDirConfigInfo) {
        this.resourcesDirConfigInfo = resourcesDirConfigInfo;
    }

    public Map<String, String> getPrefileDirConfigInfo() {
        return prefileDirConfigInfo;
    }

    public void setPrefileDirConfigInfo(Map<String, String> prefileDirConfigInfo) {
        this.prefileDirConfigInfo = prefileDirConfigInfo;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
