package graphics;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

	private static int xCount = 0, cCount = 0;
	private static Label xLabel, cLabel, kpsLabel;

	public static void main(String[] args) {
		GlobalScreen.setEventDispatcher(new JavaFxDispatchService());
		try {
			Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
			logger.setLevel(Level.OFF);
			GlobalScreen.registerNativeHook();
		} catch (NativeHookException ex) {
			System.err.println("There was a problem registering the native hook.");
			System.err.println(ex.getMessage());

			System.exit(1);
		}
		GlobalScreen.addNativeKeyListener(new GlobalKeyListener());
		Application.launch();
	}

	public void start(Stage stage) throws Exception {
		VBox root = new VBox();
		HBox box1 = new HBox();
		HBox box2 = new HBox();
		HBox box3 = new HBox();
		
		Label l1 = new Label("X : ");
		Label l2 = new Label("Y : ");
		Label l3 = new Label("KPS : ");
		xLabel = new Label("0");
		cLabel = new Label("0");
		kpsLabel = new Label("0");
		
		box1.getChildren().addAll(l1, xLabel);
		box2.getChildren().addAll(l2, cLabel);
		box3.getChildren().addAll(l3, kpsLabel);
		root.getChildren().addAll(box1,box2,box3);

		Scene scene = new Scene(root, 300, 100);
		stage.setScene(scene);
		stage.setTitle("Hello javafx");
		stage.show();
	}

	public static void press(NativeKeyEvent e) {
		Platform.runLater(() -> {
			try {
				if(e.getKeyCode() == NativeKeyEvent.VC_X) {
					xCount++;
					xLabel.setText("" + xCount);
				}
				
				if(e.getKeyCode() == NativeKeyEvent.VC_C) {
					cCount++;
					cLabel.setText("" + cCount);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});
	}

}
