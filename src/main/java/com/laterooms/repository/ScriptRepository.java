package com.laterooms.repository;

import com.laterooms.entity.Script;

/**
 * Created by abraithwaite on 17/04/2014.
 */
public interface ScriptRepository {
    public Script save(Script script);
    public Script get(Integer id);
}
