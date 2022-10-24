package application;

import java.io.RandomAccessFile;
import java.util.ArrayList;


// all address book pane classes must implement this basic functionality
public interface BaseAddressBookPane {
	void buttonsInitialize (ArrayList<CommandButton> buttonsAL);
	ButtonlessAddressBookPane getPane();
	RandomAccessFile getRandomAccessFile();
}
