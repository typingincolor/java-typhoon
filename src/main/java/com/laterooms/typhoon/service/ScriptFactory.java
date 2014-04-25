package com.laterooms.typhoon.service;

import com.laterooms.typhoon.dto.ScriptDTO;
import com.laterooms.camel.json.FactoryRequest;

/**
 * Created by abraithwaite on 17/04/2014.
 */
public interface ScriptFactory {
    public ScriptDTO build(FactoryRequest request);
}
