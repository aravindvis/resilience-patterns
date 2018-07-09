package com.resilience.manager.user;

import java.util.List;

import com.resilience.entity.User;
import com.resilience.exception.UserException;

public interface UserManager {

	List<User> getAllUsers() throws UserException;

	User getUserById(long id) throws UserException;

}
