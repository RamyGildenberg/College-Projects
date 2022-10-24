

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Scene;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.application.Platform;

public class ClockStartStopJavaFx extends Application
{ @Override // Override the start method in the Application class
  public void start(Stage primaryStage)
  {   ClockPane clock = new ClockPane(); // Create a clock
      Tick ticker=new Tick(clock);
      Thread tickerThread=new Thread(ticker);
      tickerThread.start();
      HBox hBox = new HBox(5);
      Button btStop = new Button("Stop");
      Button btStart = new Button("Start");
      hBox.getChildren().addAll(btStop, btStart);
      hBox.setAlignment(Pos.CENTER);
      BorderPane pane = new BorderPane();
      pane.setCenter(clock);
      pane.setBottom(hBox);
      // Create a scene and place it in the stage
      Scene scene = new Scene(pane, 250, 300);
      primaryStage.setTitle("ClockStarsStop"); // Set the stage title
      primaryStage.setScene(scene); // Place the scene in the stage
      primaryStage.show(); // Display the stage
      primaryStage.setAlwaysOnTop(true);
      primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent event) {
				Platform.exit();
				System.exit(0);
			}
	  });
      btStart.setOnAction(e -> ticker.play());
      btStop.setOnAction(e -> ticker.pause());
      clock.widthProperty().addListener(ov ->
      {    clock.setW(pane.getWidth());
      });
      clock.heightProperty().addListener(ov ->
      {   clock.setH(pane.getHeight());
      });
    }
    public static void main(String[] args)
    {   launch(args);
    }
  
  
}
