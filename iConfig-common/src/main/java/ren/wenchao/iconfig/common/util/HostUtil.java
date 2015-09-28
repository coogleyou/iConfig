package ren.wenchao.iconfig.common.util;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.base.Throwables;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author rollenholt
 */
public class HostUtil {

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

}
