package ren.wenchao.iconfig.server.service;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author rollenholt
 */
public class AbstractConfiguration<T> implements Configuration<T> {

    private final CopyOnWriteArrayList<ConfigListener<T>> listeners = new CopyOnWriteArrayList<>();


    @Override
    public void addListener(ConfigListener<T> listener) {
        if (listener == null) {
            return;
        }
        synchronized (listeners) {
            listeners.add(listener);
        }

    }
}
