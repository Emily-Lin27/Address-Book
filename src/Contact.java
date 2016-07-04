
public class Contact {
	public String name;
	public String address;
	public String phoneNumber;
	public String email;

	public Contact(String nameValue, String addressValue, String phoneValue, String emailValue) {
		name = nameValue;
		address = addressValue;
		phoneNumber = phoneValue;
		email = emailValue;
	}

	//******************  THE GETTER METHODS *******************************
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	//******************************* THE SETTER METHODS **************************** 
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	public String toString() { // this converts just the name of the contact to a string (no longer an object)
		return getName();
	}
}
