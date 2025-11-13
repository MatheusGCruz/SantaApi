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
    @Autowired  private QrCodeService	    qrCodeService;
	
	public List<SantaGroup> getGroups(String token){
		try {
			String userId = oauthService.searchUid(token);
			return groupRepository.findAllByUserId(userId);
		}catch(Exception ex) {
			return new ArrayList<>();
		}		
	}
	
	public SantaGroup newGroup(String token, String groupName) {
		SantaGroup santaGroup = new SantaGroup();
		santaGroup.setUserId(groupName);
		santaGroup.setGroupName(groupName);
		santaGroup.setGroupId(generateGroupId());

		try {
            santaGroup.setBase64(qrCodeService.generateBase64(santaGroup.getGroupId()));
            String userId = oauthService.searchUid(token);
            List<SantaGroup> groups = groupRepository.findAllByGroupNameAndUserId(groupName, userId);
			santaGroup.setUserId(userId);
            if(!groups.isEmpty()){
                return  groups.get(0);
            }
			return groupRepository.save(santaGroup);
		}catch(Exception ex) {
            System.out.println("error: " + ex);
            return new SantaGroup();
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
