package com.santa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.santa.dto.Player;
import com.santa.dto.SantaGroup;
import com.santa.service.CryptService;
import com.santa.service.GroupService;
import com.santa.service.PlayerService;

@RestController
@RequestMapping("")
@CrossOrigin(origins = "*") // Allow requests from React frontend
public class SantaController {
	
	@Autowired	private CryptService 	cryptService;
	@Autowired	private PlayerService 	playerService;
	@Autowired	private GroupService	groupService;
	
	@GetMapping()
	public String healthCheck() {
		return "OK";
	}
	
	
	@GetMapping("/newPlayer")
	public String newPlayer(@RequestHeader("Authorization") String token, @RequestHeader("NickName") String nickName, @RequestHeader("GroupId") String groupId) {
		return playerService.newPlayer(token, nickName, groupId);		
	}
	
	@GetMapping("/newGroup")
	public String newGroup(@RequestHeader("Authorization") String token, @RequestHeader("GroupName") String groupName) {
		return groupService.newGroup(token, groupName);		
	}
	
	@GetMapping("/groups")
	public List<SantaGroup> getGroups(@RequestHeader("Authorization") String token) {
		return groupService.getGroups(token);		
	}
	
	@GetMapping("/plays")
	public List<Player> getPlays(@RequestHeader("Authorization") String token) {
		return playerService.getPlays(token);	
	}
	
	@GetMapping("/sorts")
	public SantaGroup sorts(@RequestHeader("Authorization") String token, @RequestHeader("GroupId") String groupId) {
		return playerService.sortGroup(token, groupId);		
	}
	
	@GetMapping("/getSorted")
	public String getSorted(@RequestHeader("Authorization") String token, @RequestHeader("GroupId") String groupId) {
		return playerService.getSorted(token, groupId);		
	}
	
	
	
	
}
