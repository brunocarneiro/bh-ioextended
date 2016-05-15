package com.google.ioextended;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class UsersRepository {
	
	private static UsersRepository instance;
	
	private Set<String> loggedUser;
	
	private Map<String, String> usersChannelId;
	
	private UsersRepository(){
		setLoggedUser(new HashSet<String>());
		setUsersChannelId(new HashMap<String, String>());
	}
	
	public static UsersRepository getInstance(){
		if(instance == null){
			instance = new UsersRepository();
		}
		return instance;
	}


	public Map<String, String> getUsersChannelId() {
		return usersChannelId;
	}

	public void setUsersChannelId(Map<String, String> usersChannelId) {
		this.usersChannelId = usersChannelId;
	}

	public Set<String> getLoggedUser() {
		return loggedUser;
	}

	public void setLoggedUser(Set<String> loggedUser) {
		this.loggedUser = loggedUser;
	}

}
