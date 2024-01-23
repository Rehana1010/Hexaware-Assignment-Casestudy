package entity;

import java.util.Date;


public class Incidents {
    private int incidentID;
    private String incidentType;
    private Date incidentDate;
    private String location; 
    private String descriptions;
    private String IncStatus;
    private int victimID;
    private int suspectID;
    
	// Constructors
    public Incidents() {
    	super();
    }

    public Incidents(int incidentID, String incidentType, Date incidentDate, String location,
                    String descriptions, String IncStatus, int victimID, int suspectID) {
        this.incidentID = incidentID;
        this.incidentType = incidentType;
        this.incidentDate = incidentDate;
        this.location = location;
        this.descriptions = descriptions;
        this.IncStatus = IncStatus;
        this.victimID = victimID;
        this.suspectID = suspectID;
    }

	public int getIncidentID() {
		return incidentID;
	}

	public void setIncidentID(int incidentID) {
		this.incidentID = incidentID;
	}

	public String getIncidentType() {
		return incidentType;
	}

	public void setIncidentType(String incidentType) {
		this.incidentType = incidentType;
	}

	public Date getIncidentDate() {
		return incidentDate;
	}

	public void setIncidentDate(Date incidentDate) {
		this.incidentDate = incidentDate;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(String descriptions) {
		this.descriptions = descriptions;
	}

	public String getIncStatus() {
		return IncStatus;
	}

	public void setIncStatus(String IncStatus) {
		this.IncStatus = IncStatus;
	}

	public int getVictimID() {
		return victimID;
	}

	public void setVictimID(int victimID) {
		this.victimID = victimID;
	}

	public int getSuspectID() {
		return suspectID;
	}

	public void setSuspectID(int suspectID) {
		this.suspectID = suspectID;
	}

	public String toString() {
        return "Incidents{" +
                "incidentID=" + incidentID +
                ", incidentType='" + incidentType + '\'' +
                ", incidentDate=" + incidentDate +
                ", location='" + location + '\'' +
                ", description='" + descriptions + '\'' +
                ", status='" + IncStatus + '\'' +
                ", victimID=" + victimID +
                ", suspectID=" + suspectID +
                '}';
    }

}
