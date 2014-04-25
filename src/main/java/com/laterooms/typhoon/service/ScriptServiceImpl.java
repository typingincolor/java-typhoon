package com.laterooms.typhoon.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.laterooms.typhoon.dto.ScriptDTO;
import com.laterooms.typhoon.entity.Script;
import com.laterooms.typhoon.repository.ScriptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

;

/**
 * Created by abraithwaite on 17/04/2014.
 */
@Service
public class ScriptServiceImpl implements ScriptService {
    @Autowired
    ScriptRepository scriptRepository;

    @Override
    public ScriptDTO create(ScriptDTO script) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        Script newScript = new Script();
        newScript.setScript(script.getScript().toString());
        Script saved = scriptRepository.save(newScript);

        return new ScriptDTO(saved.getId(), saved.getScript());
    }

    @Override
    public ScriptDTO get(Integer id) {
        Script script = scriptRepository.get(id);
        return new ScriptDTO(script.getId(), script.getScript());
    }
}
