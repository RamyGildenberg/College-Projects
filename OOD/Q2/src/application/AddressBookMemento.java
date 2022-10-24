package application;


//Address book memento class, following the memento design pattern.
public class AddressBookMemento {
	private String state;
	
	public AddressBookMemento(String state) {
		this.state = state;
	}
	
	public String getState() {
		return this.state;
	}

}
