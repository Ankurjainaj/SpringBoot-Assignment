package com.springboot.dao;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.springboot.entity.File;
 
@Repository
public interface FileDao extends CrudRepository<File,Integer> {
    public void deleteByUserId(String id);
}
