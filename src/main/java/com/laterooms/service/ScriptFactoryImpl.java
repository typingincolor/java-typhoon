package com.laterooms.service;

import com.laterooms.dto.ScriptDTO;
import com.laterooms.json.FactoryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by abraithwaite on 17/04/2014.
 */
@Service
public class ScriptFactoryImpl implements ScriptFactory {
    private final static String COMMAND = "command";
    private final static String DATA = "data";
    @Autowired
    ScriptService scriptService;

    @Override
    public ScriptDTO build(FactoryRequest request) {
        ScriptDTO savedScript = null;
        if ("send_email".equals(request.getAction())) {
            Map<String, Object> emailScript = new TreeMap<String, Object>();
            Map<String, Object> step1 = buildEmailCommand((String) request.getData().get("name"));
            Map<String, Object> step2 = buildTemplateCommand((String) request.getData().get("to"), (String) request.getData().get("subject"));

            emailScript.put("a", step1);
            emailScript.put("b", step2);

            ScriptDTO script = new ScriptDTO(emailScript);
            savedScript = scriptService.create(script);
        }

        return savedScript;
    }

    private Map<String, Object> buildEmailCommand(String name) {
        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, Object> data = new HashMap<String, Object>();
        Map<String, Object> templateData = new HashMap<String, Object>();

        templateData.put("name", name);

        data.put("template_data", templateData);
        data.put("template", "email");

        result.put(COMMAND, Commands.apply_template);
        result.put(DATA, data);
        return result;
    }

    private Map<String, Object> buildTemplateCommand(String to, String subject) {
        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, Object> data = new HashMap<String, Object>();

        data.put("to", to);
        data.put("subject", subject);

        result.put(COMMAND, Commands.email);
        result.put(DATA, data);

        return result;
    }
}
