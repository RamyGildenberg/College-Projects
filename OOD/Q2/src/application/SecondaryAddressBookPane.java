package application;

import java.util.ArrayList;

public class SecondaryAddressBookPane extends AddressBookDecorator {
	private LastButton jbtLast;
	private FirstButton jbtFirst;
	private NextButton jbtNext;
	private PreviousButton jbtPrevious;
	
	// just 4 buttons to the pane, only read (no write operations)
	public SecondaryAddressBookPane(BaseAddressBookPane addressBookPane) {
		super(addressBookPane);
		jbtFirst = new FirstButton(getAddressBookPane(), getRandomAccessFile());
		jbtNext = new NextButton(getAddressBookPane(), getRandomAccessFile());
		jbtPrevious = new PreviousButton(getAddressBookPane(), getRandomAccessFile());
		jbtLast = new LastButton(getAddressBookPane(), getRandomAccessFile());
		ArrayList<CommandButton> buttonsAL = new ArrayList<CommandButton>();
		buttonsAL.add(jbtFirst);
		buttonsAL.add(jbtNext);
		buttonsAL.add(jbtPrevious);
		buttonsAL.add(jbtLast);
		buttonsInitialize(buttonsAL);
		jbtFirst.Execute();
	}
	
}
