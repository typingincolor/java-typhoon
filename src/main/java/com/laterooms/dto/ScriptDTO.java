package com.laterooms.dto;

import java.util.Map;

/**
 * Created by abraithwaite on 17/04/2014.
 */
public class ScriptDTO {
    private int _id;
    private Map<String, Object> script;

    public ScriptDTO(Map script) {
        this.script = script;
    }

    public ScriptDTO(int id, Map script) {
        this._id = id;
        this.script = script;
    }

    public int getId() {
        return _id;
    }

    public Map<String, Object> getScript() {
        return script;
    }
}
