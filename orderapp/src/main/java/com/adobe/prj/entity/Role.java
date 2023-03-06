package com.adobe.prj.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name="roles")
public class Role {
	@Id
	String role;
	@Column(name="role_description")
	String description;
}

// insert into roles values ('ADMIN', 'Administrator can access all resources');
// insert into roles values('GUEST', 'Guest users can access only landing page')