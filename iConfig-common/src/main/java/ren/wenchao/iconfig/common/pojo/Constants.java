package ren.wenchao.iconfig.common.pojo;

import com.google.common.base.Joiner;

/**
 * @author rollenholt
 */
public interface Constants {

    Joiner.MapJoiner mapJoiner = Joiner.on('&').withKeyValueSeparator("=");

    Joiner joiner = Joiner.on("|");

}

