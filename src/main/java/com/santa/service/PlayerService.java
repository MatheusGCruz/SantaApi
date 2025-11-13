package com.santa.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.santa.dto.Player;
import com.santa.dto.SantaGroup;
import com.santa.repository.PlayerRepository;

@Service
public class PlayerService {

	@Autowired 	PlayerRepository 	playerRepository;
	@Autowired 	OauthService		oauthService;
	@Autowired 	GroupService		groupService;
	@Autowired	CryptService 		cryptService;
	
	public String newPlayer(String token, String nickName, String groupId) {
		
		try {
			String userId = oauthService.searchUid(token);
			Player newPlayer = new Player(userId, nickName, groupId);
            List<Player> players = playerRepository.findAllByUserIdAndGroupId(userId, groupId);
            if(!players.isEmpty()){
                return  players.get(0).getNickName();
            }
			Player savedPlayer = playerRepository.save(newPlayer);
			return savedPlayer.getNickName();
		}
		catch(Exception ex) {
			return "Error "+ex.getMessage();
		}
		
	}
	
	public List<Player> getPlays(String token){
		try {
			String userId = oauthService.searchUid(token);
			return playerRepository.findAllByUserId(userId);
		}
		catch(Exception ex) {
			return new ArrayList();
		}
	}
	
	public SantaGroup sortGroup(String token, String groupId) {
		SantaGroup santaGroup = groupService.getGroup(groupId);
		String userId = oauthService.searchUid(token);
		if(santaGroup != null && santaGroup.getSorted() == 0 && userId.equals(santaGroup.getUserId())) {
			List<Player> playerList = playerRepository.findAllByGroupId(santaGroup.getGroupId());
			
			Collections.shuffle(playerList);
			for(Integer i =0; i<playerList.size(); i++) {
				String position = cryptService.encryptToHex(i.toString()+playerList.get(i).getNickName());
				playerList.get(i).setSantaOrder(position);	
				playerRepository.save(playerList.get(i));
			}
			
			santaGroup.setSorted(1);
			
			return groupService.saveGroup(santaGroup);
		}
		return new SantaGroup();		
	}
	
	public String getSorted(String token, String groupId) {
		String returnString = "";
		try {
			String userId = oauthService.searchUid(token);
			List<Player> playList = playerRepository.findAllByUserIdAndGroupId(userId, groupId);
			List<Player> groupList = playerRepository.findAllByGroupId(groupId);
			
			Player actualPlayer = playList.get(0);
			String playerPosition = cryptService.decryptFromHex(actualPlayer.getSantaOrder());
            if(playerPosition == null){return returnString;}
			Integer position = Integer.parseInt(playerPosition.split(actualPlayer.getNickName())[0]);
			Integer sorted = position+1==groupList.size()?0:position+1;

			
			for(Player player : groupList){
				String sortedValue = cryptService.encryptToHex(sorted.toString()+player.getNickName());
				if(sortedValue.equals(player.getSantaOrder())) {
					returnString = player.getNickName();
				}
			}
			
			return returnString;
		
		}
		catch(Exception ex) {
			return "Error: "+ex.getMessage();
		}
	}

    public String newSanta(String token, String groupName, String nickName) {
        try {

            SantaGroup santaGroup = groupService.newGroup(token, groupName);
            return newPlayer(token, nickName, santaGroup.getGroupId());
        }catch(Exception ex) {
            return "error: " + ex;
        }

    }

}
