package deloitte.digital.away.day;

import org.junit.Rule;
import org.junit.rules.ExpectedException;

import deloitte.digital.away.day.service.EventService;
import deloitte.digital.away.day.service.impl.FileEventServiceImpl;
import deloitte.digital.away.exception.EventException;
import org.junit.Test;

/**
 * Unit test for deloitte digital away day App.
 */
public class AppTest
{
	
	@Test(expected = EventException.class)
    public void testApp_appfailed() 
    {
    	try {
    		throw new EventException("Application failed to initialize");
    	}catch(EventException e){
    		
    	}
    }
	
	@Test(expected = EventException.class)
    public void testApp_resouceUnAvailable() throws EventException
    {
    	EventService serviceHandler = new FileEventServiceImpl();
    	serviceHandler.readInputfromResouce("test_incorrectname.txt");
    }
	
	@Test(expected = EventException.class)
    public void testApp_resouceInvalidDuraion() throws EventException
    {
    	EventService serviceHandler = new FileEventServiceImpl();
    	serviceHandler.readInputfromResouce("test_incorrect2.txt");
    }
  
    @Test(expected = EventException.class)
    public void testApp_resouceForamtIncorrect() throws EventException
    {
    	EventService serviceHandler = new FileEventServiceImpl();
    	serviceHandler.readInputfromResouce("test_incorrect1.txt");
    }
    
    @Test
    public void testApp_resouceloaded() throws EventException
    {
    	EventService serviceHandler = new FileEventServiceImpl();
    	serviceHandler.readInputfromResouce("test_correct.txt");
    }
}
