package jmpc.ChatApp.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="friend_request")
@IdClass(FriendRequestId.class)
public class FriendRequest {

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Column(name="date")
	private LocalDateTime acceptDate;
	
	@Column(name="is_accepted")
	private boolean isAccepted;
	
	@Id
	@ManyToOne
	@JoinColumn(name="requester_id")
	private User requester;
	
	@Id
	@ManyToOne
	@JoinColumn(name="requested_to_id")
	private User requestedTo;
	
	public FriendRequest() {
		
	}

	public LocalDateTime getAcceptDate() {
		return acceptDate;
	}

	public void setAcceptDate(LocalDateTime acceptDate) {
		this.acceptDate = acceptDate;
	}

	public boolean isAccepted() {
		return isAccepted;
	}

	public void setAccepted(boolean accepted) {
		this.isAccepted = accepted;
	}

	public User getRequester() {
		return requester;
	}

	public void setRequester(User requester) {
		this.requester = requester;
	}

	public User getRequestedTo() {
		return requestedTo;
	}

	public void setRequestedTo(User requestedTo) {
		this.requestedTo = requestedTo;
	}
	
}
