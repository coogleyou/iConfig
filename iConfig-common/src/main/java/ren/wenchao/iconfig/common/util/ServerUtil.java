package ren.wenchao.iconfig.common.util;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.base.Throwables;
import ren.wenchao.iconfig.common.conponent.server.TomcatServer;
import ren.wenchao.iconfig.common.pojo.AppServerInfo;

import javax.servlet.ServletContext;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author rollenholt
 */
public class ServerUtil {

    private static final Supplier<InetAddress> inetAddressSupplier = Suppliers.memoize(() -> {
        try {
            return InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            throw Throwables.propagate(e);
        }
    });

    public static String getHostIp() {
        InetAddress inetAddress = inetAddressSupplier.get();
        return inetAddress.getHostAddress();
    }

    public static String getHostName() {
        InetAddress inetAddress = inetAddressSupplier.get();
        return inetAddress.getHostName();
    }

    public static int getPid() {
        String name = ManagementFactory.getRuntimeMXBean().getName();
        return Integer.valueOf(name.substring(0, name.indexOf('@')));
    }

    /**
     * 获取应用的端口号,暂时仅仅支持tomcat应用,后续考虑在支持jetty
     */
    public static int getPort(ServletContext servletContext) {
        String serverInfo = servletContext.getServerInfo();
        if (serverInfo.startsWith("Apache Tomcat/")) {
            int port = new TomcatServer(servletContext).getPort();
            return port;
        }
        throw new RuntimeException("未知的服务器类型");
    }

    public static AppServerInfo collectAppServerInfo(ServletContext servletContext) {
        AppServerInfo appServerInfo = AppServerInfo.newBuilder()
                .withHostName(getHostName())
                .withHostIp(getHostIp())
                .withPort(getPort(servletContext))
                .withPid(getPid())
                .build();
        return appServerInfo;
    }
}
