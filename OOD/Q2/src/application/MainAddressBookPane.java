package application;

import java.util.ArrayList;

public class MainAddressBookPane extends AddressBookDecorator {
	private AddButton jbtAdd;
	private UndoButton jbtUndo;
	private RedoButton jbtRedo;
	
	// adding write operations to the pane with 3 more buttons (add, undo, redo)
	public MainAddressBookPane (BaseAddressBookPane addressBookPane) {
		super(addressBookPane);
		jbtAdd = new AddButton(getAddressBookPane(), getRandomAccessFile());
		jbtUndo = new UndoButton(getAddressBookPane(), getRandomAccessFile());
		jbtRedo = new RedoButton(getAddressBookPane(), getRandomAccessFile());
		ArrayList<CommandButton> buttonsAL = new ArrayList<CommandButton>();
		buttonsAL.add(jbtAdd);
		buttonsAL.add(jbtUndo);
		buttonsAL.add(jbtRedo);
		buttonsInitialize(buttonsAL);
		new FirstButton(getAddressBookPane(), getRandomAccessFile()).Execute();
	}
	
	
	
	
}
