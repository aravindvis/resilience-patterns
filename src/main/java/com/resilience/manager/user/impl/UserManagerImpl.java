package com.resilience.manager.user.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.resilience.entity.User;
import com.resilience.exception.UserException;
import com.resilience.manager.user.UserManager;

public class UserManagerImpl implements UserManager {

	private static final Logger log = LoggerFactory.getLogger(UserManagerImpl.class);

	private boolean shouldSleep() {
		return new Random().nextInt((3 - 1) + 1) + 1 == 3;
	}

	/**
	 * Circuit Breaker Pattern Implemented with the following properties - Latency
	 * is 2000 ms (2 seconds) 
	 * - Circuit can be close check in 20000 ms (20 seconds)
	 * - Error threshold for circuit to open - 50% - Fallback method -
	 * getAllUsersFallback. Please note that the method should have the exact
	 * signature 
	 * - Threadpool key for bulkhead = userThreadPool
	 */
	@Override
	@HystrixCommand(commandProperties = {
											@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
											@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value="3"),
											@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "20000"),
											@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")}, 
					fallbackMethod = "getAllUsersFallback", 
					threadPoolKey = "userThreadPool", 
					threadPoolProperties = {
												@HystrixProperty(name = "coreSize", value = "30"),
												@HystrixProperty(name = "maximumSize",value="50"),
												@HystrixProperty(name = "allowMaximumSizeToDivergeFromCoreSize", value="true"),
												@HystrixProperty(name="maxQueueSize", value="20")
										   }, 
					ignoreExceptions = { UserException.class })
	public List<User> getAllUsers() throws UserException {
		log.debug("Entering method getAllUsers");

		User user = null;
		List<User> userList = new ArrayList<>();

		try {
			user = new User(1, "First", "User", "First Engineer");
			userList.add(user);
			user = new User(2, "Second", "User", "Second Engineer");
			userList.add(user);
			user = new User(3, "Third", "User", "Third Engineer");
			userList.add(user);
			user = new User(4, "Fourth", "User", "Fourth Engineer");
			userList.add(user);
			if (shouldSleep()) {
				log.debug("Going to sleep for 3 seconds");
				Thread.sleep(3000);
			}
		} catch (Exception ex) {
			log.debug("Error occured while fetching user exception----->{}", ex);
			throw new UserException("E9999", "Some error has occured");
		}
		log.debug("Exit method getAllUsers");
		return userList;
	}

	@Override
	public User getUserById(long id) throws UserException {
		log.debug("Entering method getUserById");

		User user = null;
		if (id >= 0) {
			user = new User(id, String.valueOf(id), "User", id + " Engineer");
		}
		log.debug("Exit method getUserById");
		return user;
	}

	public List<User> getAllUsersFallback() throws UserException {
		log.debug("Entering the getAllUsersFallback method");
		User user = null;
		List<User> userList = new ArrayList<>();

		try {
			user = new User(1, "First", "User", "First Engineer, Fallback");
			userList.add(user);
			user = new User(2, "Second", "User", "Second Engineer, Fallback");
			userList.add(user);
			user = new User(3, "Third", "User", "Third Engineer,Fallback");
			userList.add(user);
			user = new User(4, "Fourth", "User", "Fourth Engineer,Fallback");
			userList.add(user);
		} catch (Exception ex) {
			log.debug("Exception occured in fallback processing as well");
			throw new UserException("E9998", "Erorr occured");
		}
		log.debug("Exit from getAllUsersFallback method");
		return userList;
	}

}
