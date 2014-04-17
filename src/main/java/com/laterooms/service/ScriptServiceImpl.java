package com.laterooms.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.laterooms.entity.Script;
import com.laterooms.dto.ScriptDTO;
import com.laterooms.repository.ScriptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

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
        newScript.setScript(gson.toJson(script.getScript()));


        Script saved = scriptRepository.save(newScript);

        return new ScriptDTO(saved.getId(), gson.fromJson(saved.getScript(), Map.class));
    }

    @Override
    public ScriptDTO get(Integer id) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Script script = scriptRepository.get(id);
        return new ScriptDTO(script.getId(), gson.fromJson(script.getScript(), Map.class));
    }
}
