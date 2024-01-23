package main;
import entity.Incidents;
import c.myexceptions.IncidentNumberNotFoundException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;



import dao.ICrimeAnalysisService;
import dao.ICrimeAnalysisServiceImpl;
public class MainModule {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    static ICrimeAnalysisService icrimeanalysisService = new ICrimeAnalysisServiceImpl();
    
    
    public static void main(String[] args) {
       
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n******** Main Menu ********");
            System.out.println("1. Create Incident");
            System.out.println("2. Update Incident Status");
            System.out.println("3. Get Incidents in Date Range");
            System.out.println("4. Search Incidents");
            System.out.println("5. Generate Incident Report");
            System.out.println("6. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
            case 1:// Create Incident
            	Incidents newIncident = new Incidents();
            	System.out.println("Enter IncidentID: ");
                int incidentId = scanner.nextInt();
                scanner.nextLine(); 
                newIncident.setIncidentID(incidentId);
            	System.out.print("Enter Incident Type: ");
            	newIncident.setIncidentType(scanner.next());
            	System.out.print("Enter Incident Date (yyyy-MM-dd): ");
            	String incidentDateStr = scanner.next();
            	try {
            		Date incidentDate = dateFormat.parse(incidentDateStr);
            		newIncident.setIncidentDate(incidentDate);
            		} catch (ParseException e) {
            			System.out.println("Invalid date format. Please use yyyy-MM-dd.");
            			break;
            			}
            	System.out.println("Enter Location: ");
            	scanner.nextLine(); // Consume the newline character
                newIncident.setLocation(scanner.nextLine());
            	System.out.print("Enter Description: ");
            	newIncident.setDescriptions(scanner.nextLine());
            	System.out.print("Enter Status: ");
            	newIncident.setIncStatus(scanner.nextLine());
            	scanner.nextLine();
            	System.out.print("Enter Victim ID: ");
            	newIncident.setVictimID(scanner.nextInt());
            	scanner.nextLine();
            	System.out.print("Enter Suspect ID: ");
            	newIncident.setSuspectID(scanner.nextInt());
            	scanner.nextLine();
            	boolean incidentCreated = icrimeanalysisService.createIncident(newIncident);
            	if (incidentCreated) {
            		System.out.println("Incident created successfully.");
            		} else {
            			System.out.println("Failed to create incident.");
            			}
            	break;
            case 2:// Update Incident Status
            	try {
            		System.out.print("Enter Incident ID for status update: ");
            		int incidentIdForUpdate = scanner.nextInt();
                    System.out.print("Enter new status: ");
                    String newStatus = scanner.next();
                    boolean statusUpdated = icrimeanalysisService.updateIncidentStatus(incidentIdForUpdate, newStatus);
                    if (statusUpdated) {
                        System.out.println("Incident status updated successfully.");
                    } else {
                        throw new IncidentNumberNotFoundException();
                    }
                } catch (IncidentNumberNotFoundException e) {
                    System.out.println(e);
                }
            	break;
            case 3:
            	// Get Incidents in Date Range
            	System.out.print("Enter start date (yyyy-MM-dd): ");
            	String startDateString = scanner.next();
            	System.out.print("Enter end date (yyyy-MM-dd): ");
            	String endDateString = scanner.next();
            	try {
            		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            		Date startDate = dateFormat.parse(startDateString);
            		Date endDate = dateFormat.parse(endDateString);
            		// Retrieve incidents in the date range
            		List<Incidents> incidentsList = icrimeanalysisService.getIncidentsInDateRange(startDateString, endDateString);
            		if (incidentsList.isEmpty()) {
            			System.out.println("No incidents found within the specified date range.");
            			} else {
            				System.out.println("Incidents within the date range:");
            				for (Incidents incident : incidentsList) {
            					System.out.println(incident);
            					}
            				}
            		} catch (Exception e) {
            			System.out.println("Error: " + e.getMessage());
            		}
                 break;
              case 4:
            	  // Search Incidents
            	  try {
            		  System.out.println("Enter Incident Type to Search: ");
            	  String incidentType = scanner.next();
            	  List<Incidents> incidentsList = icrimeanalysisService.searchIncidentsByType(incidentType);
            	  if (incidentsList.isEmpty()) {
            		  System.out.println("No incidents found for the specified type.");
            		  } else {
            			  System.out.println("Incidents with the specified type:");
            			  for (Incidents incident : incidentsList) {
            				  System.out.println(incident);
            			  }
            		  }
            	  }catch (Exception e) {
              			System.out.println("Error: " + e.getMessage());
              		}
            	  break;
             case 5:
                  
            	    // Generate Incident Report
            	    System.out.print("Enter Incident ID: ");
            	    int incidentIdForReport = scanner.nextInt();

            	    // Retrieve incident details based on the provided Incident ID
            	    Incidents incidentForReport = icrimeanalysisService.getIncidentById(incidentIdForReport);

            	    if (incidentForReport != null) {
            	        // Display incident details
            	        System.out.println("Incident Details:");
            	        System.out.println("Incident ID: " + incidentForReport.getIncidentID());
            	        System.out.println("Type: " + incidentForReport.getIncidentType());
            	        System.out.println("Date: " + incidentForReport.getIncidentDate());
            	        // Print other incident details as needed

            	        // Ask for reporting officer ID
            	        System.out.print("Enter Reporting Officer ID: ");
            	        int reportingOfficerId = scanner.nextInt();

            	        // Generate Incident Report
            	        Object generatedReport = icrimeanalysisService.generateIncidentReport(incidentForReport, reportingOfficerId);

            	        // Print or process the generatedReport as needed
            	        if (generatedReport != null) {
            	            Map<String, Object> reportDetails = (Map<String, Object>) generatedReport;
            	            System.out.println("Incident Report Generated Successfully. Report Details:");
            	            System.out.println("Report ID: " + reportDetails.get("reportID"));
            	            System.out.println("Report Date: " + reportDetails.get("reportDate"));
            	            System.out.println("Report Status: " + reportDetails.get("reportStatus"));
            	            System.out.println("Report Details:\n" + reportDetails.get("reportDetails"));
            	        } else {
            	            System.out.println("Failed to generate incident report. Please check the input details.");
            	        }
            	    } else {
            	        System.out.println("Incident found for the specified Incident ID.");
            	    }
            	    break;

   
   case 6:
	   System.out.println("Exiting Crime Analysis and Reporting System.");
	   System.exit(0);
	   break;
	 default:
    System.out.println("Invalid choice. Please enter a number between 1 and 6.");
            }
        }
    }
}


