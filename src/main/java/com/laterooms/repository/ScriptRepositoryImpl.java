package com.laterooms.repository;

import com.laterooms.entity.Script;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaMetamodelEntityInformation;
import org.springframework.data.jpa.repository.support.QueryDslJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by abraithwaite on 17/04/2014.
 */
@Repository
public class ScriptRepositoryImpl implements ScriptRepository {
    @PersistenceContext
    private EntityManager entityManager;

    private QueryDslJpaRepository<Script, Integer> scriptRepository;

    @Override
    @Transactional
    public Script save(Script script) {
        return scriptRepository.save(script);
    }

    @Override
    public Script get(Integer id) {
        return scriptRepository.findOne(id);
    }

    @PostConstruct
    public void init() {
        JpaEntityInformation<Script, Integer> scriptEntityInfo = new JpaMetamodelEntityInformation<Script, Integer>(Script.class, entityManager.getMetamodel());
        scriptRepository = new QueryDslJpaRepository<Script, Integer>(scriptEntityInfo, entityManager);
    }
}
