package model;

import java.util.List;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.query.Query;

import com.mongodb.MongoClient;

import exception.TicketGenerationException;
import utilities.Status;
import utilities.Util;

@Entity
public class TicketJdo {

	@Id
	private String ticketId;
	
	private String agentId;
	
	private String comments;
	
	private int status;
	
	private String customerId;
	
	private String assignedTo;
	
	private long generatedTime;

	public String getTicketId() {
		return ticketId;
	}

	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}

	public long getGeneratedTime() {
		return generatedTime;
	}

	public void setGeneratedTime(long generatedTime) {
		this.generatedTime = generatedTime;
	}
	
	
	public TicketJdo generateOrUpdateTicket(TicketJdo ticket) throws TicketGenerationException {
		
		if(!Util.IsValidStatus(ticket) || utilities.Status.isClosed(ticket.getStatus())) {
			int status = ticket.getStatus();
			ticket = null;
			throw new IllegalArgumentException("Illegal argument: Ticket status is invalid "+status);
		}
		
		if(ticket.getCustomerId() == null || ticket.getCustomerId().isEmpty() || ticket.getComments() == null || ticket.getComments().isEmpty()) {
			ticket = null;
			throw new IllegalArgumentException("Illegal argument: Ticket is without customer or comments");
		}
		
		if(ticket.getAssignedTo() != null && !ticket.getAssignedTo().isEmpty()) {
			ticket = null;
			throw new IllegalArgumentException("Illegal argument: Ticket's state is invalid. You are not supposed to assign this");
		}
		
		TicketJdo temp = searchTicket(ticket.getTicketId());
		if(temp != null && Status.isClosed(temp.getStatus())) {
			ticket = null;
			throw new TicketGenerationException("Ticket id already exists. Cant generate it");
		} else {
			if(!saveTicket(ticket)) {
				ticket = null;
				throw new TicketGenerationException("Database does not respond");
			}
		}
		return ticket;
	}
	
	public static TicketJdo searchTicket(String ticketId) {
		TicketJdo ticket = null;
		
		if(ticketId == null) {
			return ticket;
		}
		
		Datastore ds = Util.getDataStore();
		Query<TicketJdo> q = ds.createQuery(TicketJdo.class).filter("ticketId ==", ticketId);
		List <TicketJdo> ticketList = q.asList();
		if(ticketList != null && !ticketList.isEmpty()) {
			ticket = ticketList.get(0);
		}
		return ticket;
	}
	
	public static List<TicketJdo> viewTickets() {
		return null;
	}
	
	public boolean reopenTicket(TicketJdo ticket) throws Exception {
		ticket.setStatus(Status.OPEN.ordinal());
		return saveTicket(ticket);
	}
	
	public static boolean saveTicket(TicketJdo ticket) {
		boolean isSave = false;
		try{
			Datastore ds = Util.getDataStore();
			ds.save(ticket);
			isSave = true;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return isSave;
		
	}
	
	@Override
	public String toString() {
		return "created by "+this.getAgentId();
	}
	
}
