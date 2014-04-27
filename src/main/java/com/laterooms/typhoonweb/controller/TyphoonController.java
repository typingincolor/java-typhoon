package com.laterooms.typhoonweb.controller;

import com.laterooms.typhoon.entity.Task;
import com.laterooms.typhoon.repository.TaskRepository;
import com.laterooms.typhoonweb.DTO.RouteStateDTO;
import com.laterooms.typhoonweb.service.JMXService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by abraithwaite on 25/04/2014.
 */
@Controller
public class TyphoonController {
    @Autowired
    TaskRepository taskRepository;

    @Autowired
    JMXService jmxService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
        List<Task> unprocessedTasks = taskRepository.findAllUnprocessed();

        model.addAttribute("allUnprocessedTasks", unprocessedTasks);
        model.addAttribute("numberOfTasks", unprocessedTasks.size());
        model.addAttribute("nav-root", "active");

        return "index";
    }

    @RequestMapping(value = "/routes", method = RequestMethod.GET)
    public String routes(Model model) {
        JSONObject states = jmxService.getRouteStates();
        model.addAttribute("routes", states);
        model.addAttribute("nav-routes", "active");

        return "routes";
    }

    @RequestMapping(value = "/context", method = RequestMethod.GET)
    public String context(Model model) {
        JSONObject contextStats = jmxService.getContextStats();
        model.addAttribute("context", contextStats);
        model.addAttribute("nav-context", "active");

        return "context";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable int id) {
        Task task = taskRepository.get(id);
        taskRepository.delete(task);

        return "redirect:/";
    }
}
