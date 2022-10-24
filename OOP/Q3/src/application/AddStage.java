package application;

import java.text.NumberFormat;

import javax.swing.event.ChangeListener;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class AddStage  { 
	//ObservableList<String> instrumentsComboBox=FXCollections.observableArrayList("Guitar", "Bass",  "Flute","Saxophone");
	ObservableList<String> guitarTypeBox=FXCollections.observableArrayList("Classic", "Acoustic",  "Electric");
	ObservableList<String> fluteTypeBox=FXCollections.observableArrayList("Flute", "Recorder",  "Bass");
	ObservableList<String> fluteMaterialBox=FXCollections.observableArrayList("Wood", "Metal",  "Plastic");
	private ComboBox<String> instrumentsComboBox;
	final ComboBox<String> guitarTypeComboBox;
	final ComboBox<String> fluteTypeComboBox;
	final ComboBox<String> materialComboBox;
	private final double paddingSize = 10;
	private BorderPane addSceneBorderPane;
	private GridPane addSceneGridPane;
	private Scene addScene;
	private ComboBox<String> addComboBox;
	private Stage addInstrumentStage;
	private Scene mainScene;
	private TextField brandTextField;
	private TextField priceTextField;
	private TextField numOfStringsTextField;
	private CheckBox fretlessCheckBox=new CheckBox();
	private Button addButton;
	
	protected TextField getBrandTextField()
	{
		return this.brandTextField;
	}
	public TextField getPriceTextField()
	{
		return priceTextField;
	}
	protected TextField getNumOfStringsTextField()
	{
		return this.numOfStringsTextField;
	}
	protected CheckBox getFretless()
	{
		return this.fretlessCheckBox;
	}
	public AddStage() {
		addSceneBorderPane = new BorderPane();
		addSceneGridPane = new GridPane();
		addComboBox = new ComboBox<String>();
		addInstrumentStage = new Stage();
		instrumentsComboBox=new ComboBox<String>();
		guitarTypeComboBox=new ComboBox<String>();
		fluteTypeComboBox=new ComboBox<String>();
		materialComboBox=new ComboBox<String>();
		mainScene = new Scene(addSceneBorderPane, 400, 300);
		addButton = new Button("Add");
		instrumentsComboBox.getItems().addAll("Guitar", "Bass",  "Flute","Saxophone");
		instrumentsComboBox.setPromptText("Choose Instrument Type Here: ");
		priceTextField = new TextField();
		numOfStringsTextField=new TextField();
		brandTextField=new TextField();
		createInstrumentStage();
	}
	
	public ComboBox getInstrumentsComboBox() {
		return instrumentsComboBox;
	}
	

	public void createInstrumentStage() {
		if (setCenterPart() == null) {
			mainScene = new Scene(createInstrumentChoosingStage(), 400, 300);
		}
		else {
			addSceneBorderPane.setTop(setTopPart());
			addSceneBorderPane.setCenter(setCenterPart());
			addSceneBorderPane.setBottom(setBottomPart());
		}
	}
	private HBox setBottomPart() {
		HBox bottomPart=new HBox();
		bottomPart.setPadding(new Insets(10));
		bottomPart.setAlignment(Pos.CENTER);
		bottomPart.setSpacing(20); 
		bottomPart.getChildren().add(addButton);
		return bottomPart;
		
	}

	private HBox setTopPart()
	{
	  HBox topPart = new HBox(); 
	  topPart.setPadding(new Insets(10));
	  topPart.getChildren().addAll(instrumentsComboBox);
      topPart.setSpacing(20); 
      topPart.setAlignment(Pos.CENTER);
      return topPart;
	}
	
	public void comboBoxChanged () {
		addSceneBorderPane = new BorderPane();
		addSceneBorderPane.setBottom(setBottomPart());
		addSceneBorderPane.setCenter(setCenterPart());
		addSceneBorderPane.setTop(setTopPart());
		mainScene = new Scene(addSceneBorderPane, 400, 300);
	}
	public BorderPane createInstrumentChoosingStage() {
		VBox instrumentComboBoxOnly=new VBox();
		instrumentComboBoxOnly.getChildren().add(instrumentsComboBox);
		instrumentComboBoxOnly.setAlignment(Pos.CENTER);
		BorderPane defaultPane=new BorderPane();
		defaultPane.setCenter(instrumentComboBoxOnly);
		return defaultPane;
	}
	
	public GridPane setCenterPart()
	{
		GridPane CenterPart=new GridPane();
		CenterPart.setHgap(25);
		CenterPart.setVgap(10);
		final Text brandText = new Text("Brand:");
		final Text priceText = new Text("Price:");
		
		
		brandTextField.setPromptText("Ex: Gibson");
		brandTextField.setFocusTraversable(false);
		brandTextField.setPrefWidth(200);
		
		priceTextField.setPromptText("Ex: 7500");
		priceTextField.setFocusTraversable(false);
		priceTextField.setPrefWidth(200);
		String instrumentKind = instrumentsComboBox.getSelectionModel().getSelectedItem();
		if (instrumentKind == null) 
		{
			return null;
		}
		else if (instrumentKind.equals("Guitar")) {
			
			final Text numOfStringsText = new Text("Number of Strings:");
			final Text typeText = new Text("Type:");
			numOfStringsTextField.setPromptText("Ex: 6");
			numOfStringsTextField.setFocusTraversable(false);
			numOfStringsTextField.setPrefWidth(200); 
			guitarTypeComboBox.setPromptText("Type");
			guitarTypeComboBox.getItems().addAll("Classic", "Acoustic",  "Electric");
			
			CenterPart.add(numOfStringsText, 1, 2);
			CenterPart.add(numOfStringsTextField, 2, 2);
			
			CenterPart.add(typeText, 1, 3);
			CenterPart.add(guitarTypeComboBox, 2, 3);
		}
		else if (instrumentKind.equals("Saxophone")) {
			priceTextField.setPromptText("Ex: 7500");
			brandTextField.setPromptText("Ex: Mercury");
		}
		else if (instrumentKind.equals("Bass")) {
			brandTextField.setPromptText("Ex: Fender Jazz");
			priceTextField.setPromptText("Ex: 7500");
			numOfStringsTextField.setPromptText("Ex: 4");
			final Text numOfStringsText = new Text("Number of Strings:");
			final Text fretlessText = new Text("Fretless:");
			
			CenterPart.add(numOfStringsText, 1, 2);
			CenterPart.add(numOfStringsTextField, 2, 2);
			
			CenterPart.add(fretlessText, 1, 3);
			CenterPart.add(fretlessCheckBox, 2, 3);
			
		}
		else if (instrumentKind.equals("Flute")) {
			brandTextField.setPromptText("Ex: Levit");
			priceTextField.setPromptText("Ex: 300");
			final Text  fluteTypeText = new Text("Flute Type:");
			final Text materialText = new Text("Material");
			fluteTypeComboBox.setPromptText("Type");
			fluteTypeComboBox.getItems().addAll("Flute", "Recorder",  "Bass");
			materialComboBox.setPromptText("Material");
			materialComboBox.getItems().addAll("Wood", "Metal",  "Plastic");
			
			CenterPart.add(materialText, 1, 2);
			CenterPart.add(materialComboBox, 2, 2);
			
			CenterPart.add(fluteTypeText, 1, 3);
			CenterPart.add(fluteTypeComboBox, 2, 3);
			
			
		}
		CenterPart.add(brandText, 1, 0);
		CenterPart.add(brandTextField, 2, 0);
		
		CenterPart.add(priceText, 1, 1);
		CenterPart.add(priceTextField, 2, 1);
		
		CenterPart.setAlignment(Pos.CENTER);
		return CenterPart;
	}
	
	protected Scene getMainScene() {
  		return mainScene;
  	}
	
	public String getBrand()
	{
		
		return brandTextField.getText();
	}
	public ComboBox<String> getGuitarTypeComboBox() {
		
		return guitarTypeComboBox;
	}
	public ComboBox<String> getFluteTypeComboBox() {
			
			return fluteTypeComboBox;
	}
	public ComboBox<String> getMaterialTypeComboBox() {
			
			return materialComboBox;
	}
	public Button getAddButton() {
		
		return addButton;
	}
	
	
}	
	


