package com.adobe.prj.entity;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Employee {
	private int id;
	private String name;
	private String email;
	private List<String> languages;
	private Map<String, Integer> skills; 
}
