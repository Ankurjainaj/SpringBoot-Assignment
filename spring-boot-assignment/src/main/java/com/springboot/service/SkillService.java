package com.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.dao.SkillDao;
import com.springboot.entity.Skills;

import java.util.List;

@Service
@Transactional
public class SkillService {
    @Autowired
    private SkillDao skillDao;

    public Skills addSkill(String skill,String userEmail)
    {
        Skills currSkill=new Skills();
        currSkill.setName(skill);
        currSkill.setUserId(userEmail);
        return currSkill;
    }

    public Skills addSkill(Skills skill)
    {
        return skillDao.save(skill);
    }

    public List<Skills> getAllByUserId(String userId)
    {
        return skillDao.getAllByUserId(userId);
    }

    public List<String> getAllNameByUserId(String userId)
    {
        return  skillDao.getAllNameByUserId(userId);
    }

    public void deleteByUserId(String id)
    {
        skillDao.deleteByUserId(id);
    }

    @Modifying
    public void updateSkill(String id,Skills newSkill)
    {
        skillDao.deleteByUserId(id);
        Skills s=skillDao.save(newSkill);
        System.out.println(s);
    }

}
