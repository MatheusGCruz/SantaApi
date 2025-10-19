package com.santa.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.santa.dto.SantaGroup;
import com.santa.repository.GroupRepository;

@Service
public class GroupService {

	@Autowired 	private UtilService 		utilService;
	@Autowired	private GroupRepository 	groupRepository;
	@Autowired 	private	OauthService		oauthService; 
	
	public List<SantaGroup> getGroups(String token){
		try {
			String userId = oauthService.searchUid(token);
			return groupRepository.findAllByUserId(userId);
		}catch(Exception ex) {
			return new ArrayList<>();
		}		
	}
	
	public String newGroup(String token, String groupName) {
		SantaGroup santaGroup = new SantaGroup();
		santaGroup.setUserId(groupName);
		santaGroup.setGroupName(groupName);
		santaGroup.setGroupId(generateGroupId());
		
		try {
			String userId = oauthService.searchUid(token);
			santaGroup.setUserId(oauthService.searchUid(userId)); 
			return groupRepository.save(santaGroup).getGroupId();			
		}catch(Exception ex) {
			return "error: " + ex;
		}		
	}
	
	public String generateGroupId() {
		String newUid = "";
		List<SantaGroup> existingGroup = new ArrayList<SantaGroup>();
		do {
			newUid = utilService.generatePinCode();
			existingGroup = groupRepository.findAllByGroupId(newUid);

		} while (!existingGroup.isEmpty());

		return newUid;
	}
	
	public SantaGroup getGroup(String groupId) {
		try {
			return groupRepository.findAllByGroupId(groupId).get(0);
		}
		catch(Exception ex) {
			return new SantaGroup();
		}
		
	}
	
	
	public SantaGroup saveGroup(SantaGroup group) {
		return groupRepository.save(group);
	}
}
