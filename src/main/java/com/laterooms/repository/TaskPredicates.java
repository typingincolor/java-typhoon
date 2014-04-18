package com.laterooms.repository;

import com.laterooms.entity.QTask;
import com.mysema.query.types.Predicate;

import java.util.Date;

/**
 * Created by andrew on 16/04/2014.
 */
public class TaskPredicates {
    public static Predicate readyToProcess() {
        QTask task = QTask.task;
        return task.statusCode.isNull().and(task.at.before(new Date()));
    }

    public static Predicate isUnprocessed() {
        QTask task = QTask.task;
        return task.statusCode.isNull();
    }
}
