package ren.wenchao.iconfig.server.service;


public interface Configuration<T> {

    void addListener(ConfigListener<T> listener);

    interface ConfigListener<T> {
        void onLoad(T conf);
    }

}
