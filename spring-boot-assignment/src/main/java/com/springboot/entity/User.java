package com.springboot.entity;
import lombok.*;

import javax.persistence.*;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name="users")
@Entity
public class User {
    public User(String name2, String email2, String phone2, String state2, String gender2, String photos2) {
		this.name=name2;
		this.email=email2;
		this.gender=gender2;
		this.phone=phone2;
		this.photos=photos2;
		this.state=state2;
	}
	public User() {
		
	}
	
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String name;
    @Id
    private String email;
    private String phone;
    private String state;
    private String gender;
    private String photos;
	
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

}
