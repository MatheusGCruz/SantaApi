package com.santa.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class SantaGroup {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column
	private String userId;	
	
	@Column
	private String groupName;
	
	@Column
	private String groupId;
	
	@Column 
	private Integer sorted;
	
	@Column(columnDefinition="nvarchar(max)")
	private String base64;	
	
	public SantaGroup() {
		this.sorted = 0;
	}

}
