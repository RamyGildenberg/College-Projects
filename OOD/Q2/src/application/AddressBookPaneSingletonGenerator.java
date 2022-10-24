package application;

import java.util.ArrayList;

abstract public class AddressBookPaneSingletonGenerator {
	private static int counter = 0;
	private final static int MAX_ADDRESSBOOK_INSTANCES = 3;
	private final static int MAX_SECONDARY_ADDRESSBOOK_INSTANCES = 2;
	private static ArrayList<BaseAddressBookPane> addressBookPaneList = new ArrayList<BaseAddressBookPane>();
	
	public static BaseAddressBookPane getInstance() { // first 2 instances will be secondary panes, the third and last will be main pane.
		if (counter < MAX_SECONDARY_ADDRESSBOOK_INSTANCES) {
			BaseAddressBookPane newPane = new SecondaryAddressBookPane(ButtonlessAddressBookPane.getInstance());
			addressBookPaneList.add(newPane);
			counter++;
			return newPane;
		}
		else if (counter < MAX_ADDRESSBOOK_INSTANCES) {
			BaseAddressBookPane newPane = new MainAddressBookPane(new SecondaryAddressBookPane(ButtonlessAddressBookPane.getInstance()));
			addressBookPaneList.add(newPane);
			counter++;
			return newPane;
		}
		System.out.println("Singelton violation. Only 3 panes were created");
		return null;
	}
	 
	
}
