package application;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;



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

public class HW1_Ramy_Gildenberg extends Application
{	public static void main(String[] args)
    { launch(args);
	}
	@Override
	public void start(Stage primaryStage) throws Exception
	{	AddressBookPane pane = new AddressBookPane();
		Scene scene = new Scene(pane);
		scene.getStylesheets().add("styles.css");
		primaryStage.setTitle("AddressBook");
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setAlwaysOnTop(true);
	}
}


class AddressBookPane extends GridPane
{
  private RandomAccessFile raf;
  // Text fields]
  private TextField jtfName = new TextField();
  private TextField jtfStreet = new TextField();
  private TextField jtfCity = new TextField();
  private TextField jtfState = new TextField();
  private TextField jtfZip = new TextField();
  
  // Buttons
  private AddButton jbtAdd;
  private FirstButton jbtFirst;
  private NextButton jbtNext;
  private PreviousButton jbtPrevious;
  private LastButton jbtLast;
  private Sort1Button jbtSort1;
  private Sort2Button jbtSort2;
  private IterButton jbtIter;
  public EventHandler<ActionEvent> ae = 
	new EventHandler<ActionEvent>()
  {	public void handle(ActionEvent arg0)
    { ((Command) arg0.getSource()).Execute();
	}
  };
  
  public AddressBookPane()
  { // Open or create a random access file
 	try
 	{ raf = new RandomAccessFile("address.dat", "rw");
	} 
 	catch (IOException ex)
 	{ System.out.print("Error: " + ex);
	  System.exit(0);
	}
	jtfState.setAlignment(Pos.CENTER_LEFT);
	jtfState.setPrefWidth(90);
	jtfZip.setPrefWidth(60);
	jbtAdd = new AddButton(this, raf);
	jbtFirst = new FirstButton(this, raf);
	jbtNext = new NextButton(this, raf);
	jbtPrevious = new PreviousButton(this, raf);
	jbtLast = new LastButton(this, raf);
	jbtSort1 = new Sort1Button(this, raf);
	jbtSort2 = new Sort2Button(this, raf);
	jbtIter = new IterButton(this, raf);
	Label state = new Label("State");
	Label zp = new Label("Zip");
	Label name = new Label("Name");
	Label street = new Label("Street");
	Label city = new Label("City");		
	// Panel p1 for holding labels Name, Street, and City
	GridPane p1 = new GridPane();
	p1.add(name, 0, 0);
	p1.add(street, 0, 1);
	p1.add(city, 0, 2);
	p1.setAlignment(Pos.CENTER_LEFT);
	p1.setVgap(8);
	p1.setPadding(new Insets(0, 2, 0, 2));
	GridPane.setVgrow(name, Priority.ALWAYS);
	GridPane.setVgrow(street, Priority.ALWAYS);
	GridPane.setVgrow(city, Priority.ALWAYS);
	// City Row
	GridPane adP = new GridPane();
	adP.add(jtfCity, 0, 0);
	adP.add(state, 1, 0);
	adP.add(jtfState, 2, 0);
	adP.add(zp, 3, 0);
	adP.add(jtfZip, 4, 0);
	adP.setAlignment(Pos.CENTER_LEFT);
	GridPane.setHgrow(jtfCity, Priority.ALWAYS);
	GridPane.setVgrow(jtfCity, Priority.ALWAYS);
	GridPane.setVgrow(jtfState, Priority.ALWAYS);
	GridPane.setVgrow(jtfZip, Priority.ALWAYS);
	GridPane.setVgrow(state, Priority.ALWAYS);
	GridPane.setVgrow(zp, Priority.ALWAYS);
	// Panel p4 for holding jtfName, jtfStreet, and p3
	GridPane p4 = new GridPane();
	p4.add(jtfName, 0, 0);
	p4.add(jtfStreet, 0, 1);
	p4.add(adP, 0, 2);
	p4.setVgap(1);
	GridPane.setHgrow(jtfName, Priority.ALWAYS);
	GridPane.setHgrow(jtfStreet, Priority.ALWAYS);
	GridPane.setHgrow(adP, Priority.ALWAYS);
	GridPane.setVgrow(jtfName, Priority.ALWAYS);
	GridPane.setVgrow(jtfStreet, Priority.ALWAYS);
	GridPane.setVgrow(adP, Priority.ALWAYS);
	// Place p1 and p4 into jpAddress
	GridPane jpAddress = new GridPane();
	jpAddress.add(p1, 0, 0);
	jpAddress.add(p4, 1, 0);
	GridPane.setHgrow(p1, Priority.NEVER);
	GridPane.setHgrow(p4, Priority.ALWAYS);
	GridPane.setVgrow(p1, Priority.ALWAYS);
	GridPane.setVgrow(p4, Priority.ALWAYS);
	// Set the panel with line border
	jpAddress.setStyle("-fx-border-color: grey;"
			+ " -fx-border-width: 1;"
			+ " -fx-border-style: solid outside ;");
	// Add buttons to a panel
	FlowPane jpButton = new FlowPane();
	jpButton.setHgap(5);
	jpButton.getChildren().addAll(jbtAdd, jbtFirst, 
		jbtNext, jbtPrevious, jbtLast,jbtSort1,jbtSort2, jbtIter);
	jpButton.setAlignment(Pos.CENTER);
	GridPane.setVgrow(jpButton, Priority.NEVER);
	GridPane.setVgrow(jpAddress, Priority.ALWAYS);
	GridPane.setHgrow(jpButton, Priority.ALWAYS);
	GridPane.setHgrow(jpAddress, Priority.ALWAYS);
	// Add jpAddress and jpButton to the stage
	this.setVgap(5);
	this.add(jpAddress, 0, 0);
	this.add(jpButton, 0, 1);
	jbtAdd.setOnAction(ae);
	jbtFirst.setOnAction(ae);
	jbtNext.setOnAction(ae);
	jbtPrevious.setOnAction(ae);
	jbtLast.setOnAction(ae);
	jbtSort1.setOnAction(ae);
	jbtSort2.setOnAction(ae);
	jbtIter.setOnAction(ae);
	jbtFirst.Execute();
  }
	public void actionHandled(ActionEvent e)
	{ ((Command) e.getSource()).Execute();
	}
	public void SetName(String text)
	{ jtfName.setText(text);
	}
	public void SetStreet(String text)
	{ jtfStreet.setText(text);
	}
	public void SetCity(String text)
	{ jtfCity.setText(text);
	}
	public void SetState(String text)
	{ jtfState.setText(text);
	}
	public void SetZip(String text)
	{ jtfZip.setText(text);
	}
	public String GetName()
	{ return jtfName.getText();
	}
	public String GetStreet()
	{ return jtfStreet.getText();
	}
	public String GetCity()
	{ return jtfCity.getText();
	}
	public String GetState()
	{ return jtfState.getText();
	}
	public String GetZip()
	{ return jtfZip.getText();
	}
}


interface Command
{ public void Execute();
}


class CommandButton extends Button implements Command
{
  public final static int NAME_SIZE = 32;
  public final static int STREET_SIZE = 32;
  public final static int CITY_SIZE = 20;
  public final static int STATE_SIZE =6;
  public final static int ZIP_SIZE = 5;
  public final static int RECORD_SIZE = 
	(NAME_SIZE + STREET_SIZE + CITY_SIZE + STATE_SIZE + ZIP_SIZE);
  protected AddressBookPane p;
  protected RandomAccessFile raf;
  public CommandButton(AddressBookPane pane, RandomAccessFile r)
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
   
   
   public void optimizedBubbleSortBy(Comparator<String> comparator) throws IOException {
	   for (int i = 0; i < raf.length() - RECORD_SIZE*2; i += (RECORD_SIZE * 2)) {
		   for (int j = 0; j < raf.length() - i - (RECORD_SIZE*2); j += RECORD_SIZE * 2) {
			   raf.seek(j);
			   String firstAddressFromRecord = FixedLengthStringIO.readFixedLengthString(RECORD_SIZE, raf);
			   String secondAddressFromRecord = FixedLengthStringIO.readFixedLengthString(RECORD_SIZE, raf);
			   if (comparator.compare(firstAddressFromRecord, secondAddressFromRecord) > 0) 
				   swapInFile(j, firstAddressFromRecord, secondAddressFromRecord);
		   }
	   }
	   new FirstButton(p, raf).Execute();
   }
   
   public void swapInFile (int currentIndexInRaf, String firstAddressFromRecord, String secondAddressFromRecord) throws IOException {
	   raf.seek(currentIndexInRaf);
	   FixedLengthStringIO.writeFixedLengthString(secondAddressFromRecord, RECORD_SIZE, raf);
	   FixedLengthStringIO.writeFixedLengthString(firstAddressFromRecord, RECORD_SIZE, raf);
   }
	   
}

class NameZipComparator implements Comparator<String> {
	private int substringStart;
	private int substringEnd;
	private String type;
	
	
	public NameZipComparator(int substringStart, int substringEnd, String type) {
		this.substringStart = substringStart;
		this.substringEnd = substringEnd;
		this.type = type.toUpperCase();
	}

	public NameZipComparator(int substringStart, int substringEnd) {
		this.substringStart = substringStart;
		this.substringEnd = substringEnd;	
		this.type = null;
	}

	@Override
	public int compare(String firstRecord, String secondRecord) {
		if (type != null && type.equals("ZIPCODE")) {
			Integer firstZipCode = Integer.parseInt(firstRecord.substring(substringStart+1, substringEnd).trim());
			Integer secondZipCode = Integer.parseInt(secondRecord.substring(substringStart+1, substringEnd).trim());
			return firstZipCode.compareTo(secondZipCode);
		} else {
			String firstName = firstRecord.substring(substringStart, substringEnd);
			String secondName = secondRecord.substring(substringStart, substringEnd);
			return firstName.compareTo(secondName);
		}
	}
}



class AddButton extends CommandButton
{ public AddButton(AddressBookPane pane, RandomAccessFile r)
  {	super(pane, r);
	this.setText("Add");
  }
  @Override
  public void Execute()
  {	writeAddress();
  }
}


class NextButton extends CommandButton
{ public NextButton(AddressBookPane pane, RandomAccessFile r)
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


class PreviousButton extends CommandButton
{ public PreviousButton(AddressBookPane pane, RandomAccessFile r)
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


class LastButton extends CommandButton
{ public LastButton(AddressBookPane pane, RandomAccessFile r)
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


class FirstButton extends CommandButton
{ public FirstButton(AddressBookPane pane, RandomAccessFile r)
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
class Sort1Button extends CommandButton {
	public Sort1Button(AddressBookPane pane, RandomAccessFile r) {
		super(pane, r);
		this.setText("Sort1");
	}

	@Override
	public void Execute() {
		try {
			if (raf.length() > 0) 
				optimizedBubbleSortBy(new NameZipComparator(0, CommandButton.NAME_SIZE - 1));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

class RecordListIterator implements ListIterator<String> {
	private int currentIndex = -1;
	private int lastIndex = 0;
	private final int RECORD_SIZE = CommandButton.RECORD_SIZE;
	private RandomAccessFile raf = null;
	
	
	public RecordListIterator (RandomAccessFile raf, int currentIndex) throws IOException {
		this.raf = raf;
		this.currentIndex = currentIndex;
	}
	
	
	@Override
	public void add(String insertString) {
		try {
			ArrayList<String> listToAdd = new ArrayList<String>();
			int currentNext = currentIndex + RECORD_SIZE*2;
			listToAdd.add(insertString);
			if (hasNext()) {
				while (hasNext()) {
					listToAdd.add(next());
				}
				raf.seek(currentIndex);
			}
			for (int i = 0; i < listToAdd.size(); i++) 
				FixedLengthStringIO.writeFixedLengthString(listToAdd.get(i), RECORD_SIZE, raf);
			currentIndex = currentNext;
			lastIndex = -1;
		}
		catch (IOException ex) {	
			ex.printStackTrace();
		}
	}

	@Override
	public boolean hasNext() {
		try {
			if (nextIndex() < raf.length()/(RECORD_SIZE*2))
				return true;
			else return false;
		} catch (IOException e) {
			return false;
		}
	}

	@Override
	public boolean hasPrevious() {
		if (previousIndex() > 0)
			return true;
		else return false;
	}

	@Override
	public String next() {
		try {
			if (hasNext()) {
				lastIndex = currentIndex;
				raf.seek(currentIndex);
				String currentRecordString = FixedLengthStringIO.readFixedLengthString(RECORD_SIZE, raf);
				currentIndex = (int) raf.getFilePointer();
				return currentRecordString;
			}
			return null;
		}
		catch (IOException ex) {
			return null;
		}
	}

	@Override
	public int nextIndex() {
		int nextIndex = currentIndex / (RECORD_SIZE*2);
		return nextIndex;
	}

	@Override
	public String previous() {
		try {
			if (!hasPrevious())
				return null;
			else {
				int previousIndexFromCurrent = currentIndex - RECORD_SIZE*2;
				raf.seek(previousIndexFromCurrent);
				String currentRecordString = FixedLengthStringIO.readFixedLengthString(RECORD_SIZE, raf);
				currentIndex = previousIndexFromCurrent;
				return currentRecordString;
			}
		}
		catch (IOException ex) {
			return null;
		}
	}

	@Override
	public int previousIndex() {
		int previousIndex = (currentIndex / (RECORD_SIZE*2)) - 2;
		return previousIndex;
	}

	@Override
	public void remove() {
		try {
			if (raf.length() > 0 && lastIndex != -1) {
				if (hasNext())
				{
					ArrayList<String> list = new ArrayList<String>();
					while (hasNext())
						list.add(next());
					raf.seek(currentIndex - RECORD_SIZE*2);
					for (int i = 0; i < list.size(); i++) 
						FixedLengthStringIO.writeFixedLengthString(list.get(i), RECORD_SIZE, raf);
				}
				currentIndex = (int) raf.getFilePointer();
				lastIndex = -1;
				raf.setLength(raf.length() - RECORD_SIZE*2);
			}
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void set(String stringToSet) {
		try {
			if (lastIndex != -1) {
				raf.seek(currentIndex - RECORD_SIZE*2);
				FixedLengthStringIO.writeFixedLengthString(stringToSet, RECORD_SIZE, raf);
			}
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public void deleteAllRecords () throws IOException {
		while (hasNext()) {
			currentIndex = 0;
			raf.seek(0);
			next();
			remove();
		}
		raf.setLength(0);
		lastIndex = 0;
		currentIndex = 0;
	}
	
}




class Sort2Button extends CommandButton {
	public Sort2Button(AddressBookPane pane, RandomAccessFile r) {
		super(pane, r);
		this.setText("Sort2");
	}

	@Override
	public void Execute() {
		try {
			if (raf.length() > 0) {}
				optimizedBubbleSortBy(new NameZipComparator(CommandButton.RECORD_SIZE - CommandButton.ZIP_SIZE - 1,
						CommandButton.RECORD_SIZE - 1, "ZIPCODE"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class StreetNameComparator implements Comparator<String> {
	@Override
	public int compare(String firstStreet, String secondStreet) {
		NameZipComparator comp = new NameZipComparator(CommandButton.NAME_SIZE-1, CommandButton.STREET_SIZE + CommandButton.NAME_SIZE-1);
		int valueFromMyComparator = comp.compare(firstStreet, secondStreet);
		if (valueFromMyComparator != 0) 
			return valueFromMyComparator;
		else return firstStreet.compareTo(secondStreet);
	}
}


class IterButton extends CommandButton {
	private boolean firstRun = true;
	private Map<String, String> iteratedHashMap = null;
	private AddressBookPane pane;
	
	public IterButton(AddressBookPane pane, RandomAccessFile r) {
		super(pane, r);
		this.pane = pane;
		this.setText("Iter");
	}
	
	@Override
	public void Execute () {
		try {
			if (raf.length() > 0) {
				RecordListIterator lit = new RecordListIterator(raf, 0);
				if (firstRun == true) {
					this.iteratedHashMap = getMapIterator(lit);
					firstRun = false;
				}
				else {
					iteratedHashMapToTreeSet (lit, iteratedHashMap);
				}
			}
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	private void iteratedHashMapToTreeSet(RecordListIterator lit, Map<String, String> iteratedHashMap) throws IOException {
		TreeSet<String> recordTreeSet = new TreeSet<String>(new StreetNameComparator());
		recordTreeSet.addAll(iteratedHashMap.values());
		lit.deleteAllRecords();
		for (String record : recordTreeSet)
			lit.add(record);
	}

	private Map<String, String> getMapIterator(RecordListIterator lit) throws IOException {
		HashMap<String, String> iterateHM = new HashMap<String, String>();
		while (lit.hasNext()) {
			String currentRecord = lit.next();
			iterateHM.put(currentRecord.substring(0, RECORD_SIZE - ZIP_SIZE), currentRecord);
		}
		lit.deleteAllRecords();
		for (String record : iterateHM.values()) {
			lit.add(record);
		}
		return iterateHM;
	}
	
	
	
	

}

