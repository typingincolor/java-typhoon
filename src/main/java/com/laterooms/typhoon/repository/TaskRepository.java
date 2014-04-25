package com.laterooms.typhoon.repository;

import com.laterooms.typhoon.entity.Task;

import java.util.List;

/**
 * Created by andrew on 16/04/2014.
 */
public interface TaskRepository {
    Task get(Integer id);
    Task save(Task task);
    List<Task> findAllUnprocessed();
    List<Task> findAll();
    List<Task> findAllReadyToBeProcessed();
    void delete(Task task);
}
