package com.santa.dto;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Player {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column
	private String userId;	
	
	@Column
	private String nickName;
	
	@Column
	private String groupId;
	
	@Column
	private String santaOrder;
	
	public Player() {};
	
	public Player(String userId, String nickName, String groupId) {
		this.groupId=groupId;
		this.nickName = nickName;
		this.userId = userId;
	};

}
