package computergraphics.homework2;

import computergraphics.homework2.shapes.Pyramid;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

public class Main extends Application{
    PerspectiveCamera previewCamera,mainCamera;
    private Translate translateMainCamera;
    private Rotate xRotateMainCamera;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        int FIELD_LENGTH=1000;
        Rectangle field=new Rectangle(FIELD_LENGTH, FIELD_LENGTH, Color.GREEN);
        field.setTranslateX(-FIELD_LENGTH/2);
        field.setTranslateY(-FIELD_LENGTH/2);
        Pyramid pyramid=new Pyramid(50, 50);
        PhongMaterial pyramidMaterial=new PhongMaterial(Color.YELLOW);
        pyramid.setMaterial(pyramidMaterial);
        pyramid.setTranslateX(100);
        Road r1=new Road();
        Group mainGroup=new Group();
        mainGroup.getChildren().addAll(field, pyramid, r1);
        previewCamera=new PerspectiveCamera(true);
        previewCamera.setNearClip(0.1);
        previewCamera.setFarClip(1000);
        mainCamera=new PerspectiveCamera(true);
        mainCamera.setFarClip(10000);
        xRotateMainCamera = new Rotate(180, Rotate.X_AXIS);
        translateMainCamera = new Translate(0, 0, -1000);
        mainCamera.getTransforms().addAll(xRotateMainCamera, translateMainCamera);
        /*mainCamera.setRotationAxis(Rotate.X_AXIS);
        mainCamera.setRotate(180);
        mainCamera.setTranslateZ(+1000);*/
        Rotate rot1=new Rotate(-135, Rotate.X_AXIS);
        Rotate rot2=new Rotate(0, Rotate.Y_AXIS);
        Translate t1=new Translate(0, 0, -700);
        previewCamera.getTransforms().addAll(rot1,rot2,t1);
        PointLight pointLight=new PointLight();
        pointLight.setTranslateX(75);
        //mainGroup.getChildren().add(pointLight);
        Scene scene=new Scene(mainGroup, 640, 480, true);
        scene.setCamera(mainCamera);
        scene.setOnKeyPressed(e->keyPressingMainCamera(e));
        primaryStage.setScene(scene);
        primaryStage.setTitle("Traffic Simulator");
        primaryStage.show();
    }
    
    public void keyPressingMainCamera(KeyEvent e){
        switch(e.getCode()){
            case UP:
                translateMainCamera.setZ(translateMainCamera.getZ()+10);
                break;
            case DOWN:
                translateMainCamera.setZ(translateMainCamera.getZ()-10);
                break;
        }
    }
    public static void main(String[] args){
        launch(args);
    }
}
