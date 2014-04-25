package com.laterooms.typhoon.repository;

import com.laterooms.typhoon.entity.Script;

/**
 * Created by abraithwaite on 17/04/2014.
 */
public interface ScriptRepository {
    public Script save(Script script);
    public Script get(Integer id);
}
