package com.google.ioextended;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;

public class Message {
	
	static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	
	private Long id;
	
	private String body;
	
	private String from;
	
	private String channel;
	
	private Long timestamp;
	
	public Message(Long id, String body, String from, String channel, Long timestamp) {
		super();
		this.id = id;
		this.body = body;
		this.from = from;
		this.channel = channel;
		this.timestamp = timestamp;
	}

	public void save(){
		
		Entity entity = new Entity("Message");
		entity.setIndexedProperty("body", this.body);
		entity.setIndexedProperty("from", this.from);
		entity.setIndexedProperty("channel", this.channel);
		entity.setIndexedProperty("timestamp", this.timestamp);
		
		datastore.put(entity);
	}
	
	public static List<Message> list(){
		
		Query q = new Query("Message");
		q = q.addSort("timestamp", SortDirection.ASCENDING);
		List<Entity> entities = datastore.prepare(q).asList(FetchOptions.Builder.withLimit(100));
		List<Message> messages = new ArrayList<>();
		
		for (Entity entity : entities) {
			
			messages.add(new Message((Long)entity.getProperty("id"), (String)entity.getProperty("body"), (String)entity.getProperty("from"), (String)entity.getProperty("channel"), (Long)entity.getProperty("timestamp")));
		}
		return messages;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	
}
