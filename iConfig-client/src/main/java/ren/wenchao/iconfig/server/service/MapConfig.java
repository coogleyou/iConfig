package ren.wenchao.iconfig.server.service;

import java.io.IOException;
import java.io.StringReader;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author rollenholt
 */
public class MapConfig extends AbstractConfiguration<Map<String, String>> {

    public static Map<String, String> parseProperties(String data) throws IOException {
        if (data == null)
            return Collections.emptyMap();

        Properties p = new Properties();

        p.load(new StringReader(data));

        Map<String, String> map = new LinkedHashMap<>(p.size());

        for (String key : p.stringPropertyNames()) {
            map.put(key, p.getProperty(key));
        }
        return Collections.unmodifiableMap(map);
    }
}
