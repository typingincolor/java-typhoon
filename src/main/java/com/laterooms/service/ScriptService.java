package com.laterooms.service;

import com.laterooms.dto.ScriptDTO;

/**
 * Created by abraithwaite on 17/04/2014.
 */
public interface ScriptService {
    ScriptDTO create(ScriptDTO script);
    ScriptDTO get(Integer id);
}
