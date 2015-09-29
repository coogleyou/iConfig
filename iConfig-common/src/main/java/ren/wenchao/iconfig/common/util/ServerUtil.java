package ren.wenchao.iconfig.common.util;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.base.Throwables;

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

}
