class EmployeeAccount{
	constructor(){
		var username;
		var firstname;
		var middlename;
		var lastname;
		var email;
		var address;
		var city;
		var state;
		var islocked;
		var isadmin;
		var employeeid;

		function getUsername() {
			return username;
		}

		function setUsername(username) {
			this.username = username;
		}

		function getPassword() {
			return password;
		}

		function setPassword(password) {
			this.password = password;
		}

		function getFirstname() {
			return firstname;
		}

		function setFirstname(firstname) {
			this.firstname = firstname;
		}

		function getMiddlename() {
			return middlename;
		}

		function setMiddlename(middlename) {
			this.middlename = middlename;
		}

		function getLastname() {
			return lastname;
		}

		function setLastname(lastname) {
			this.lastname = lastname;
		}

		function getEmail() {
			return email;
		}

		function setEmail(email) {
			this.email = email;
		}

		function getAddress() {
			return address;
		}

		function setAddress(address) {
			this.address = address;
		}

		function getCity() {
			return city;
		}

		function setCity(city) {
			this.city = city;
		}

		function getState() {
			return state;
		}

		function setState(state) {
			this.state = state;
		}
		
		function isLocked() {
			return islocked;
		}

		function setLocked(islocked) {
			this.islocked = islocked;
		}

		function isAdmin() {
			return isadmin;
		}

		function setAdmin(isadmin) {
			this.isadmin = isadmin;
		}
		
		function getEmployeeid() {
			return employeeid;
		}

		function setEmployeeid(employeeid) {
			this.employeeid = employeeid;
		}
		
		function toAJAX() {
			return 	  "username:" + this.getUsername() + "&"
					+ "firstname:" + this.getFirstname() + "&"
					+ "middlename:" + this.getMiddlename() + "&"
					+ "lastname:" + this.getLastname() + "&"
					+ "email:" + this.getEmail() + "&"
					+ "address:" + this.getAddress() + "&"
					+ "city:" + this.getCity() + "&"
					+ "state:" + this.getState() + "&"
					+ "islocked:" + this.isLocked() + "&"
					+ "isadmin:" + this.isAdmin() + "&"
					+ "employeeid:" + this.getEmployeeid();
		}
	}
}