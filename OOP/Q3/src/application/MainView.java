package application;
import java.util.ArrayList;

import javafx.application.Application; 
import javafx.collections.ObservableList; 
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class MainView extends BorderPane {   
   
	private Scene mainScene = new Scene(getMainBorderPane(), 800, 300);
	private ArrayList<MusicalInstrument> instruments;
	private final int paddingSize=20;
	private final int spaceSize=20;
	private int currentIndex;
	private TextField typeTextField;
	private TextField brandTextField;
	private TextField priceTextField;
	public MainView(ArrayList<MusicalInstrument> instruments) {
		this.instruments = instruments;
		currentIndex = 0;
		typeTextField = new TextField();
		brandTextField = new TextField();
		priceTextField = new TextField();
		setScene();
	}
	
	private void setScene() {
		setTop(setTopPart());
		setRight(setRightPart());
		setLeft(setLeftPart());
		setBottom(setBottomPart());
		setCenter(setCenterPart());
	}
	
	private HBox setTopPart()
	{
	  HBox topPart = new HBox(); 
	  topPart.setPadding(new Insets(paddingSize));
      final TextField searchTextField = new TextField();  
      HBox.setHgrow(searchTextField, Priority.ALWAYS);
      searchTextField.setPromptText("Search...");
      searchTextField.setFocusTraversable(false);
      searchTextField.setPrefWidth(700); 
      Button playButton = new Button("Go!");  
      topPart.getChildren().addAll(searchTextField,playButton);
      topPart.setSpacing(spaceSize); 
      topPart.setAlignment(Pos.CENTER);
      mainScene.setOnKeyPressed(e -> {
    	    if (e.getCode() == KeyCode.ENTER) {
    	    	String toSearch = searchTextField.getText();
    	    	  for (int i = 0; i < instruments.size(); i++) {
    	    		  String mainInstrumentDescription = instruments.get(i).toString();
    	    		  if (mainInstrumentDescription.contains(toSearch)) {
    	        		  currentIndex = i;
    	        		  break;
    	        	  }
    	    	  }
    	    	  setCenter(setCenterPart());
    	    }
    	});
      playButton.setOnAction((event) -> {
    	  String toSearch = searchTextField.getText();
    	  for (int i = 0; i < instruments.size(); i++) {
    		  String mainInstrumentDescription = instruments.get(i).toString();
    		  if (mainInstrumentDescription.contains(toSearch)) {
        		  currentIndex = i;
        		  break;
        	  }
    	  }
    	  setCenter(setCenterPart());
      });
      return topPart;
      
	}

	private GridPane setCenterPart()
	{
		GridPane CenterPart=new GridPane();
		CenterPart.setHgap(25);
		CenterPart.setVgap(10);
		final Text typeText = new Text("Type:");
		final Text brandText = new Text("Brand:");
		final Text priceText = new Text("Price:");
		
		if (instruments.size() > 0) {
			typeTextField.setPromptText("No Items");
			typeTextField.setText(instruments.get(currentIndex).getClass().getSimpleName());
			typeTextField.setFocusTraversable(false);
			typeTextField.setPrefWidth(200); 
			
			brandTextField.setPromptText("No Items");
			brandTextField.setText(instruments.get(currentIndex).getBrand());
			brandTextField.setFocusTraversable(false);
			brandTextField.setPrefWidth(200);
			
			priceTextField.setPromptText("No Items");
			priceTextField.setText(instruments.get(currentIndex).getPrice().toString());
			priceTextField.setFocusTraversable(false);
			priceTextField.setPrefWidth(200);
		}
		else {
			typeTextField.setText("");
			typeTextField.setPromptText("No Items");
			typeTextField.setFocusTraversable(false);
			typeTextField.setPrefWidth(200); 
			
			brandTextField.setText("");
			brandTextField.setPromptText("No Items");
			brandTextField.setFocusTraversable(false);
			brandTextField.setPrefWidth(200);
			
			priceTextField.setText("");
			priceTextField.setPromptText("No Items");
			priceTextField.setFocusTraversable(false);
			priceTextField.setPrefWidth(200);
		}
		
		CenterPart.add(typeText, 1, 0);
		CenterPart.add(typeTextField, 2, 0);
		
		CenterPart.add(brandText, 1, 1);
		CenterPart.add(brandTextField, 2, 1);
		
		CenterPart.add(priceText, 1, 2);
		CenterPart.add(priceTextField, 2, 2);
		
		
		CenterPart.setAlignment(Pos.CENTER);
		return CenterPart;
	}
	
	private VBox setLeftPart()
	{
		Button leftArrow=new Button("<");
		VBox leftPart=new VBox();
		leftPart.getChildren().add(leftArrow);
		leftPart.setPadding(new Insets(paddingSize));
		leftPart.setAlignment(Pos.CENTER);
		leftArrow.setOnAction((event) -> {
			currentIndex--;
			if (currentIndex < 0)
				currentIndex = instruments.size()-1;
			setCenter(setCenterPart());
		});
		return leftPart;
	}
	
	private VBox setRightPart()
	{
		Button rightArrow=new Button(">");
		VBox rightPart=new VBox();
		rightPart.getChildren().add(rightArrow);
		rightPart.setPadding(new Insets(paddingSize));
		rightPart.setAlignment(Pos.CENTER);
		rightArrow.setOnAction((event) -> {
			currentIndex++;
			if (currentIndex == instruments.size())
				currentIndex = 0;
			setCenter(setCenterPart());
		});
		return rightPart;
	}
	
	private HBox setBottomPart()
	{
		final Button addButton=new Button("Add");
		final Button deleteButton=new Button("Delete");	
		final Button clearButton=new Button("Clear");
		HBox bottomsTopPart=new HBox();
		bottomsTopPart.getChildren().addAll(addButton,deleteButton,clearButton);
		bottomsTopPart.setSpacing(spaceSize);
		bottomsTopPart.setPadding(new Insets(spaceSize));
		bottomsTopPart.setAlignment(Pos.CENTER);
		AddStage AddSceneUI = new AddStage();
		deleteButton.setOnAction((event) -> {
			instruments.remove(currentIndex);
			setCenter(setCenterPart());
		});
		clearButton.setOnAction((event) -> {
			instruments.removeAll(instruments);
			currentIndex = 0;
			setCenter(setCenterPart());
			
		});
		addButton.setOnAction((event)-> {
			Stage newStage = new Stage();
			newStage.setScene(AddSceneUI.getMainScene());
			ComboBox<String> comboBox = AddSceneUI.getInstrumentsComboBox();
			comboBox.setOnAction((event1) -> {
				AddSceneUI.comboBoxChanged();
				newStage.setScene(AddSceneUI.getMainScene());
			});
			newStage.show();
			
		
		});
		
		Button AddSceneAddButton = AddSceneUI.getAddButton();
		TextField price = AddSceneUI.getPriceTextField();
		AddSceneAddButton.setOnAction((event2) -> {
			if(AddSceneUI.getInstrumentsComboBox().getSelectionModel().getSelectedItem().equals("Bass"))
			{
				if(!AddSceneUI.getPriceTextField().getText().matches("-?\\d+(\\.\\d+)?"))
				{
					System.out.println("bla");
					Alert wrongInput=new Alert(AlertType.ERROR);
					wrongInput.setTitle("Error");
					wrongInput.setContentText("Price must be a number! ");
					wrongInput.setHeaderText("Price Error!");
					wrongInput.showAndWait();
				}
				else if(Integer.parseInt(AddSceneUI.getPriceTextField().getText())<0)
				{
					Alert wrongPrice=new Alert(AlertType.ERROR);
					wrongPrice.setTitle("Error");
					wrongPrice.setContentText("Price must be a positive number! ");
					wrongPrice.setHeaderText("Price Error!");
					wrongPrice.showAndWait();
				}
				else if(Integer.parseInt(AddSceneUI.getNumOfStringsTextField().getText())!=6 && Integer.parseInt(AddSceneUI.getNumOfStringsTextField().getText())!=4)
				{
					Alert wrongNumOfStrings=new Alert(AlertType.ERROR);
					wrongNumOfStrings.setTitle("Error");
					wrongNumOfStrings.setContentText("Amount of strings must be  4 or 6! ");
					wrongNumOfStrings.setHeaderText("Number of strings Error!");
					wrongNumOfStrings.showAndWait();
				}
				else {
				Bass bass=new Bass(AddSceneUI.getBrand(),Integer.parseInt(AddSceneUI.getPriceTextField().getText()),
						Integer.parseInt(AddSceneUI.getNumOfStringsTextField().getText()),AddSceneUI.getFretless().isSelected());
				instruments.add(bass);
				}
			}
			if(AddSceneUI.getInstrumentsComboBox().getSelectionModel().getSelectedItem().equals("Saxophone"))
			{
				if(!AddSceneUI.getPriceTextField().getText().matches("-?\\d+(\\.\\d+)?"))
				{
					Alert wrongInput=new Alert(AlertType.ERROR);
					wrongInput.setTitle("Error");
					wrongInput.setContentText("Price must be a number! ");
					wrongInput.setHeaderText("Price Error!");
					wrongInput.showAndWait();
				}
				else if(Integer.parseInt(AddSceneUI.getPriceTextField().getText())<0)
				{
					Alert wrongPrice=new Alert(AlertType.ERROR);
					wrongPrice.setTitle("Error");
					wrongPrice.setContentText("Price must be a positive number! ");
					wrongPrice.setHeaderText("Price Error!");
					wrongPrice.showAndWait();
				}
				else {
				Saxophone saxophone=new Saxophone(AddSceneUI.getBrand(),Integer.parseInt(AddSceneUI.getPriceTextField().getText()));
				instruments.add(saxophone);
				}
				
			}
			if(AddSceneUI.getInstrumentsComboBox().getSelectionModel().getSelectedItem().equals("Guitar"))
			{
				if(AddSceneUI.getPriceTextField().getText().matches("-?\\d+(\\.\\d+)?") && Integer.parseInt(AddSceneUI.getPriceTextField().getText())<0)
				{
					Alert wrongPrice=new Alert(AlertType.ERROR);
					wrongPrice.setTitle("Error");
					wrongPrice.setContentText("Price must be a positive number! ");
					wrongPrice.setHeaderText("Price Error!");
					wrongPrice.showAndWait();
				}
				else if(!AddSceneUI.getPriceTextField().getText().matches("-?\\d+(\\.\\d+)?")) 
				{
					Alert wrongInput=new Alert(AlertType.ERROR);
					wrongInput.setTitle("Error");
					wrongInput.setContentText("Price must be a number! ");
					wrongInput.setHeaderText("Price Error!");
					wrongInput.showAndWait();
				} 
				else if(AddSceneUI.getNumOfStringsTextField().getText().equals(""))
				{
					Alert wrongNumOfStrings=new Alert(AlertType.ERROR);
					wrongNumOfStrings.setTitle("Error");
					wrongNumOfStrings.setContentText("Amount of strings must be  6 or 8! ");
					wrongNumOfStrings.setHeaderText("Number of strings Error!");
					wrongNumOfStrings.showAndWait();
				}
				else if(Integer.parseInt(AddSceneUI.getNumOfStringsTextField().getText())!=8 && Integer.parseInt(AddSceneUI.getNumOfStringsTextField().getText())!=6)
				{
					Alert wrongNumOfStrings=new Alert(AlertType.ERROR);
					wrongNumOfStrings.setTitle("Error");
					wrongNumOfStrings.setContentText("Amount of strings must be  6 or 8! ");
					wrongNumOfStrings.setHeaderText("Number of strings Error!");
					wrongNumOfStrings.showAndWait();
					
				}
				else if (AddSceneUI.getGuitarTypeComboBox().getSelectionModel().getSelectedItem() == null) {
					Alert wrongType=new Alert(AlertType.ERROR);
					wrongType.setTitle("Error");
					wrongType.setContentText("Must choose a type");
					wrongType.setHeaderText("Type Error!");
					wrongType.showAndWait();
				}
				else {Guitar guitar=new Guitar(AddSceneUI.getBrand(),Integer.parseInt(AddSceneUI.getPriceTextField().getText()),
						Integer.parseInt(AddSceneUI.getNumOfStringsTextField().getText()),AddSceneUI.getGuitarTypeComboBox().getSelectionModel().getSelectedItem());
				System.out.println(AddSceneUI.getBrand());
				System.out.println(Integer.parseInt(AddSceneUI.getPriceTextField().getText()));
				System.out.println(Integer.parseInt(AddSceneUI.getNumOfStringsTextField().getText()));
				System.out.println(AddSceneUI.getGuitarTypeComboBox().getSelectionModel().getSelectedItem());
				
				instruments.add(guitar);
				}
			}
			if(AddSceneUI.getInstrumentsComboBox().getSelectionModel().getSelectedItem().equals("Flute"))
			{
				if(!AddSceneUI.getPriceTextField().getText().matches("-?\\d+(\\.\\d+)?"))
				{
					Alert wrongInput=new Alert(AlertType.ERROR);
					wrongInput.setTitle("Error");
					wrongInput.setContentText("Price must be a number! ");
					wrongInput.setHeaderText("Price Error!");
					wrongInput.showAndWait();
				}
				else if(Integer.parseInt(AddSceneUI.getPriceTextField().getText())<0)
				{
					Alert wrongPrice=new Alert(AlertType.ERROR);
					wrongPrice.setTitle("Error");
					wrongPrice.setContentText("Price must be a positive number! ");
					wrongPrice.setHeaderText("Price Error!");
					wrongPrice.showAndWait();
				}
				else if (AddSceneUI.getFluteTypeComboBox().getSelectionModel().getSelectedItem() == null || AddSceneUI.getMaterialTypeComboBox().getSelectionModel().getSelectedItem() == null) {
					Alert wrongType=new Alert(AlertType.ERROR);
					wrongType.setTitle("Error");
					wrongType.setContentText("Must choose a type");
					wrongType.setHeaderText("Type Error!");
					wrongType.showAndWait();
				}
				else {Flute flute=new Flute(AddSceneUI.getBrand() , Integer.parseInt(AddSceneUI.getPriceTextField().getText()) ,
						AddSceneUI.getMaterialTypeComboBox().getSelectionModel().getSelectedItem() , AddSceneUI.getFluteTypeComboBox().getSelectionModel().getSelectedItem());
				instruments.add(flute);
				}
				
			}
			currentIndex = instruments.size() - 1;
			setCenter(setCenterPart());
			
			
		});
		
		
		return bottomsTopPart;
	}
	
	
	
      
      
      protected BorderPane getMainBorderPane() {
		
		return this;
	}
      protected Scene getMainScene() {
  		return mainScene;
  	}
      
    
      


   
}