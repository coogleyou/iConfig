package ren.wenchao.iconfig.common.pojo;

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

    private int pid;


    private Type type;

    private String token;

    public enum Type {
        dev, beta, prod
    }
}
