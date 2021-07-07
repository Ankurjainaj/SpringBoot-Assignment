package com.springboot.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.dao.FileDao;
import com.springboot.entity.File;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class FileService {
    @Autowired
    private FileDao fileDao;

    public File addFile(String fileName,String userEmail)
    {
        File file=new File();
        file.setFileDirectory("/home/extramarks/Documents/workspace-spring-tool-suite/spring-boot-assignment/src/main/resources/static/images"+fileName);
        file.setCreated_at(LocalDateTime.now());
        file.setUpdated_at(null);
        file.setDeleted_at(null);
        file.setUserId(userEmail);
        return file;
    }

    public File addFile(File file)
    {
        return fileDao.save(file);
    }

    public void deleteFileById(int id)
    {
        fileDao.deleteById(id);
    }

    public File getFileById(int id)
    {
        return fileDao.findById(id).get();
    }

    public List<File> getAllFile()
    {
        return (List<File>)fileDao.findAll();
    }

    public void deleteByUserId(String id)
    {
        fileDao.deleteByUserId(id);
    }

    @Modifying
    public void updateFile(String id,File newFile)
    {
        fileDao.deleteByUserId(id);
        File f=fileDao.save(newFile);
        System.out.println(f);
    }
}