package com.d.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
* @author di
*/
@Entity
@Table(name="user")
public class UserDto {
	@Id
	private Integer id;
}
