package model;

import java.util.List;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
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
	
	public static List<TicketJdo> getCustomerTickets(String customerId) throws Exception {
		List <TicketJdo> ticketList = null;
		
		Datastore ds = Util.getDataStore();
		Query<TicketJdo> q = ds.createQuery(TicketJdo.class).filter("customerId ==", customerId);;

		
		ticketList = q.asList();
		
		return ticketList;
	}
}
