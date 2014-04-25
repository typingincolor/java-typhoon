package com.laterooms.typhoon.service;

import com.laterooms.typhoon.dto.ScriptDTO;

/**
 * Created by abraithwaite on 17/04/2014.
 */
public interface ScriptService {
    ScriptDTO create(ScriptDTO script);
    ScriptDTO get(Integer id);
}
