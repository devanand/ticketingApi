package controllers;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;

import exception.TicketGenerationException;
import model.CustomerJdo;
import model.TicketJdo;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http.Response;
import play.mvc.Result;
import utilities.Util;

public class TicketGeneratorController extends Controller {

	public Result generateOrUpdateTicket() {
		Result status = null;
		Response response = response();
		checkPreFlight(response);
		try {
			JsonNode json = request().body().asJson();
			
			if(json == null) {
				status = badRequest("Request body is empty");
				return status;
			}
		
			TicketJdo ticket = Util.convertFromJson(json, TicketJdo.class);
			
			ticket = ticket.generateOrUpdateTicket(ticket);
			System.out.println(ticket == null);
			status = ok(json);
			
		} catch(TicketGenerationException t) {
			t.printStackTrace();
			status = status(400, "Ticket id already exists");
		} catch(IllegalArgumentException e) {
			e.printStackTrace();
			status = badRequest("Ticket cannot be saved");
		} catch (Exception e) {
			//e.printStackTrace();
			status = status(500, "This is a bad request");
		}
		return status;
	}
	
	public Result reopenTicket() {
		Result status = null;
		Response response = response();
		checkPreFlight(response);
		try {
			JsonNode json = request().body().asJson();
			if(json == null) {
				status = badRequest("Request body is empty");
			} else {
				TicketJdo ticket = Util.convertFromJson(json, TicketJdo.class);
				if(ticket.getStatus() != utilities.Status.CLOSED.getStatus()) {
					status = ok("Change Successful"); 
				} else {
					status = ticket.reopenTicket(ticket)?ok("Change Successful"):status(500, "Internal error");
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
			status = badRequest("Ticket json format is bad");
		}
		
		return status;
	}
	
	public Result assignTicket() {
		Response response = response();
		checkPreFlight(response);
		
		Result status = null;
		try {
			JsonNode json = request().body().asJson();
			
			if(json == null) {
				status = badRequest("Request body is empty");
			} else {
				TicketJdo ticket = Util.convertFromJson(json, TicketJdo.class);
				
				if(ticket.getAssignedTo() == null || ticket.getAssignedTo().isEmpty()) {
					status = badRequest("It is not assigned to anyone so ticket cannot be closed");
					return status;
				}
				
				if(ticket.getStatus() != utilities.Status.CLOSED.getStatus()) {
					status = badRequest("Assigned ticket should be closed");
				} else {
					status = TicketJdo.saveTicket(ticket)?ok("Change Successful"):status(500, "Internal error");
				}
			}
		} catch(Exception e) {
			status = status(500, "Internal error");
		}
		return status;
	}
	
	public Result getTickets() {
		Response response = response();
		checkPreFlight(response);
		
		Result status = null;
		try {
			JsonNode json = request().body().asJson();
			
			if(json == null) {
				status = badRequest("Request body is empty");
			}
			else {
				Map<String, String> criteria = Util.convertFromJson(json, Map.class);
				
//				if(criteria == null || criteria.isEmpty()) {
//					status = badRequest("Request body is empty");
//					return status;
//				}
				
				List <TicketJdo> ticketList = CustomerJdo.getCustomerTickets(criteria);
				if(ticketList == null || ticketList.isEmpty()) {
					status(200, "No tickets for the customer");
					
				} else {
					status = ok(Json.toJson(ticketList));
				}
			}
		} catch(NullPointerException e) {
			e.printStackTrace();
			status = status(500, "There is an null exception somewhere");
		} catch(Exception e) {
			e.printStackTrace();
			status = status(500, e.getMessage());
		} 
		
		return status;
	}
	
	public static void checkPreFlight(Response response) {
	    response.setHeader("Access-Control-Allow-Origin", "*");       // Need to add the correct domain in here!!
	    response.setHeader("Access-Control-Allow-Methods", "POST");   // Only allow POST
	    response.setHeader("Access-Control-Max-Age", "300");          // Cache response for 5 minutes
	    response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");         // Ensure this header is also allowed!
	}
}
