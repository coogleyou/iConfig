package ren.wenchao.iconfig.common.pojo;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * @author rollenholt
 */
public final class AppServerInfo implements Serializable {

    /**
     * 主机名
     */
    private String hostName;

    /**
     * 主机id
     */
    private String hostIp;

    /**
     * 主机端口
     */
    private int port;

    /**
     * 进程id
     */
    private int pid;

    private AppServerInfo(Builder builder) {
        setHostName(builder.hostName);
        setHostIp(builder.hostIp);
        setPort(builder.port);
        setPid(builder.pid);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getHostIp() {
        return hostIp;
    }

    public void setHostIp(String hostIp) {
        this.hostIp = hostIp;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public static final class Builder {
        private String hostName;
        private String hostIp;
        private int port;
        private int pid;

        private Builder() {
        }

        public Builder withHostName(String val) {
            hostName = val;
            return this;
        }

        public Builder withHostIp(String val) {
            hostIp = val;
            return this;
        }

        public Builder withPort(int val) {
            port = val;
            return this;
        }

        public Builder withPid(int val) {
            pid = val;
            return this;
        }

        public AppServerInfo build() {
            return new AppServerInfo(this);
        }
    }
}
