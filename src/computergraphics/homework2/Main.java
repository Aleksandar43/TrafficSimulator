package computergraphics.homework2;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Main extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        Rectangle field=new Rectangle(320, 240, Color.GREEN);
        Group mainGroup=new Group();
        mainGroup.getChildren().add(field);
        PerspectiveCamera mainCamera=new PerspectiveCamera(true);
        Scene scene=new Scene(mainGroup, 640, 480);
        scene.setCamera(mainCamera);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Traffic Simulator");
        primaryStage.show();
    }
    
    public static void main(String[] args){
        launch(args);
    }
}
