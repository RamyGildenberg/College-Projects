package application;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class ButtonlessAddressBookPane extends GridPane implements BaseAddressBookPane {
	private RandomAccessFile raf;
	// Text fields
	private TextField jtfName = new TextField();
    private TextField jtfStreet = new TextField();
	private TextField jtfCity = new TextField();
	private TextField jtfState = new TextField();
	private TextField jtfZip = new TextField();
	private GridPane jpAddress; 
	private FlowPane jpButton; 
	private final static int MAX_ADDRESSBOOK_INSTANCES = 3; // max pane instances allowed in the program
	private static int counter = 0; // counter for current num of pane instances
	
	
	public EventHandler<ActionEvent> ae = 
			new EventHandler<ActionEvent>()
		  {	public void handle(ActionEvent arg0)
		    { ((Command) arg0.getSource()).Execute();
			}
		  };
	
	private ButtonlessAddressBookPane () {
		// Open or create a random access file
	 	try
	 	{ raf = new RandomAccessFile("address.dat", "rw");
		} 
	 	catch (IOException ex)
	 	{ System.out.print("Error: " + ex);
		  System.exit(0);
		}
		jtfState.setAlignment(Pos.CENTER_LEFT);
		jtfState.setPrefWidth(25);
		jtfZip.setPrefWidth(60);
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
		jpAddress = new GridPane();
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
		jpButton = new FlowPane();
		jpButton.setHgap(5);
		jpButton.setAlignment(Pos.CENTER);
		GridPane.setVgrow(jpButton, Priority.NEVER);
		GridPane.setVgrow(jpAddress, Priority.ALWAYS);
		GridPane.setHgrow(jpButton, Priority.ALWAYS);
		GridPane.setHgrow(jpAddress, Priority.ALWAYS);
		// Add jpAddress and jpButton to the stage
		this.setVgap(5);
		this.add(jpAddress, 0, 0); 
		this.add(jpButton, 0, 1);
		
	}
	

	public void resetTextFields() { 
		jtfName.setText("");
		jtfCity.setText("");
		jtfStreet.setText("");
		jtfZip.setText("");
		jtfState.setText("");
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
	
	// only return a new instance if we didn't exceed the num of max instances allowed (in our case, 3)
	public static ButtonlessAddressBookPane getInstance() {
		if (counter < MAX_ADDRESSBOOK_INSTANCES) {
			counter++;
			return new ButtonlessAddressBookPane();
		}
		return null;
	}
	
	
	// initializing all the buttons in the pane and setting event listeners
	@Override
	public void buttonsInitialize(ArrayList<CommandButton> buttonsAL) {
		for (int i = 0; i < buttonsAL.size(); i++) {
			CommandButton currentButton = buttonsAL.get(i);
			currentButton.setOnAction(ae);
			jpButton.getChildren().add(currentButton);
		}
	}

	@Override
	public ButtonlessAddressBookPane getPane() {
		return this;
	}

	@Override
	public RandomAccessFile getRandomAccessFile() {
		return raf;
	}
	
}
