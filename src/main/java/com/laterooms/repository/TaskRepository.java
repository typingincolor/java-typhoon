package com.laterooms.repository;

import com.laterooms.entity.Task;

import java.util.List;

/**
 * Created by andrew on 16/04/2014.
 */
public interface TaskRepository {
    Task get(Integer id);
    List<Task> findAllUnprocessed();
}
