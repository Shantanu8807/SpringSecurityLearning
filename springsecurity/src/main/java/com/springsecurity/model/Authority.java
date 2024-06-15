package com.springsecurity.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="authorities")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Authority {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
    	
	private String name;
	
	
	@ManyToOne
	@JoinColumn(name="customer_id")
	private Customer customer;

}
