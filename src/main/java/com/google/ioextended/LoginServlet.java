package com.google.ioextended;

import java.io.IOException;
import java.nio.charset.Charset;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;

public class LoginServlet extends HttpServlet {
	
	private static final String CHANNEL_ID = "BHIOEXTENDED";
	
	/**
	 * Ready
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		JSONObject object = new JSONObject();
		object.put("messages", new JSONArray(Message.list()));
		resp.setHeader("Content-Type", "application/json;charset=UTF-8");
		resp.setContentType("application/json; charset=UTF-8");
		resp.getWriter().write(object.toString());
		resp.getWriter().flush();
	}

	/**
	 * Login submission
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		ChannelService channelService = ChannelServiceFactory.getChannelService();

		String channelId = channelService.createChannel(CHANNEL_ID);
		
		JSONObject object = new JSONObject();
		object.put("channelId", channelId);
		
		object.write(resp.getWriter());
	}
	
}
