package com.resilience.entity;

import java.io.Serializable;

public class User implements Serializable {

	private static final long serialVersionUID = 2872140491703043729L;
	private long userId;
	private String userFName;
	private String userLName;
	private String accessLevel;

	public User(long userId, String userFName, String userLName, String accessLevel) {
		super();
		this.userId = userId;
		this.userFName = userFName;
		this.userLName = userLName;
		this.accessLevel = accessLevel;
	}

	public long getUserId() {
		return userId;
	}

	public String getUserFName() {
		return userFName;
	}

	public String getUserLName() {
		return userLName;
	}

	public String getAccessLevel() {
		return accessLevel;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accessLevel == null) ? 0 : accessLevel.hashCode());
		result = prime * result + ((userFName == null) ? 0 : userFName.hashCode());
		result = prime * result + (int) (userId ^ (userId >>> 32));
		result = prime * result + ((userLName == null) ? 0 : userLName.hashCode());
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
		User other = (User) obj;
		if (accessLevel == null) {
			if (other.accessLevel != null)
				return false;
		} else if (!accessLevel.equals(other.accessLevel))
			return false;
		if (userFName == null) {
			if (other.userFName != null)
				return false;
		} else if (!userFName.equals(other.userFName))
			return false;
		if (userId != other.userId)
			return false;
		if (userLName == null) {
			if (other.userLName != null)
				return false;
		} else if (!userLName.equals(other.userLName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userFName=" + userFName + ", userLName=" + userLName + ", accessLevel="
				+ accessLevel + "]";
	}

}
