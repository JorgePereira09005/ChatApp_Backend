package jmpc.ChatApp.Controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jmpc.ChatApp.entities.FriendRequest;
import jmpc.ChatApp.entities.Post;
import jmpc.ChatApp.entities.User;
import jmpc.ChatApp.repositories.FriendRequestRepository;
import jmpc.ChatApp.repositories.PostRepository;
import jmpc.ChatApp.repositories.UserRepository;

@RestController
@RequestMapping(path = "/api")
public class RestAPIController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private FriendRequestRepository friendRequestRepository;
	
	@GetMapping(path="/users")
	private Collection<User> getUsers() {
		return this.userRepository.findAll();
	}
	
	@GetMapping(path="/posts")
	private Collection<Post> getPosts() {
		return this.postRepository.findAll();
	}
	
	@GetMapping(path="/friendrequests")
	private Collection<FriendRequest> getFriendRequests() {
		return this.friendRequestRepository.findAll();
	}
	
}
