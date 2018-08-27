
package com.resilience.services;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.resilience.entity.User;
import com.resilience.exception.UserException;
import com.resilience.manager.user.UserManager;
import com.resilience.utils.Utilities;

@RestController
@RequestMapping("/user")
public class ExampleServices {

	private static final Logger log = LoggerFactory.getLogger(ExampleServices.class.getName());

	@Autowired
	private UserManager userImpl;

	@Autowired
	private RestTemplate restTemplate;

	@RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public ObjectNode getUsers() throws UserException {
		log.debug("Inside getUsers by /list method");
		List<User> listAllUsers = null;
		ObjectNode response = Utilities.createObjectNode();
		ArrayNode userList = null;
		try {
			listAllUsers = userImpl.getAllUsers();
			userList = Utilities.convertListToArrayNode(listAllUsers);
		} catch (UserException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new UserException("E9999", "ERROR OCCURRED PROCESSING REQUEST");
		}
		response.put("STATUS", "SUCCESS");
		response.putArray("users").addAll(userList);
		return response;
	}

	/**
	 * Calling of external service through Ribbon client. We are simulating external
	 * service by calling another service.
	 */
	@RequestMapping(value = "/listByProxy", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public ObjectNode getUsersThroughProxy() {
		log.debug("Calling user service");
		ObjectNode response = restTemplate.getForObject("http://resilience/user/list", ObjectNode.class);
		response.put("Proxy", "YES");
		return response;
	}

}
