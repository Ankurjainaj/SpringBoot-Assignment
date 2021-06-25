package com.springboot.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
 
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import com.springboot.entity.User;
import com.springboot.entity.File;
import com.springboot.entity.Skills;
import com.springboot.entity.UserandSkills;
import com.springboot.service.FileService;
import com.springboot.service.SkillService;
import com.springboot.service.UserService;

	@Controller
	public class UserController {

	    @Autowired
	    private UserService userService;

	    @Autowired
	    private SkillService skillService;

	    @Autowired
	    private FileService fileService;

	    @GetMapping("/")
	    public String showForm(Model model)
	    {
	    	User user = new User();
//          List<String> stateList = Arrays.asList("Andhra Pradesh","Arunachal Pradesh","Assam","Bihar","Chhattisgarh","Goa","Gujarat","Haryana",
//                  "Himachal Pradesh","Jammu and Kashmir","Jharkhand","Karnataka","Kerala","Madhya Pradesh","Maharashtra","Manipur","Meghalaya","Mizoram","Nagaland","Odisha","Punjab","Rajasthan","Sikkim","Tamil Nadu","Telangana","Tripura","Uttarakhand","Uttar Pradesh","West Bengal","Andaman and Nicobar Islands","Chandigarh","Dadra and Nagar Haveli","Daman and Diu","Delhi","Lakshadweep","Puducherry");
//           
//          model.addAttribute("user", user);
//          model.addAttribute("state", stateList);
	        return "user-details";
	    }


	    @GetMapping("/getfile/{id}")
	    public ResponseEntity files(@PathVariable("id") int id) throws Exception
	    {
	        File file = fileService.getFileById(id);
	        if(file==null)
	        {
	            return ResponseEntity.badRequest().body("file not found");
	        }
	        byte[] image = Files.readAllBytes(Paths.get(file.getFileDirectory()));
	        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
	    }
	    
	    @ResponseStatus(HttpStatus.BAD_REQUEST)
	    @ExceptionHandler(BindException.class)
	    public ModelAndView handleValidationExceptions(
	            BindException ex) {
	        ModelAndView modelAndView = new ModelAndView();
	        modelAndView.setViewName("user-details");
	        String err="";
	        if(ex.hasFieldErrors("name"))
	        {
	            err+="Name Field has error\n";
	        }
	        if(ex.hasFieldErrors("email"))
	        {
	            err+="Email Field has error\n";
	        }
	        if(ex.hasFieldErrors("phone"))
	        {
	            err+="Phone Field has error\n";
	        }
	        if(ex.hasFieldErrors("state"))
	        {
	            err+="State Field has error\n";
	        }

	        modelAndView.addObject("errors",err);
	        return modelAndView;

	    }

	    @PostMapping("/addUser")
	    public ModelAndView addUser(@ModelAttribute UserandSkills userandSkills,@ModelAttribute User user, @RequestParam("image") MultipartFile multipartFile, ModelAndView modelAndView,Errors result)
	    throws IOException 
	    {
	    	try {

	    		          if (result.hasErrors()) {
	    		              System.out.println(result.getErrorCount());
	    		               throw new Exception(result.toString());
	    		            }
	    		            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
	    		            for (String skill : userandSkills.getSkills()) {
	    		                Skills currSkill = skillService.addSkill(skill, userandSkills.getEmail());
	    		                skillService.addSkill(currSkill);
	    		            }
	    		            modelAndView.addObject("userandSkills", userandSkills);
	    		            Files.copy(multipartFile.getInputStream(), Paths.get("/home/extramarks/Documents/workspace-spring-tool-suite/spring-boot-assignment/src/main/resources/static/images" + fileName), StandardCopyOption.REPLACE_EXISTING);
	    		            File file = fileService.addFile(fileName, userandSkills.getEmail());
	    		            fileService.addFile(file);
	    		            userandSkills.setPhotos("http://localhost:8080/getfile/" + String.valueOf(file.getId()));
	    		            userService.addUser(userandSkills.getUser());
	    		            modelAndView.addObject("image", "http://localhost:8080/getfile/" + String.valueOf(file.getId()));
	    		            modelAndView.setViewName("submit-details");
	    		            return modelAndView;
	    		        }
	    		        catch(Throwable e)
	    		        {
	    		            modelAndView.setViewName("user-details");
	    		            modelAndView.addObject("errors", e.toString());
	    		            return modelAndView;
	    		        }
	    }

	    @PostMapping("/updateUser")
	    public  ModelAndView updateUser(@RequestParam("email") String email,ModelAndView modelAndView)
	    {
	        System.out.println(email);
	        modelAndView.setViewName("update-user-details");
	        modelAndView.addObject("user",userService.getUserByEmail(email));
	        modelAndView.addObject("userSkills",skillService.getAllByUserId(email));
	        modelAndView.addObject("image",userService.getUserByEmail(email).getPhotos());
	        System.out.println(skillService.getAllNameByUserId(email));
	        modelAndView.addObject("allSkills",skillService.getAllNameByUserId(email));
	        return modelAndView;
	    }

	    @PostMapping("/updateUserDetails")
	    public ModelAndView updateUserDetails( @ModelAttribute @Valid UserandSkills userandSkills
	            , @RequestParam("oldEmail") String oldEmail
	            , @RequestParam("updateimage") MultipartFile multipartFile, ModelAndView modelAndView
	            , Errors result)
	    {
	            
	    try {

	    	//System.out.println(result.toString());
	    	            if (result.hasErrors()) {
	    	                System.out.println(result.getErrorCount());
	    	                throw new Exception(result.toString());
	    	            }
	    	        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
	    	        System.out.println(userandSkills+" "+oldEmail+fileName);
	    	        for(String skill:userandSkills.getSkills())
	    	        {
	    	            Skills currSkill=skillService.addSkill(skill,userandSkills.getEmail());
	    	            skillService.updateSkill(oldEmail,currSkill);
	    	        }
	    	        modelAndView.setViewName("submit-details");
	    	        modelAndView.addObject("userandSkills",userandSkills);
	    	        Files.copy(multipartFile.getInputStream(), Paths.get("/home/extramarks/Documents/workspace-spring-tool-suite/spring-boot-assignment/src/main/resources/static/images"+fileName), StandardCopyOption.REPLACE_EXISTING);
	    	        File file=fileService.addFile(fileName,userandSkills.getEmail());
	    	        fileService.updateFile(oldEmail,file);
	    	        userandSkills.setPhotos("http://localhost:8080/getfile/"+String.valueOf(file.getId()));
	    	        userService.updateUser(oldEmail,userandSkills.getUser());
	    	        modelAndView.addObject("image","http://localhost:8080/getfile/"+String.valueOf(file.getId()));
	    	        return modelAndView;
	    	        }
	    	        catch(Throwable e)
	    	        {
	    	            modelAndView.setViewName("user-details");
	    	            modelAndView.addObject("errors", e.toString());
	    	            return modelAndView;
	    	        }
	    	    }


}
