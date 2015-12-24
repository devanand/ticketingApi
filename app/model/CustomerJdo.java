package model;

import java.util.List;
import java.util.Map;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.query.CriteriaContainer;
import org.mongodb.morphia.query.CriteriaContainerImpl;
import org.mongodb.morphia.query.FieldEnd;
import org.mongodb.morphia.query.Query;

import utilities.Util;

@Entity
public class CustomerJdo {

	
	@Id
	private String phoneNumber;
	
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public static List<TicketJdo> getCustomerTickets(Map<String, String> conditions) throws Exception {
		List <TicketJdo> ticketList = null;
		Datastore ds = Util.getDataStore();
		Query<TicketJdo> q = ds.createQuery(TicketJdo.class);
		
		
		for(String key:conditions.keySet()) {
			if(key.equals("timeStart")) {
				q=q.field("generatedTime").greaterThanOrEq(Long.parseLong(conditions.get(key)));
			} else if(key.equals("timeEnd")) {
				q=q.field("generatedTime").lessThanOrEq(Long.parseLong(conditions.get(key)));
			} else {
				q=q.field(key).equal(conditions.get(key));	
			}
		}
			
		System.out.println(q.toString());
		ticketList = q.asList();
		
		return ticketList;
	}
	
	
}
