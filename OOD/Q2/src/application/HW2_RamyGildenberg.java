package application;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
public class HW2_RamyGildenberg extends Application
{	public static void main(String[] args)
    { launch(args);
	}
	@Override
	public void start(Stage primaryStage) throws Exception
	{	
		final int NUM_OF_PANES = 3; // max number of pane instances we create at startup
		ArrayList<BaseAddressBookPane> addressBooksAL = new ArrayList<BaseAddressBookPane>();
		for (int i = 0; i < NUM_OF_PANES; i++) {
			BaseAddressBookPane addressBookPane = AddressBookPaneSingletonGenerator.getInstance();
			if (addressBookPane != null ) {
				addressBooksAL.add(addressBookPane);
				Scene scene = new Scene(addressBookPane.getPane());
				primaryStage = new Stage();
				scene.getStylesheets().add("styles.css");
				primaryStage.setTitle("AddressBook");
				primaryStage.setScene(scene);
				primaryStage.show();
				primaryStage.setAlwaysOnTop(true);
			}
		}
	}
}

// address book caretaker class, following the memento design pattern
class AddressBookCareTaker extends CommandButton { 
	public static ArrayList<AddressBookMemento> addressBookMementoAL = new ArrayList<AddressBookMemento>(); // will keep a list of memento objects with the states
	public static AddressBookOriginator addressBookOriginator = new AddressBookOriginator();
	public static int undoCounter = 0; // counter for how many undo operations we perform, so we know how to limit the redo operation
	
	public AddressBookCareTaker (ButtonlessAddressBookPane addressBookPane, RandomAccessFile raf) {
		super (addressBookPane, raf);
		if (addressBookMementoAL.size() == 0) { // intialize the states at startup of the program
			int addressSize = CommandButton.RECORD_SIZE;
			try {
				raf.seek(addressSize * 2);
				int currentFilePointer = (int) raf.getFilePointer();
				while (currentFilePointer <= raf.length()) {
					raf.seek(currentFilePointer - (addressSize * 2));
					String currentAddress = FixedLengthStringIO.readFixedLengthString(addressSize, raf);
					addressBookOriginator.setState(currentAddress);
					addressBookMementoAL.add(addressBookOriginator.saveStateToAddressBookMemento());
					raf.seek(currentFilePointer + addressSize * 2);
					currentFilePointer = (int) raf.getFilePointer();
				}
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

interface Command
{ public void Execute();
}
class CommandButton extends Button implements Command
{ public final static int NAME_SIZE = 32;
  public final static int STREET_SIZE = 32;
  public final static int CITY_SIZE = 20;
  public final static int STATE_SIZE = 2;
  public final static int ZIP_SIZE = 5;
  public final static int RECORD_SIZE = 
	(NAME_SIZE + STREET_SIZE + CITY_SIZE + STATE_SIZE + ZIP_SIZE);
  protected ButtonlessAddressBookPane p;
  protected RandomAccessFile raf;
  
  
  public CommandButton(ButtonlessAddressBookPane pane, RandomAccessFile r)
  {	super();
	p = pane;
	raf = r;
  }
  public void Execute()
  {
  }
  /** Write a record at the end of the file */
  public void writeAddress()
  {	try
    { raf.seek(raf.length());
	  FixedLengthStringIO.writeFixedLengthString(p.GetName(), 
		 NAME_SIZE, raf);
	  FixedLengthStringIO.writeFixedLengthString(p.GetStreet(),
		 STREET_SIZE, raf);
	  FixedLengthStringIO.writeFixedLengthString(p.GetCity(), 
		 CITY_SIZE, raf);
	  FixedLengthStringIO.writeFixedLengthString(p.GetState(),
		STATE_SIZE, raf);
	  FixedLengthStringIO.writeFixedLengthString(p.GetZip(), 
		ZIP_SIZE, raf);
	 } 
     catch (IOException ex)
     { ex.printStackTrace();
	 }
   }
   /** Read a record at the specified position */
   public void readAddress(long position) throws IOException
   { raf.seek(position);
	 String name = 
	   FixedLengthStringIO.readFixedLengthString(NAME_SIZE, raf);
	 String street = 
	   FixedLengthStringIO.readFixedLengthString(STREET_SIZE, raf);
  	 String city = 
  	   FixedLengthStringIO.readFixedLengthString(CITY_SIZE, raf);
	 String state = 
	   FixedLengthStringIO.readFixedLengthString(STATE_SIZE, raf);
  	 String zip = 
  	   FixedLengthStringIO.readFixedLengthString(ZIP_SIZE, raf);
	 p.SetName(name);
	 p.SetStreet(street);
	 p.SetCity(city);
	 p.SetState(state);
	 p.SetZip(zip);
	}
}

class AddButton extends AddressBookCareTaker
{ public AddButton(ButtonlessAddressBookPane pane, RandomAccessFile r)
  {	super(pane, r);
	this.setText("Add");	
  }
  @Override
  public void Execute()
  {	writeAddress();
  }
}
class NextButton extends AddressBookCareTaker
{ public NextButton(ButtonlessAddressBookPane pane, RandomAccessFile r)
  {	super(pane, r);
	this.setText("Next");
  }
  @Override
  public void Execute()
  {	try
    { long currentPosition = raf.getFilePointer();
	  if (currentPosition < raf.length())
		readAddress(currentPosition);
	} 
    catch (IOException ex)
    { ex.printStackTrace();
	}
  }
}
class PreviousButton extends AddressBookCareTaker
{ public PreviousButton(ButtonlessAddressBookPane pane, RandomAccessFile r)
  {	super(pane, r);
	this.setText("Previous");
  }
  @Override
  public void Execute()
  {	try
    { long currentPosition = raf.getFilePointer();
	  if (currentPosition - 2 * 2 * RECORD_SIZE >= 0)
	 	    readAddress(currentPosition - 2 * 2 * RECORD_SIZE);
	  else;
	  } 
      catch (IOException ex)
      {	ex.printStackTrace();
	  }
	}
  }
class LastButton extends AddressBookCareTaker
{ public LastButton(ButtonlessAddressBookPane pane, RandomAccessFile r)
  {	super(pane, r);
	this.setText("Last");
  }
  @Override
  public void Execute()
  {	try
    { long lastPosition = raf.length();
	  if (lastPosition > 0)
		readAddress(lastPosition - 2 * RECORD_SIZE);
	} 
    catch (IOException ex)
    { ex.printStackTrace();
	}
   }
}

class UndoButton extends AddressBookCareTaker {
	public UndoButton(ButtonlessAddressBookPane pane, RandomAccessFile r) {
		super(pane, r);
		this.setText("Undo");
    }
	
	@Override
  	public void Execute() {
		try {
			 if (raf.length() > (CommandButton.RECORD_SIZE*2) && addressBookMementoAL.size() > 0) {
				 undoCounter++;
				// deleting last addition but still remember the last state in the memento array
				 raf.seek(raf.length() - CommandButton.RECORD_SIZE * 2); 
				 String address = FixedLengthStringIO.readFixedLengthString(CommandButton.RECORD_SIZE, raf);
				 addressBookMementoAL.add(new AddressBookMemento(address));
				 raf.seek(raf.length());
				 new PreviousButton(p, raf).Execute();
			 }
				 else {
					 p.resetTextFields();
				 }
			     if (raf.length() > (CommandButton.RECORD_SIZE*2))
			    	 raf.setLength(raf.length() - CommandButton.RECORD_SIZE * 2);
			 }
	    catch (IOException ex) {
	    	ex.printStackTrace();
		}
   }
}

class RedoButton extends AddressBookCareTaker {
	public RedoButton(ButtonlessAddressBookPane pane, RandomAccessFile r) {
		super(pane, r);
		this.setText("Redo");
    }
	
	@Override
  	public void Execute() {
		try {
			 // recreating the last undo and remember to update state
			 if (raf.length() > 0 && addressBookMementoAL.size() > 0 && undoCounter > 0) {
				 addressBookOriginator.setState(addressBookMementoAL.get(addressBookMementoAL.size() - 1).getState());
				 addressBookMementoAL.remove(addressBookMementoAL.size()-1);
				 raf.seek((raf.length()));
				 FixedLengthStringIO.writeFixedLengthString(addressBookOriginator.getState(), CommandButton.RECORD_SIZE, raf);
				 new LastButton(p, raf).Execute();
				 undoCounter--;
			 }
		}
	    catch (IOException ex) {
	    	ex.printStackTrace();
		}
   }
}

class FirstButton extends AddressBookCareTaker
{ public FirstButton(ButtonlessAddressBookPane pane, RandomAccessFile r)
  {	super(pane, r);
	this.setText("First");
  }

@Override
  public void Execute()
  {	try
    { if (raf.length() > 0) readAddress(0);
	} 
    catch (IOException ex)
    { ex.printStackTrace();
	}
  }
}
