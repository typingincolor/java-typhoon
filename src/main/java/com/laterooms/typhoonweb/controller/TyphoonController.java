package com.laterooms.typhoonweb.controller;

import com.laterooms.typhoon.entity.Task;
import com.laterooms.typhoon.repository.TaskRepository;

import com.laterooms.typhoonweb.DTO.RouteStateDTO;
import com.laterooms.typhoonweb.service.JMXService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by abraithwaite on 25/04/2014.
 */
@Controller
public class TyphoonController {
    Logger logger = LoggerFactory.getLogger("com.laterooms");

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    JMXService jmxService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
    	List<Task> unprocessedTasks = taskRepository.findAllUnprocessed();
        List<RouteStateDTO> states = jmxService.getRouteStates();

    	model.addAttribute("routeStates", states);
    	model.addAttribute("allUnprocessedTasks", unprocessedTasks);
    	model.addAttribute("numberOfTasks", unprocessedTasks.size());

        return "index";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable int id) {
        Task task = taskRepository.get(id);
        taskRepository.delete(task);

        return "redirect:/";
    }
}
