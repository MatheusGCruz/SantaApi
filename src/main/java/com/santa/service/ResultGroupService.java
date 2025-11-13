package com.santa.service;

import com.santa.dto.Player;
import com.santa.dto.SantaGroup;
import com.santa.entity.ResultGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ResultGroupService {

    @Autowired OauthService oauthService;
    @Autowired PlayerService playerService;
    @Autowired GroupService groupService;

    public List<ResultGroup> getResultGroups(String token) {
        List<ResultGroup> resultGroups = new ArrayList<ResultGroup>();
        try {
            String userId = oauthService.searchUid(token);

            List<Player> selectedPlays = playerService.getPlays(token);
            for(Player player : selectedPlays){
                SantaGroup santaGroup = groupService.getGroup(player.getGroupId());
                String santaPlayer = playerService.getSorted(token, santaGroup.getGroupId());

                resultGroups.add(new ResultGroup(player, santaGroup, santaPlayer));

            }
            return resultGroups;
        }
        catch(Exception ex) {
            System.out.println(ex.getMessage());
            return resultGroups;
        }
    }
}
