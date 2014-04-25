package com.laterooms.typhoonweb.controller;

import com.laterooms.typhoon.entity.Task;
import com.laterooms.typhoon.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
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

    @ModelAttribute("allUnprocessedTasks")
    public List<Task> populateUnprocessedTasks() {
        return this.taskRepository.findAllUnprocessed();
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable int id) {
        Task task = taskRepository.get(id);
        taskRepository.delete(task);

        return "redirect:/";
    }
}
