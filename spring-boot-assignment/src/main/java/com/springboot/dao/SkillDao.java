package com.springboot.dao;

import com.springboot.entity.Skills;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillDao extends CrudRepository<Skills,Integer> {
    public List<Skills> getAllByUserId(String userId);

    @Query(value = "select skill.name from skill where user_id = ?",nativeQuery = true)
    public List<String> getAllNameByUserId(String userId);

    public void deleteByUserId(String id);


}