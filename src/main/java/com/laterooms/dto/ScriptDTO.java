package com.laterooms.dto;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * Created by abraithwaite on 17/04/2014.
 */
public class ScriptDTO {
    private int _id;
    private JsonElement script;

    public ScriptDTO(String script) {
        this.script = new JsonParser().parse(script);
    }

    public ScriptDTO(int id, String script) {
        this._id = id;
        this.script = new JsonParser().parse(script);
    }

    public int getId() {
        return _id;
    }

    public JsonElement getScript() {
        return script;
    }
}
