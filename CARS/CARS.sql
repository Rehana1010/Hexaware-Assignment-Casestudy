create database cars;
use cars;

-- Create Victims Table
CREATE TABLE Victims (
    VictimID INT auto_increment PRIMARY KEY,
    FirstName VARCHAR(255),
    LastName VARCHAR(255),
    DateOfBirth DATE,
    Gender CHAR(1),
    ContactInformation VARCHAR(255)
);

-- Create Suspects Table
CREATE TABLE Suspects (
    SuspectID INT auto_increment PRIMARY KEY,
    FirstName VARCHAR(255),
    LastName VARCHAR(255),
    DateOfBirth DATE,
    Gender CHAR(1),
    ContactInformation VARCHAR(255)    
);


-- Create Incidents Table
CREATE TABLE Incidents (
    IncidentID INT auto_increment PRIMARY KEY,
    IncidentType VARCHAR(255),
    IncidentDate DATE,
    Location VARCHAR(200),
    Descriptions TEXT,
    IncStatus VARCHAR(50),
    VictimID INT,
    SuspectID INT,
    FOREIGN KEY (VictimID) REFERENCES Victims(VictimID),
    FOREIGN KEY (SuspectID) REFERENCES Suspects(SuspectID)
);


-- Create Law Enforcement Agencies Table
CREATE TABLE LawEnforcementAgencies (
    AgencyID INT auto_increment PRIMARY KEY,
    AgencyName VARCHAR(255),
    Jurisdiction VARCHAR(255),
    ContactInformation VARCHAR(255),
    Officers JSON
);
CREATE TABLE Evidence (
    EvidenceID INT auto_increment PRIMARY KEY,
    Descriptions TEXT,
    LocationFound VARCHAR(255),
    IncidentID INT,
    FOREIGN KEY (IncidentID) REFERENCES Incidents(IncidentID)
);


-- Create Officers Table
CREATE TABLE Officers (
    OfficerID INT auto_increment PRIMARY KEY,
    FirstName VARCHAR(255),
    LastName VARCHAR(255),
    BadgeNumber VARCHAR(50),
    Ranks varchar(50),
    ContactInformation VARCHAR(255),
	AgencyID INT,
    FOREIGN KEY (AgencyID) REFERENCES LawEnforcementAgencies(AgencyID)
);


-- Create Reports Table
CREATE TABLE Reports (
    ReportID INT auto_increment PRIMARY KEY,
    IncidentID INT,
    ReportingOfficer INT,
    ReportDate DATE,
    ReportDetails TEXT,
    ReportStatus VARCHAR(50),
    FOREIGN KEY (IncidentID) REFERENCES Incidents(IncidentID),
    FOREIGN KEY (ReportingOfficer) REFERENCES Officers(OfficerID)
);

-- Inserting data into Victims table
INSERT INTO Victims (VictimID, FirstName, LastName, DateOfBirth, Gender, ContactInformation)
VALUES
    (1, 'Lucy', 'K', '1992-10-15', 'F', '12345'),
    (2, 'Daisy', 'P', '1989-08-21', 'F','67890'),
    (3, 'Nick', 'V', '1987-05-17', 'M', '36985'),
    (4, 'Madhu', 'C', '1999-08-28', 'F', '21047'),
    (5, 'Vishnu', 'N', '1991-12-05', 'M', '85214');


-- Inserting data into Suspects table
INSERT INTO Suspects (SuspectID, FirstName, LastName, DateOfBirth, Gender, ContactInformation)
VALUES
    (1, 'Kiran', 'J', '1982-03-10', 'M', '45623'),
    (2, 'Rohith', 'M', '1995-11-28', 'M', '78452'),
    (3, 'Lasya', 'N', '1977-06-15', 'F', '98523'),
    (4, 'Naziya', 'P', '1992-02-19', 'F', '73214'),
    (5, 'Lalith', 'T', '1985-12-03', 'M','98634');

-- Inserting data into Officers table
INSERT INTO Officers (OfficerID, FirstName, LastName, BadgeNumber, Ranks, ContactInformation, AgencyID)
VALUES
    (1, 'Michael', 'G', '12345', 'Sergeant', '555-1111', 1),
    (2, 'Akhira', 'K', '54321', 'Detective', '555-2222', 2),
    (3, 'Pavan', 'K', '98765', 'Officer', '555-3333', 3),
    (4, 'Ankitha', 'P', '56789', 'Captain', '555-4444', 4),
    (5, 'Nanda', 'M', '87654', 'Lieutenant', '555-5555', 5);


-- Inserting data into Incidents table
INSERT INTO Incidents (IncidentID, IncidentType, IncidentDate, Location, Descriptions, IncStatus, VictimID, SuspectID)
VALUES
    (1, 'Robbery', '2023-12-15', '40.7128, -74.006','Store robbery', 'Open', 1, 1),
    (2, 'Homicide', '2023-12-16', '34.0522, -118.2437', 'Assault and murder', 'Closed', 2, 2),
    (3, 'Theft', '2023-12-17', '41.8781, -87.6298', 'Car theft', 'Open', 3, 3),
    (4, 'Assault', '2023-12-18', '39.9526, -75.1652', 'Physical assault', 'Closed', 4, 4),
    (5, 'Burglary', '2023-12-19', '29.7604, -95.3698', 'Residential burglary', 'Open', 5, 5);

INSERT INTO LawEnforcementAgencies (AgencyID, AgencyName, Jurisdiction,ContactInformation,Officers)
VALUES
    (1, 'City Police Department', 'Citywide', '11111','[{"OfficerID": 1, "FirstName": "Michael", "LastName": "G", "BadgeNumber": "12345", "Rank": "Sergeant"}]'),
    (2, 'County Sheriff Office', 'Countrywide', '22222','[{"OfficerID": 2, "FirstName": "Akhira", "LastName": "K", "BadgeNumber": "54321", "Rank": "Detective"}]'),
    (3, 'State Patrol', 'Statewide', '33333','[{"OfficerID": 3, "FirstName": "Pavan", "LastName": "K", "BadgeNumber": "98765", "Rank": "Officer"}]'),
    (4, 'Federal Bureau of Investigation', 'National','44444','[{"OfficerID": 4, "FirstName": "Ankitha", "LastName": "P", "BadgeNumber": "56789", "Rank": "Captain"}]'),
    (5, 'Drug Enforcement Administration', 'National','55555','[{"OfficerID": 5, "FirstName": "Nanda", "LastName": "M", "BadgeNumber": "87654", "Rank": "Lieutenant"}]');

INSERT INTO Reports (ReportID, IncidentID, ReportingOfficer, ReportDate, ReportDetails, ReportStatus)
VALUES
(1, 1, 1, '2023-01-10', 'Investigation ongoing', 'Draft'),
(2, 2, 2, '2023-02-10', 'Case closed. Suspect apprehended.', 'Finalized'),
(3, 3, 3, '2023-03-10', 'Witness statements collected', 'Draft'),
(4, 4, 4, '2023-04-10', 'Suspect identified and charged', 'Finalized'),
(5, 5, 5, '2023-05-10', 'Forensic analysis in progress', 'Draft');

INSERT INTO Evidence (EvidenceID, Descriptions, LocationFound, IncidentID)
VALUES
(1, 'Surveillance footage', 'Store premises', 1),
(2, 'Gun casing', 'Parking lot', 2),
(3, 'Stolen items', 'Mall premises', 3),
(4, 'Security camera recording', 'Bar premises', 4),
(5, 'Forced entry tools', 'Residence', 5);

