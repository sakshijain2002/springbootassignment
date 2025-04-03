package com.example.demo.entity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "countries")
@Data
public class Country {
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    
	    private String name;
	    private String capital;
	    private String region;
	    private Long population;
	    private String languages;
	@Column(length = 1000, nullable = false)
	    private String flag;
	}



