package jmpc.ChatApp.Controllers;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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
	
	@GetMapping(path="/posts/{id}")
	private Post getPost(@PathVariable int id) {
		
		try {
            return postRepository.findById(id).get();
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException( HttpStatus.NOT_FOUND, "entity not found");
        }
	}
	
	@PostMapping(path="/posts")
	private Post submitNewPost(@RequestBody Post newPost) {
        return this.postRepository.save(newPost);
	}
	
	@GetMapping(path="/friendrequests")
	private Collection<FriendRequest> getFriendRequests() {
		return this.friendRequestRepository.findAll();
	}
	
	@GetMapping(path="/friendrequests/{id}")
	private FriendRequest getFriendRequest(@PathVariable int id) {

		try {
            return this.friendRequestRepository.findById(id).get();
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException( HttpStatus.NOT_FOUND, "entity not found");
        }
	}
	
	@GetMapping(path="/friendrequests/user/{id}")
	private Set<FriendRequest> getUserFriendRequests(@PathVariable int id) {

		try {
            return this.friendRequestRepository.friendRequestByRequesterIdOrRequestedToId(id);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException( HttpStatus.NOT_FOUND, "Entity not found");
        }
	}
	
	@PostMapping(path="/friendrequests")
	private ResponseEntity<String> submitNewFriendRequests(@RequestBody FriendRequest friendRequest) {
		
		int requestedToId = friendRequest.getRequestedToId();
		int requesterId = friendRequest.getRequesterId();
		
		User userRequestedTo, userRequester;
		
		try {
			
			if(this.friendRequestRepository.existsByRequestedToIdAndRequesterId(requestedToId, requesterId)) {
				return new ResponseEntity<String>("Friend request already exists and is pending or has been rejected.", HttpStatus.CONFLICT);
			}
			
			userRequestedTo = this.userRepository.findById(requestedToId).get();
			userRequester = this.userRepository.findById(requesterId).get();
			
		} catch (Exception e) {
			return new ResponseEntity<String>("There was an error submitting the friend request! One or more users might not exist.", HttpStatus.BAD_REQUEST);
		}
		
		friendRequest.setRequested_to(userRequestedTo);
		friendRequest.setRequester(userRequester);
		
		this.friendRequestRepository.save(friendRequest);
		
		return new ResponseEntity<String>("Friend request sent!", HttpStatus.OK);		
		
	}
	
	@PostMapping(path="/friendrequests/accept")
	private ResponseEntity<String> acceptFriendRequest(@RequestBody FriendRequest friendRequest) {
		
		if( !friendRequest.isAccepted() && !this.friendRequestRepository.isFriendRequestAccepted(friendRequest.getId())) {
			friendRequest.setAccepted(true);
			friendRequest.setAcceptDate(java.time.LocalDateTime.now());
			this.friendRequestRepository.save(friendRequest);
			
			return new ResponseEntity<String>("Friend request accepted!", HttpStatus.OK);
		} 
		else {	
			return new ResponseEntity<String>("Friend request already accepted!", HttpStatus.CONFLICT);
		}
				
	}
	
	@PostMapping(path="/friendrequests/decline")
	private ResponseEntity<String> declineFriendRequest(@RequestBody FriendRequest friendRequest) {
		//TODO: front end should show confirmation text box
		this.friendRequestRepository.delete(friendRequest);	
		return new ResponseEntity<String>("Friend request declined.", HttpStatus.OK);
	}
	
}
