package com.laterooms.service;

import com.laterooms.dto.ScriptDTO;
import com.laterooms.dto.ScriptEngineResultDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by abraithwaite on 17/04/2014.
 */
@Service
public class ScriptEngineImpl implements ScriptEngine {
    Logger logger = LoggerFactory.getLogger("com.laterooms");

    @Autowired
    CommandExecutor commandExecutor;

    @Override
    public ScriptEngineResultDTO run(ScriptDTO script) {
        ScriptEngineResultDTO result = new ScriptEngineResultDTO();
        for (String key : script.getScript().keySet()) {
            logger.info(key);

            Map<String, Object> command = (Map<String, Object>) script.getScript().get(key);

            Map<String, Object> commandResult = commandExecutor.execute((String) command.get("command"), (Map <String, Object>) command.get("data"));
            result.add(commandResult);
        }

        return result;
    }
}
