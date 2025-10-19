package com.santa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.santa.dto.Player;



@Repository
public interface PlayerRepository extends JpaRepository<Player,Long>{

	List<Player> findAllByUserId(String userId);

	List<Player> findAllByGroupId(String groupId);

	List<Player> findAllByUserIdAndGroupId(String userId, String groupId);

}
