package com.springsecurity.model;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Customer {
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
    
	
	private String email;
	
	@JsonProperty(access=JsonProperty.Access.WRITE_ONLY)
	private String password;
	
	private String role;
	
	@Column(name="create_dt")
	private String createDt;
	
	@JsonIgnore
	@OneToMany(mappedBy="customer", fetch=FetchType.EAGER)
	private Set<Authority> authorities;
	
	
	
}
