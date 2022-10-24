package application;

import java.io.RandomAccessFile;
import java.util.ArrayList;

// address book decorator class, following the decorator design pattern
public abstract class AddressBookDecorator implements BaseAddressBookPane {
	private BaseAddressBookPane addressBookPane;
	
	public AddressBookDecorator (BaseAddressBookPane addressBookPane) {
		this.addressBookPane = addressBookPane;
	}
	
	@Override
	public ButtonlessAddressBookPane getPane() {
		return addressBookPane.getPane();
	}
	
	@Override
	public RandomAccessFile getRandomAccessFile() {
		return addressBookPane.getRandomAccessFile();
	}
	
	@Override
	public void buttonsInitialize(ArrayList<CommandButton> buttonsAL) {
		addressBookPane.buttonsInitialize(buttonsAL);
	}
	
	public ButtonlessAddressBookPane getAddressBookPane() {
		return addressBookPane.getPane();
	}
}
