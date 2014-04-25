package com.laterooms.typhoon.service;

import java.util.Map;

/**
 * Created by abraithwaite on 17/04/2014.
 */
public interface CommandExecutor {
    Map<String, Object> execute(String command, Map<String, Object> data);
}
