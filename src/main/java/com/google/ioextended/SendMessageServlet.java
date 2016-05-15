package com.google.ioextended;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.google.appengine.api.channel.ChannelMessage;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;
import com.google.appengine.repackaged.com.google.gson.JsonObject;

public class SendMessageServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		ChannelService channelService = ChannelServiceFactory.getChannelService();
		String to = req.getParameter("to");
		String from = req.getParameter("from");
		String body = req.getParameter("body");
		
		String channelId = UsersRepository.getInstance().getUsersChannelId().get(to);
		JSONObject object = new JSONObject();
		object.put("from", from);
		object.put("body", body);
		object.put("type", "message");
		
		channelService.sendMessage(new ChannelMessage(channelId, object.toString()));
	}
	
}
