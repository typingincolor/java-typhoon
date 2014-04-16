package com.laterooms.repository;

import com.laterooms.entity.Task;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaMetamodelEntityInformation;
import org.springframework.data.jpa.repository.support.QueryDslJpaRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static com.laterooms.repository.TaskSpecifications.isUnprocessed;

/**
 * Created by andrew on 16/04/2014.
 */
@Repository
public class TaskRepositoryImpl implements TaskRepository {
    @PersistenceContext
    private EntityManager entityManager;

    private QueryDslJpaRepository<Task, Integer> taskRepository;

    @Override
    public Task get(Integer id) {
        return taskRepository.findOne(id);
    }

    @Override
    public List<Task> findAllUnprocessed() {
        return taskRepository.findAll(isUnprocessed());
    }

    @PostConstruct
    public void init() {
        JpaEntityInformation<Task, Integer> taskEntityInfo = new JpaMetamodelEntityInformation<Task, Integer>(Task.class, entityManager.getMetamodel());
        taskRepository = new QueryDslJpaRepository<Task, Integer>(taskEntityInfo, entityManager);
    }
}
