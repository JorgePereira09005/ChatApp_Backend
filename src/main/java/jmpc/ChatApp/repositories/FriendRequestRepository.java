package jmpc.ChatApp.repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jmpc.ChatApp.entities.FriendRequest;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Integer> {

	Boolean existsByRequestedToIdAndRequesterId(int requestedToId, int requesterId);
	
	@Query("SELECT r.isAccepted FROM FriendRequest r where r.id = :id")
    Boolean isFriendRequestAccepted(@Param("id") int id);
	
	//query to find every friend request with the same User id for RequesterId or RequestedTo
	@Query(value = "SELECT * FROM friend_request WHERE requester_id = :id OR requested_to_id = :id", nativeQuery = true)
	Set<FriendRequest> friendRequestByRequesterIdOrRequestedToId(int id);
}
