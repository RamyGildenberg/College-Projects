package application;

// Address book originator class, following the memento design pattern.
public class AddressBookOriginator { 
	private String state;
	
	public void setState (String state) {
		this.state = state;
	}
	
	public String getState() {
		return this.state;
	}
	
	public AddressBookMemento saveStateToAddressBookMemento() {
		return new AddressBookMemento (this.state);
	}
}
