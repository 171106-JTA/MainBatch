/**
 *  Author: Antony Lulciuc
 *  Creates essential records used by system
 *  All: US-State and US-City codes
 */
DROP TABLE CODE_LIST CASCADE CONSTRAINTS;
DROP TRIGGER CODE_LIST_COUNTER;
DROP SEQUENCE CODE_LIST_SEQ;


CREATE TABLE CODE_LIST (
    id INTEGER,
    code VARCHAR2(32) NOT NULL,
    value VARCHAR2(32) NOT NULL,
    description VARCHAR2(250) NULL,
    CONSTRAINT pk_code_id PRIMARY KEY (id),
    CONSTRAINT unique_code UNIQUE (code,value,description)
);

-- CREATE SEQUENCE 
CREATE SEQUENCE CODE_LIST_SEQ 
    START WITH 1
    INCREMENT BY 1;

-- CREATE AUTO INCREMENTER 
CREATE OR REPLACE TRIGGER CODE_LIST_COUNTER
    BEFORE INSERT ON CODE_LIST
    FOR EACH ROW
BEGIN
    IF :new.id IS NULL THEN 
        SELECT CODE_LIST_SEQ.nextval INTO :new.id FROM DUAL;
    END IF;
END;
/

-- CREATE STATE CODES 
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE) VALUES('US-STATE', 'Alabama', 'AL');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE) VALUES('US-STATE', 'Alaska', 'AK');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE) VALUES('US-STATE', 'Arizona', 'AZ');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE) VALUES('US-STATE', 'Arkansas', 'AR');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE) VALUES('US-STATE', 'California', 'CA');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE) VALUES('US-STATE', 'Colorado', 'CO');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE) VALUES('US-STATE', 'Connecticut', 'CT');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE) VALUES('US-STATE', 'Delaware', 'DE');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE) VALUES('US-STATE', 'Florida', 'FL');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE) VALUES('US-STATE', 'Georgia', 'GA');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE) VALUES('US-STATE', 'Hawaii', 'HI');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE) VALUES('US-STATE', 'Idaho', 'ID');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE) VALUES('US-STATE', 'Illinois', 'IL');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE) VALUES('US-STATE', 'Indiana', 'IN');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE) VALUES('US-STATE', 'Iowa', 'IA');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE) VALUES('US-STATE', 'Kansas', 'KS');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE) VALUES('US-STATE', 'Kentucky', 'KY');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE) VALUES('US-STATE', 'Louisiana', 'LA');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE) VALUES('US-STATE', 'Maine', 'ME');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE) VALUES('US-STATE', 'Maryland', 'MD');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE) VALUES('US-STATE', 'Massachusetts', 'MA');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE) VALUES('US-STATE', 'Michigan', 'MI');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE) VALUES('US-STATE', 'Minnesota', 'MN');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE) VALUES('US-STATE', 'Mississippi', 'MS');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE) VALUES('US-STATE', 'Missouri', 'MO');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE) VALUES('US-STATE', 'Montana', 'MT');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE) VALUES('US-STATE', 'Nebraska', 'NE');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE) VALUES('US-STATE', 'Nevada', 'NV');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE) VALUES('US-STATE', 'New Hampshire', 'NH');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE) VALUES('US-STATE', 'New Jersey', 'NJ');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE) VALUES('US-STATE', 'New Mexico', 'NM');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE) VALUES('US-STATE', 'New York', 'NY');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE) VALUES('US-STATE', 'North Carolina', 'NC');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE) VALUES('US-STATE', 'North Dakota', 'ND');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE) VALUES('US-STATE', 'Ohio', 'OH');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE) VALUES('US-STATE', 'Oklahoma', 'OK');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE) VALUES('US-STATE', 'Oregon', 'OR');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE) VALUES('US-STATE', 'Pennsylvania', 'PA');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE) VALUES('US-STATE', 'Rhode Island', 'RI');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE) VALUES('US-STATE', 'South Carolina', 'SC');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE) VALUES('US-STATE', 'South Dakota', 'SD');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE) VALUES('US-STATE', 'Tennessee', 'TN');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE) VALUES('US-STATE', 'Texas', 'TX');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE) VALUES('US-STATE', 'Utah', 'UT');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE) VALUES('US-STATE', 'Vermont', 'VT');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE) VALUES('US-STATE', 'Virginia', 'VA');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE) VALUES('US-STATE', 'Washington', 'WA');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE) VALUES('US-STATE', 'West Virginia', 'WV');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE) VALUES('US-STATE', 'Wisconsin', 'WI');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE) VALUES('US-STATE', 'Wyoming', 'WY');

--CITY CODES
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Birmingham International Airport', 'BHM');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Dothan Regional Airport', 'DHN');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Huntsville International Airport', 'HSV');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Mobile', 'MOB');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Montgomery', 'MGM');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Anchorage International Airport', 'ANC');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Fairbanks International Airport', 'FAI');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Juneau International Airport', 'JNU');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Flagstaff', 'FLG');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Phoenix, Phoenix Sky Harbor International Airport', 'PHX');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Tucson International Airport', 'TUS');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Yuma International Airport', 'YUM');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Fayetteville', 'FYV');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Little Rock National Airport', 'LIT');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Burbank', 'BUR');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Fresno', 'FAT');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Long Beach', 'LGB');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Los Angeles International Airport', 'LAX');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Oakland', 'OAK');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Ontario', 'ONT');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Palm Springs', 'PSP');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Sacramento', 'SMF');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'San Diego', 'SAN');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'San Francisco International Airport', 'SFO');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'San Jose', 'SJC');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Santa Ana', 'SNA');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Aspen', 'ASE');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Colorado Springs', 'COS');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Denver International Airport', 'DEN');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Grand Junction', 'GJT');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Pueblo', 'PUB');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Hartford', 'BDL');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Washington, Dulles International Airport', 'IAD');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Washington National Airport', 'DCA');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Daytona Beach', 'DAB');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Fort Lauderdale - Hollywood International Airport', 'FLL');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Fort Meyers', 'RSW');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Jacksonville', 'JAX');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Key West International Airport', 'EYW');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Miami International Airport', 'MIA');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Orlando', 'MCO');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Pensacola', 'PNS');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'St.Petersburg', 'PIE');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Sarasota', 'SRQ');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Tampa', 'TPA');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'West Palm Beach', 'PBI');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Panama City - Bay County International Airport', 'PFN');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Atlanta Hartsfield International Airport', 'ATL');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Augusta', 'AGS');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Savannah', 'SAV');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Hilo', 'ITO');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Honolulu International Airport', 'HNL');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Kahului', 'OGG');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Kailua', 'KOA');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Lihue', 'LIH');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Boise', 'BOI');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Chicago Midway Airport', 'MDW');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Chicago, O''Hare International Airport Airport', 'ORD');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Moline', 'MLI');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Peoria', 'PIA');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Evansville', 'EVV');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Fort Wayne', 'FWA');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Indianapolis International Airport', 'IND');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'South Bend', 'SBN');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Cedar Rapids', 'CID');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Des Moines', 'DSM');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Wichita', 'ICT');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Lexington', 'LEX');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Louisville', 'SDF');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Baton Rouge', 'BTR');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'New Orleans International Airport', 'MSY');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Shreveport', 'SHV');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Augusta', 'AUG');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Bangor', 'BGR');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Portland', 'PWM');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Baltimore', 'BWI');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Boston, Logan International Airport', 'BOS');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Hyannis', 'HYA');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Nantucket', 'ACK');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Worcester', 'ORH');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Battlecreek', 'BTL');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Detroit Metropolitan Airport', 'DTW');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Detroit', 'DET');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Flint', 'FNT');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Grand Rapids', 'GRR');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Kalamazoo - Battle Creek International Airport', 'AZO');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Lansing', 'LAN');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Saginaw', 'MBS');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Duluth', 'DLH');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Minneapolis / St.Paul International Airport', 'MSP');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Rochester', 'RST');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Gulfport', 'GPT');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Jackson', 'JAN');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Kansas City', 'MCI');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'St Louis, Lambert International Airport', 'STL');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Springfield', 'SGF');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Billings', 'BIL');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Lincoln', 'LNK');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Omaha', 'OMA');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Las Vegas, Las Vegas McCarran International Airport', 'LAS');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Reno - Tahoe International Airport', 'RNO');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Manchester', 'MHT');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Atlantic City International Airport', 'ACY');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Newark International Airport', 'EWR');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Trenton', 'TTN');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Albuquerque International Airport', 'ABQ');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Alamogordo', 'ALM');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Albany International Airport', 'ALB');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Buffalo', 'BUF');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Islip', 'ISP');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'New York, John F Kennedy International Airport', 'JFK');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'New York, La Guardia Airport', 'LGA');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Newburgh', 'SWF');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Rochester', 'ROC');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Syracuse', 'SYR');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Westchester', 'HPN');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Asheville', 'AVL');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Charlotte / Douglas International Airport', 'CLT');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Fayetteville', 'FAY');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Greensboro', 'GSO');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Raleigh', 'RDU');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Winston - Salem', 'INT');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Bismark', 'BIS');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Fargo', 'FAR');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Akron', 'CAK');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Cincinnati', 'CVG');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Cleveland', 'CLE');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Columbus', 'CMH');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Dayton', 'DAY');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Toledo', 'TOL');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Oklahoma City', 'OKC');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Tulsa', 'TUL');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Eugene', 'EUG');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Portland International Airport', 'PDX');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Portland, Hillsboro Airport', 'HIO');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Salem', 'SLE');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Allentown', 'ABE');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Erie', 'ERI');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Harrisburg', 'MDT');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Philadelphia', 'PHL');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Pittsburgh', 'PIT');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Scranton', 'AVP');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Providence - T.F.Green Airport', 'PVD');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Charleston', 'CHS');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Columbia', 'CAE');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Greenville', 'GSP');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Myrtle Beach', 'MYR');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Pierre', 'PIR');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Rapid City', 'RAP');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Sioux Falls', 'FSD');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Bristol', 'TRI');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Chattanooga', 'CHA');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Knoxville', 'TYS');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Memphis', 'MEM');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Nashville', 'BNA');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Amarillo', 'AMA');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Austin Bergstrom International Airport', 'AUS');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Corpus Christi', 'CRP');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Dallas Love Field Airport', 'DAL');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Dallas / Fort Worth International Airport', 'DFW');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'El Paso', 'ELP');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Houston, William B Hobby Airport', 'HOU');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Houston, George Bush Intercontinental Airport', 'IAH');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Lubbock', 'LBB');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Midland', 'MAF');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'San Antonio International Airport', 'SAT');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Salt Lake City', 'SLC');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Burlington', 'BTV');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Montpelier', 'MPV');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Rutland', 'RUT');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Dulles', 'IAD');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Newport News', 'PHF');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Norfolk', 'ORF');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Richmond', 'RIC');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Roanoke', 'ROA');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Pasco, Pasco / Tri - Cities Airport', 'PSC');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Seattle, Tacoma International Airport', 'SEA');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Spokane International Airport', 'GEG');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Charleston', 'CRW');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Clarksburg', 'CKB');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Green Bay', 'GRB');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Madison', 'MSN');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Milwaukee', 'MKE');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Casper', 'CPR');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Cheyenne', 'CYS');
INSERT INTO CODE_LIST(CODE, DESCRIPTION, VALUE)VALUES('CITY-CODE', 'Jackson Hole', 'JAC');

--STATE CITY CODEGROUPS

INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'AL', 'BHM');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'AL', 'DHN');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'AL', 'HSV');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'AL', 'MOB');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'AL', 'MGM');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'AK', 'ANC');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'AK', 'FAI');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'AK', 'JNU');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'AZ', 'FLG');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'AZ', 'PHX');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'AZ', 'TUS');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'AZ', 'YUM');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'AR', 'FYV');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'AR', 'LIT');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'CA', 'BUR');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'CA', 'FAT');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'CA', 'LGB');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'CA', 'LAX');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'CA', 'OAK');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'CA', 'ONT');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'CA', 'PSP');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'CA', 'SMF');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'CA', 'SAN');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'CA', 'SFO');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'CA', 'SJC');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'CA', 'SNA');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'CO', 'ASE');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'CO', 'COS');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'CO', 'DEN');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'CO', 'GJT');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'CO', 'PUB');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'CT', 'BDL');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'DC', 'IAD');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'DC', 'DCA');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'FL', 'DAB');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'FL', 'FLL');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'FL', 'RSW');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'FL', 'JAX');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'FL', 'EYW');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'FL', 'MIA');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'FL', 'MCO');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'FL', 'PNS');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'FL', 'PIE');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'FL', 'SRQ');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'FL', 'TPA');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'FL', 'PBI');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'FL', 'PFN');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'GA', 'ATL');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'GA', 'AGS');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'GA', 'SAV');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'HI', 'ITO');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'HI', 'HNL');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'HI', 'OGG');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'HI', 'KOA');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'HI', 'LIH');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'ID', 'BOI');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'IL', 'MDW');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'IL', 'ORD');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'IL', 'MLI');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'IL', 'PIA');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'IN', 'EVV');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'IN', 'FWA');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'IN', 'IND');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'IN', 'SBN');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'IA', 'CID');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'IA', 'DSM');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'KS', 'ICT');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'KY', 'LEX');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'KY', 'SDF');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'LA', 'BTR');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'LA', 'MSY');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'LA', 'SHV');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'ME', 'AUG');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'ME', 'BGR');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'ME', 'PWM');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'MD', 'BWI');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'MA', 'BOS');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'MA', 'HYA');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'MA', 'ACK');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'MA', 'ORH');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'MI', 'BTL');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'MI', 'DTW');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'MI', 'DET');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'MI', 'FNT');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'MI', 'GRR');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'MI', 'AZO');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'MI', 'LAN');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'MI', 'MBS');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'MN', 'DLH');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'MN', 'MSP');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'MN', 'RST');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'MS', 'GPT');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'MS', 'JAN');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'MO', 'MCI');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'MO', 'STL');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'MO', 'SGF');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'MT', 'BIL');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'NE', 'LNK');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'NE', 'OMA');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'NV', 'LAS');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'NV', 'RNO');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'NH', 'MHT');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'NJ', 'ACY');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'NJ', 'EWR');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'NJ', 'TTN');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'NM', 'ABQ');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'NM', 'ALM');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'NY', 'ALB');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'NY', 'BUF');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'NY', 'ISP');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'NY', 'JFK');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'NY', 'LGA');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'NY', 'SWF');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'NY', 'ROC');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'NY', 'SYR');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'NY', 'HPN');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'NC', 'AVL');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'NC', 'CLT');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'NC', 'FAY');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'NC', 'GSO');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'NC', 'RDU');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'NC', 'INT');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'ND', 'BIS');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'ND', 'FAR');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'OH', 'CAK');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'OH', 'CVG');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'OH', 'CLE');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'OH', 'CMH');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'OH', 'DAY');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'OH', 'TOL');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'OK', 'OKC');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'OK', 'TUL');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'OR', 'EUG');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'OR', 'PDX');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'OR', 'HIO');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'OR', 'SLE');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'PA', 'ABE');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'PA', 'ERI');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'PA', 'MDT');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'PA', 'PHL');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'PA', 'PIT');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'PA', 'AVP');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'RI', 'PVD');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'SC', 'CHS');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'SC', 'CAE');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'SC', 'GSP');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'SC', 'MYR');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'SD', 'PIR');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'SD', 'RAP');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'SD', 'FSD');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'TN', 'TRI');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'TN', 'CHA');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'TN', 'TYS');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'TN', 'MEM');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'TN', 'BNA');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'TX', 'AMA');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'TX', 'AUS');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'TX', 'CRP');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'TX', 'DAL');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'TX', 'DFW');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'TX', 'ELP');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'TX', 'HOU');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'TX', 'IAH');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'TX', 'LBB');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'TX', 'MAF');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'TX', 'SAT');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'UT', 'SLC');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'VT', 'BTV');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'VT', 'MPV');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'VT', 'RUT');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'VA', 'IAD');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'VA', 'PHF');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'VA', 'ORF');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'VA', 'RIC');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'VA', 'ROA');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'WA', 'PSC');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'WA', 'SEA');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'WA', 'GEG');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'WV', 'CRW');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'WV', 'CKB');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'WI', 'GRB');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'WI', 'MSN');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'WI', 'MKE');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'WY', 'CPR');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'WY', 'CYS');
INSERT INTO CODE_LIST(CODE, VALUE, DESCRIPTION)VALUES('CITY-CODE-GROUP', 'WY', 'JAC');

COMMIT;


