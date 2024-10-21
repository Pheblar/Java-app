package by.kulevets.demociproj.utils;

import by.kulevets.demociproj.enumeration.Layer;
import by.kulevets.demociproj.enumeration.LogLevel;

import java.util.HashMap;
import java.util.Map;

public class FluentdUtils {

    private FluentdUtils() {}

    public static Map<String, Object> buildLog(LogLevel level, Layer layer, String dest, String message, Object obj){
        Map<String, Object> log = new HashMap<>();
        log.put("level", level.name());
        log.put("layer", layer.name());
        log.put("destination", dest);
        log.put("message", message);
        log.put("obj", obj);
        return log;
    }
}
