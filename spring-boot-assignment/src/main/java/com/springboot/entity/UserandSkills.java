package com.springboot.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.util.List;

@Getter
@Setter
@ToString
@Data
public class UserandSkills {
	@NotEmpty
	@NotNull
	@Email
    private String email;
	@NotEmpty
	@NotNull
	private String name;
	@Size(min=10,max=10,message = "Must have 10 digits")
	@NotEmpty
	@NotNull
    private String phone;
	
	@NotEmpty
	@NotNull
    private String state;
	@NotEmpty
	@NotNull
	private String gender;
	@NotEmpty
	@NotNull
	private String photos;
	@NotEmpty
	@NotNull
	private List<String> skills;


    public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}


	public String getPhotos() {
		return photos;
	}


	public void setPhotos(String photos) {
		this.photos = photos;
	}


	public List<String> getSkills() {
		return skills;
	}


	public void setSkills(List<String> skills) {
		this.skills = skills;
	}


	public User getUser()
    {
        return new User(this.name,this.email,this.phone,this.state,this.gender,this.photos);
    }
}