package controllers;

import java.util.Date;
import java.util.List;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;

import model.TicketJdo;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http.RequestBody;
import play.mvc.Result;
import views.html.index;

public class Application extends Controller {

    public Result index() {
    	
        return ok(index.render("Your new application is ready."));
    }
    
    public Result testParams() {
    	RequestBody body = request().body();
    	
        return ok(index.render(body.asText()));
    }
}
