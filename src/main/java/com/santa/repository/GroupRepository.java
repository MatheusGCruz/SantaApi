package com.santa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.santa.dto.SantaGroup;

@Repository
public interface GroupRepository extends JpaRepository<SantaGroup,Long>{
	List<SantaGroup> findAllByGroupId(String newUid);

	List<SantaGroup> findAllByUserId(String token);

    List<SantaGroup> findAllByGroupNameAndUserId(String groupName, String userId);
}
