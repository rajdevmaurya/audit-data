package com.demo.audit.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity(name = "LOCK")
public class Lock {

	@Id
	private String name;

	@Column(name = "CREATE_DT")
	private LocalDateTime createDt;

}