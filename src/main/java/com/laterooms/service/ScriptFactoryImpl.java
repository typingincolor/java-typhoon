package com.laterooms.service;

import com.laterooms.dto.ScriptDTO;
import com.laterooms.json.FactoryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by abraithwaite on 17/04/2014.
 */
@Service
public class ScriptFactoryImpl implements ScriptFactory {
    @Autowired
    ScriptService scriptService;

    @Override
    public ScriptDTO build(FactoryRequest request) {
        ScriptDTO savedScript = null;
        if ("send_email".equals(request.getAction())) {
            Map<String, Object> emailScript = new HashMap<String, Object>();
            Map<String, Object> step1 = new HashMap<String, Object>();
            Map<String, Object> step1Data = new HashMap<String, Object>();
            Map<String, Object> templateData = new HashMap<String, Object>();

            step1.put("command", "apply_template");
            step1Data.put("template", "email");
            templateData.put("name", request.getData().get("name"));
            step1Data.put("template_data", templateData);
            step1.put("data", step1Data);

            emailScript.put("one", step1);

            ScriptDTO script = new ScriptDTO(emailScript);
            savedScript = scriptService.create(script);
        }

        return savedScript;
    }
}
