package com.santa.entity;

import com.santa.dto.Player;
import com.santa.dto.SantaGroup;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class ResultGroup {

    private String groupName;
    private String groupId;
    private Boolean isSorted;
    private boolean isAdmin;
    private String santaName;
    private String nickName;
    private String base64;


    public ResultGroup() {};

    public ResultGroup(Player player, SantaGroup santaGroup, String  santaNick) {
        this.groupName = santaGroup.getGroupName();
        this.groupId = santaGroup.getGroupId();
        this.isSorted = !santaNick.isEmpty();
        this.isAdmin = player.getUserId().equals(santaGroup.getUserId());
        this.nickName = player.getNickName();
        this.santaName = santaNick;
        this.base64 = santaGroup.getBase64();
    };
}
