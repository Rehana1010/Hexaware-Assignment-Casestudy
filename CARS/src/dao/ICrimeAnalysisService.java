package dao;

import java.util.List;

import entity.Incidents;

public interface ICrimeAnalysisService {

	
	List<Incidents> searchIncidentsByType(String incidentType);

	List<Incidents> getIncidentsInDateRange(String startDateStr, String endDateStr);

	boolean updateIncidentStatus(int incidentId, String IncStatus);

	boolean createIncident(Incidents incident);

	Object generateIncidentReport(Incidents incident, int reportingOfficer);

	Incidents getIncidentById(int incidentId);

}
