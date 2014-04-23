package com.laterooms.repository;

import com.laterooms.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

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
