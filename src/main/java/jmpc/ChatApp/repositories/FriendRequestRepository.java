package jmpc.ChatApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import jmpc.ChatApp.entities.FriendRequest;
import jmpc.ChatApp.entities.FriendRequestId;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, FriendRequestId> {

}
