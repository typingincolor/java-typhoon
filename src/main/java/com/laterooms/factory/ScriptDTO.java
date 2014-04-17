package com.laterooms.factory;

import java.util.Map;

/**
 * Created by abraithwaite on 17/04/2014.
 */
public class ScriptDTO implements Script {
    private Map<String, Object> script;

    public ScriptDTO(Map script) {
        this.script = script;
    }

    public Map<String, Object> getScript() {
        return script;
    }
}
