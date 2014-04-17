package com.laterooms.service;

import com.laterooms.dto.ScriptDTO;
import com.laterooms.dto.ScriptEngineResultDTO;

/**
 * Created by abraithwaite on 17/04/2014.
 */
public interface ScriptEngine {
    ScriptEngineResultDTO run(ScriptDTO script);
}
