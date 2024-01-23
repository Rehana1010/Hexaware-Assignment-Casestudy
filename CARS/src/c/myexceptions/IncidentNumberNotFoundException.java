package c.myexceptions;


public class IncidentNumberNotFoundException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IncidentNumberNotFoundException(String string) {
		System.out.println("No incidentID found");
	}

	public IncidentNumberNotFoundException() {
		System.out.println("No incidentID found");
	}
	public String toString() {
		return "Incident ID not found";
	}
}