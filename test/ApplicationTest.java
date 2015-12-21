import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;

import model.TicketJdo;

import org.junit.*;

import play.mvc.*;
import play.test.*;
import play.data.DynamicForm;
import play.data.validation.ValidationError;
import play.data.validation.Constraints.RequiredValidator;
import play.i18n.Lang;
import play.libs.F;
import play.libs.F.*;
import play.libs.Json;
import play.twirl.api.Content;

import static play.test.Helpers.*;
import static org.junit.Assert.*;


/**
*
* Simple (JUnit) tests that can call all parts of a play app.
* If you are interested in mocking a whole application, see the wiki for more details.
*
*/
public class ApplicationTest {

    @Test
    public void simpleCheck() {
        int a = 1 + 1;
        assertEquals(2, a);
    }

    @Test
    public void renderTemplate() {
        Content html = views.html.index.render("Your new application is ready.");
        assertEquals("text/html", contentType(html));
        assertTrue(contentAsString(html).contains("Your new application is ready."));
    }
    
//    @Test
//    public void testGenerate() {
//    	TicketJdo t = new TicketJdo();
//    	t.setTicketId("1");
//    	t.setAgentId("1");
//    	t.setComments("This is a test");
//    	t.setStatus(1);
//    	t.setCustomerId("1");
//    	t.setAssignedTo("");
//    	t.setGeneratedTime(new Date().getTime());
//    	Result result = callAction(
//    			controllers.TicketGeneratorController.generateOrUpdateTicket()
//    			);
//    }


}
