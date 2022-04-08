package com.travels.rajbus;

import java.util.List;

import javax.management.relation.Role;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;



	
	@Entity
	@Table(name = "users")
	public class User {
	    @Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    private Long id;
	    private String email;
	    private String firstname;
	    private String lastname;
	    private String phoneNumber;

	    private List<Role> roles;
	    private boolean isActivated;
	    private String activationHash;
	    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
	    

	    public String getEmail() {
	        return email;
	    }

	    public void setEmail(String email) {
	        this.email = email;
	    }

	    public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }

	    public String getFirstname() {
	        return firstname;
	    }

	    public void setFirstname(String firstname) {
	        this.firstname = firstname;
	    }

	    public String getLastname() {
	        return lastname;
	    }

	    public void setLastname(String lastname) {
	        this.lastname = lastname;
	    }

	    public String getPhoneNumber() {
	        return phoneNumber;
	    }

	    public void setPhoneNumber(String phoneNumber) {
	        this.phoneNumber = phoneNumber;
	    }

	    public boolean isActivated() {
	        return isActivated;
	    }

	    public void setActivated(boolean activated) {
	        isActivated = activated;
	    }

	    public List<Role> getRoles() {
	        return roles;
	    }

	    public void setRoles(List<Role> roles) {
	        this.roles = roles;
	    }

	    public String getActivationHash() {
	        return activationHash;
	    }

	    public void setActivationHash(String activationHash) {
	        this.activationHash = activationHash;
	    }

	}




