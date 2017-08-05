package computergraphics.homework2;

import computergraphics.homework2.shapes.Pyramid;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

public class Main extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        Rectangle field=new Rectangle(500, 500, Color.GREEN);
        field.setTranslateX(-250);
        field.setTranslateY(-250);
        Cylinder zAxis=new Cylinder(10, 100);
        zAxis.setRotationAxis(Rotate.X_AXIS);
        zAxis.setRotate(90);
        zAxis.setTranslateZ(50);
        PhongMaterial mat=new PhongMaterial(Color.GREY);
        zAxis.setMaterial(mat);
        Pyramid pyramid=new Pyramid(50, 50);
        PhongMaterial pyramidMaterial=new PhongMaterial(Color.YELLOW);
        pyramid.setMaterial(pyramidMaterial);
        Group mainGroup=new Group();
        mainGroup.getChildren().addAll(field, zAxis, pyramid);
        PerspectiveCamera mainCamera=new PerspectiveCamera(true);
        mainCamera.setNearClip(0.1);
        mainCamera.setFarClip(1000);
        /*mainCamera.setRotationAxis(Rotate.X_AXIS);
        mainCamera.setRotate(180);
        mainCamera.setTranslateZ(+1000);*/
        Rotate rot1=new Rotate(-135, Rotate.X_AXIS);
        Rotate rot2=new Rotate(0, Rotate.Y_AXIS);
        Translate t1=new Translate(0, 0, -700);
        mainCamera.getTransforms().addAll(rot1,rot2,t1);
        PointLight pointLight=new PointLight();
        pointLight.setTranslateX(75);
        //mainGroup.getChildren().add(pointLight);
        Scene scene=new Scene(mainGroup, 640, 480, true);
        scene.setCamera(mainCamera);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Traffic Simulator");
        primaryStage.show();
    }
    
    public static void main(String[] args){
        launch(args);
    }
}
