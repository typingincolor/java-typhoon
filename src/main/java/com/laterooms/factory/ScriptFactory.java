package com.laterooms.factory;

import com.laterooms.json.FactoryRequest;

/**
 * Created by abraithwaite on 17/04/2014.
 */
public interface ScriptFactory {
    public ScriptDTO build(FactoryRequest request);
}
