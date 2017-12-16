package p1.revature.beans;

public class Location extends Bean {
	private int locationID;
	private String city, stateAbbreviation, streetAddress;
	private int zipCode, suiteNumber;
	
	
	public Location()
	{
		super();
	}
	/**
	 * A full constructor for Location
	 * @param locationID : the ID of this location in its database table
	 * @param city 
	 * @param stateAbbreviation : the two-character abbreviation of the state this Location is in
	 * @param streetAddress 
	 * @param zipCode
	 * @param suiteNumber
	 */
	public Location(int locationID, String city, String stateAbbreviation, String streetAddress, int zipCode,
			int suiteNumber) {
		super();
		this.locationID = locationID;
		this.city = city;
		this.stateAbbreviation = stateAbbreviation;
		this.streetAddress = streetAddress;
		this.zipCode = zipCode;
		this.suiteNumber = suiteNumber;
	}
	
	/**
	 * Constructor for every field but locationID
	 * @param city
	 * @param stateAbbreviation : the two-character abbreviation of the state this Location is in
	 * @param streetAddress
	 * @param zipCode
	 * @param suiteNumber
	 */
	public Location(String city, String stateAbbreviation, String streetAddress, int zipCode, int suiteNumber) {
		super();
		this.city = city;
		this.stateAbbreviation = stateAbbreviation;
		this.streetAddress = streetAddress;
		this.zipCode = zipCode;
		this.suiteNumber = suiteNumber;
	}
	
	/**
	 * Basic, yet complete, constructor for Location
	 * @param city
	 * @param stateAbbreviation : the two-character abbreviation of the state this Location is in
	 * @param streetAddress
	 * @param zipCode
	 */
	public Location(String city, String stateAbbreviation, String streetAddress, int zipCode) {
		super();
		this.city = city;
		this.stateAbbreviation = stateAbbreviation;
		this.streetAddress = streetAddress;
		this.zipCode = zipCode;
	}
	
	
	/**
	 * For specifying location without zip code
	 * @param city
	 * @param stateAbbreviation : the two-character abbreviation of the state this Location is in
	 * @param streetAddress
	 */
	public Location(String city, String stateAbbreviation, String streetAddress) {
		super();
		this.city = city;
		this.stateAbbreviation = stateAbbreviation;
		this.streetAddress = streetAddress;
	}

	public int getLocationID() {
		return locationID;
	}
	public void setLocationID(int locationID) {
		this.locationID = locationID;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getStateAbbreviation() {
		return stateAbbreviation;
	}
	public void setStateAbbreviation(String stateAbbreviation) {
		this.stateAbbreviation = stateAbbreviation;
	}
	public String getStreetAddress() {
		return streetAddress;
	}
	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}
	public int getZipCode() {
		return zipCode;
	}
	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
	}
	public int getSuiteNumber() {
		return suiteNumber;
	}
	public void setSuiteNumber(int suiteNumber) {
		this.suiteNumber = suiteNumber;
	}

	@Override
	public String toString() {
		return "Location [locationID=" + locationID + ", city=" + city + ", stateAbbreviation=" + stateAbbreviation
				+ ", streetAddress=" + streetAddress + ", zipCode=" + zipCode + ", suiteNumber=" + suiteNumber + "]";
	}
	
	
}
