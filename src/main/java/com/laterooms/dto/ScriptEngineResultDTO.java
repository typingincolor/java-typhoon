package com.laterooms.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by abraithwaite on 17/04/2014.
 */
public class ScriptEngineResultDTO {
    private List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();

    public void add(Map<String, Object> result) {
        results.add(result);
    }

    public List<Map<String, Object>> getResults() {
        return results;
    }
}
