package com.laterooms.typhoon.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by abraithwaite on 17/04/2014.
 */
@Service
public class CommandExecutorImpl implements CommandExecutor {
    @Override
    public Map<String, Object> execute(String command, Map<String, Object> data) {
        Map<String, Object> result = new HashMap<String, Object>();

        switch (Commands.valueOf(command)) {
            case apply_template:
            case email:
                result.put(command, "OK");
                break;
            default:
                throw new RuntimeException("Unknown command " + command);
        }

        return result;
    }
}
