package utilities;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.fasterxml.jackson.databind.JsonNode;
import com.mongodb.MongoClient;

import model.TicketJdo;
import play.libs.Json;

public class Util {

	private Util() {
		
	}
	
	public static <T> T convertFromJson(JsonNode json, Class<T> c) throws Exception {
		
		T t = Json.fromJson(json, c);
		return t;
	}
	
	public static boolean IsValidStatus(TicketJdo ticket) {
		return Status.check(ticket.getStatus());
	}
	
	
	public static MongoClient getMongo() {
		return MongoInstanceHolder.client;
	}
	private static class MongoInstanceHolder {
		private static final MongoClient client = new MongoClient();
	}
	
	private static class MorphiaHolder {
		private static final Morphia morphia = new Morphia();
	}
	
	public static Datastore getDataStore() {
		Morphia morphia = MorphiaHolder.morphia;
		morphia.mapPackage("model");
		Datastore ds = morphia.createDatastore(getMongo(), "ticketing");
		return ds;
	}
}
