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
    @Autowired
    ScriptService scriptService;

    private final static String COMMAND = "command";
    private final static String DATA = "data";

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

        result.put(COMMAND, Commands.apply_template);
        data.put("template", "email");
        templateData.put("name", name);
        data.put("template_data", templateData);
        result.put(DATA, data);
        return result;
    }

    private Map<String, Object> buildTemplateCommand(String to, String subject) {
        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, Object> data = new HashMap<String, Object>();

        result.put(COMMAND, Commands.email);
        data.put("to", to);
        data.put("subject", subject);
        result.put(DATA, data);

        return result;
    }
}
