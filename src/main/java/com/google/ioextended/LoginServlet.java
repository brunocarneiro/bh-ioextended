package com.google.ioextended;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.appengine.api.channel.ChannelMessage;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;

public class LoginServlet extends HttpServlet {
	
	/**
	 * Ready
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		ChannelService channelService = ChannelServiceFactory.getChannelService();
		
		//send loggedUsers
		JSONArray usersArray = new JSONArray(UsersRepository.getInstance().getLoggedUser());
		JSONObject object = new JSONObject();
		object.put("type", "usersList");
		object.put("loggedUsers", usersArray);
		
		System.out.println( object.toString());
		channelService.sendMessage( new ChannelMessage(req.getParameter("channelId"), object.toString()));
		
	}

	/**
	 * Login submission
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		ChannelService channelService = ChannelServiceFactory.getChannelService();
		String login = req.getParameter("login");

		String channelId = channelService.createChannel(login);
		
		UsersRepository.getInstance().getLoggedUser().add(login);
		UsersRepository.getInstance().getUsersChannelId().put(login, channelId);
		
		JSONObject object = new JSONObject();
		object.put("channelId", channelId);
		
		object.write(resp.getWriter());
	}
	
}
