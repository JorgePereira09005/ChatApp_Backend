package jmpc.ChatApp.entities;

import java.io.Serializable;

public class FriendRequestId implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private User requester;
	
	private User requestedTo;
	
	public FriendRequestId() {
		
	}

	public FriendRequestId(User requester, User requestedTo) {
		this.requester = requester;
		this.requestedTo = requestedTo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((requestedTo == null) ? 0 : requestedTo.hashCode());
		result = prime * result + ((requester == null) ? 0 : requester.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FriendRequestId other = (FriendRequestId) obj;
		if (requestedTo == null) {
			if (other.requestedTo != null)
				return false;
		} else if (!requestedTo.equals(other.requestedTo))
			return false;
		if (requester == null) {
			if (other.requester != null)
				return false;
		} else if (!requester.equals(other.requester))
			return false;
		return true;
	}
	

}
