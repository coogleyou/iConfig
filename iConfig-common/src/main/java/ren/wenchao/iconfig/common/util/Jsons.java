package ren.wenchao.iconfig.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.base.Throwables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author rollenholt
 */
public class Jsons {

    private static final ObjectMapper mapper = new ObjectMapper();

    private static final Logger logger = LoggerFactory.getLogger(Jsons.class);

    public static String toJson(Object object) {
        Preconditions.checkNotNull(object);
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            logger.error("can not serialize object to json: {}", e.getMessage(), e);
            throw Throwables.propagate(e);
        }
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(json));
        Preconditions.checkNotNull(clazz);
        try {
            return mapper.readValue(json, clazz);
        } catch (IOException e) {
            logger.error("can not serialize json to object: {}", e.getMessage(), e);
            throw Throwables.propagate(e);
        }
    }
}
