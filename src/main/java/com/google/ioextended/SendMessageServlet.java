package com.google.ioextended;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.google.appengine.api.channel.ChannelMessage;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;

public class SendMessageServlet extends HttpServlet {
	
	private static final String CHANNEL_ID = "BHIOEXTENDED";

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		ChannelService channelService = ChannelServiceFactory.getChannelService();
		String from = req.getParameter("from");
		String body = req.getParameter("body");
		String channel = req.getParameter("channel");
		String channelId = req.getParameter("channelId");
		
		Message msg = new Message(null, body, from, channel, new Date().getTime());
		msg.save();
		
		JSONObject object = new JSONObject(msg);
		
		channelService.sendMessage(new ChannelMessage(channelId, object.toString()));
	}
	
}
