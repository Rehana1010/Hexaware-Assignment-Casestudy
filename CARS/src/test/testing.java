package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import entity.Incidents;
import dao.ICrimeAnalysisServiceImpl;

public class testing {
	
	private ICrimeAnalysisServiceImpl test;
     
    
    @Before
    public void setUp() {
        test = new ICrimeAnalysisServiceImpl();
       
        
    }
    
    
    @After
    public void tearDown() {
        // Clean up resources if needed
        test = null;
       
       
    }
    
    @Test
    public void testCreateIncident() {
    	
    	Incidents testnewincident = new Incidents(
    			15,
    			"theft",
    			java.sql.Date.valueOf("2024-01-01"),
    			"75,90",
    			"gold theft",
    			"open",
    			5,
    			2
    			);
    	boolean result = test.createIncident(testnewincident);
    	
    	assertTrue("Incident creation success", result);
    	
    }
    
    @Test
    public void updateIncidentStatus() {
    	int incidentId = 1;
        String IncStatus = "CLOSED";
    	
    	boolean result = test.updateIncidentStatus(incidentId, IncStatus);
    	
    	assertTrue("Incident status updated successfully", result);
              
    	
    }

}