package com.adobe.prj.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adobe.prj.entity.Employee;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
	
	@Autowired
	ObjectMapper mapper;
	
	Employee e = null;
	Map<String, Integer> skills = new HashMap<>();
	
	public EmployeeController() {
		skills.put("Java", 4);
		skills.put("React", 5);
		e = new Employee(1, "Roger", "roger@gmail.com", List.of("English", "Hindi", "Kannada"), skills);
	}
	
	@PatchMapping(path = "/{id}", consumes = "application/json-patch+json")
	public ResponseEntity<Employee> updateEmployee(@PathVariable("id") int id, 
			@RequestBody JsonPatch patch) throws Exception {
		Employee patched = applyPatchToEmployee(patch, e);
		// send patched to Service ==> DAO ==> update
		return ResponseEntity.ok(patched);
	}
	
	private Employee applyPatchToEmployee(JsonPatch patch, Employee target) throws Exception {
		JsonNode patched = patch.apply(mapper.convertValue(target, JsonNode.class));
		return mapper.treeToValue(patched, Employee.class);
	}
}
