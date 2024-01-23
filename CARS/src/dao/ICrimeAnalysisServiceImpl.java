package dao;


import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import entity.*;
import c.myexceptions.IncidentNumberNotFoundException;
import util.DBConnection;
import util.PropertyUtil;

public class ICrimeAnalysisServiceImpl implements ICrimeAnalysisService {

    private Connection connection;

    public ICrimeAnalysisServiceImpl() {
        this.connection = DBConnection.getConnection(PropertyUtil.getPropertyString());
    }

    @Override
	public boolean createIncident(Incidents incident) {
        try {
String sql = "INSERT INTO Incidents (IncidentID, IncidentType, IncidentDate, Location, Descriptions, IncStatus, VictimID, SuspectID) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, incident.getIncidentID());
                preparedStatement.setString(2, incident.getIncidentType());
                preparedStatement.setDate(3, new java.sql.Date(incident.getIncidentDate().getTime()));
                preparedStatement.setString(4, incident.getLocation());
                preparedStatement.setString(5, incident.getDescriptions());
                preparedStatement.setString(6, incident.getIncStatus());
                preparedStatement.setInt(7, incident.getVictimID());
                preparedStatement.setInt(8, incident.getSuspectID());

                int rowsAffected = preparedStatement.executeUpdate();

                return rowsAffected > 0;
            }
        } catch (SQLException e) {

            e.printStackTrace();
            return false;
        }
    }

	@Override
	public boolean updateIncidentStatus(int incidentId,String IncStatus) {
    	try {

            String sql = "UPDATE Incidents SET IncStatus = ? WHERE IncidentID = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, IncStatus);
                preparedStatement.setInt(2, incidentId);

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    return true;
                } else {
                    throw new IncidentNumberNotFoundException();
                }
            } catch (IncidentNumberNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        } catch (SQLException e) {

            e.printStackTrace();
            return false;
        }
		return false;
    }

	@Override
	public List<Incidents> getIncidentsInDateRange(String startDateStr, String endDateStr) {
        List<Incidents> incidentsList = new ArrayList<>();
        try {

            String sql = "SELECT * FROM Incidents WHERE IncidentDate BETWEEN ? AND ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date startDate = new java.sql.Date(dateFormat.parse(startDateStr).getTime());
                Date endDate = new java.sql.Date(dateFormat.parse(endDateStr).getTime());
                preparedStatement.setDate(1, startDate);
                preparedStatement.setDate(2, endDate);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Incidents incident = new Incidents();
                        incident.setIncidentID(resultSet.getInt("incidentID"));
                        incident.setIncidentType(resultSet.getString("incidentType"));
                        incident.setIncidentDate(resultSet.getDate("incidentDate"));
                        incident.setLocation(resultSet.getString("location"));
                        incident.setDescriptions(resultSet.getString("descriptions"));
                        incident.setIncStatus(resultSet.getString("Incstatus"));
                        incident.setVictimID(resultSet.getInt("victimID"));
                        incident.setSuspectID(resultSet.getInt("suspectID"));
                        incidentsList.add(incident);
                    }
                }
            }
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
        return incidentsList;
    }

	@Override
	public List<Incidents> searchIncidentsByType(String incidentType) {
        List<Incidents> incidentsList = new ArrayList<>();

        try {


            String sql = "SELECT * FROM Incidents WHERE IncidentType = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, incidentType);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                	if (!resultSet.isBeforeFirst()) {
                        throw new IncidentNumberNotFoundException("No incidents found in the specified date range.");
                    }

                    while (resultSet.next()) {
                        Incidents incident = new Incidents();


                        incident.setIncidentID(resultSet.getInt("IncidentID"));
                        incident.setIncidentType(resultSet.getString("IncidentType"));
                        incident.setIncidentDate(resultSet.getDate("IncidentDate"));
                        incident.setLocation(resultSet.getString("Location"));
                        incident.setDescriptions(resultSet.getString("Descriptions"));
                        incident.setIncStatus(resultSet.getString("IncStatus"));
                        incident.setVictimID(resultSet.getInt("VictimID"));
                        incident.setSuspectID(resultSet.getInt("SuspectID"));

                        incidentsList.add(incident);
                    }
                } catch (IncidentNumberNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return incidentsList;
    }
	@Override
	public Object generateIncidentReport(Incidents incident, int reportingOfficer) {
        try {
            String reportDetails = "Incident Report\n"
                    + "Incident Type: " + incident.getIncidentType() + "\n"
                    + "Incident Date: " + incident.getIncidentDate() + "\n"
                    + "Location: " + incident.getLocation() + "\n"
                    + "Description: " + incident.getDescriptions() + "\n"
                    + "Status: " + incident.getIncStatus();

            Map<String, Object> reportObject = new HashMap<>();
            reportObject.put("reportDetails", reportDetails);
            reportObject.put("reportDate", new java.util.Date());
            reportObject.put("status", "Draft");

            String insertReportQuery = "INSERT INTO Reports (IncidentID, ReportDetails, ReportDate, Statuss, ReportingOfficer) " +
                    "VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(insertReportQuery)) {
                statement.setInt(1, incident.getIncidentID());
                statement.setString(2, reportDetails);
                statement.setDate(3, new java.sql.Date(new java.util.Date().getTime()));
                statement.setString(4, "Draft");
                statement.setInt(5, reportingOfficer);

                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Incident report saved to the database.");
                } else {
                    System.out.println("Failed to save incident report to the database.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }

            return reportObject;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
	@Override
	public Incidents getIncidentById(int incidentId) {
	    try {
	        String sql = "SELECT * FROM Incidents WHERE IncidentID = ?";
	        try (PreparedStatement statement = connection.prepareStatement(sql)) {
	            statement.setInt(1, incidentId);

	            try (ResultSet resultSet = statement.executeQuery()) {
	                if (resultSet.next() ) {

	                	 System.out.println("Incident Type: " + resultSet.getString("IncidentType"));

	 	                System.out.println("Incident Date: " + resultSet.getDate("IncidentDate"));
	 	                System.out.println("Description: " + resultSet.getString("Descriptions"));
	 	                System.out.println("Location: " + resultSet.getString("Location"));
	 	                System.out.println("IncStatus: " + resultSet.getString("IncStatus"));
	 	                System.out.println("Suspect ID: " + resultSet.getInt("SuspectID"));
	 	               System.out.println("Victim ID: " + resultSet.getInt("VictimID"));

	                } else {
	                    throw new IncidentNumberNotFoundException("Incident with ID " + incidentId + " not found.");
	                }
	            } catch (IncidentNumberNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return null;
	    }
		return null;
	}




}