package com.laterooms.camel.converters;

import com.laterooms.typhoon.entity.Task;
import com.laterooms.camel.json.AtRequest;
import org.apache.camel.Converter;

@Converter
public class AtRequestToTaskConverter {
    @Converter
    public static Task toTask(AtRequest atRequest) {
        Task task = new Task();

        task.setAt(atRequest.getAt());
        task.setUrl(atRequest.getUrl());

        return task;
    }
}
