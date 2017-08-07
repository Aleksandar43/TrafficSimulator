package computergraphics.homework2;

import computergraphics.homework2.shapes.Pyramid;
import javafx.application.Application;
import javafx.scene.Camera;
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
    private Scene scene;
    private PerspectiveCamera previewCamera,mainCamera,junctionCamera;
    private Translate translateMainCamera,translateJunctionCamera;
    private Rotate xRotateMainCamera,xRotateJunctionCamera,yRotateJunctionCamera,zRotateJunctionCamera;
    private static double xPivotJunctionCamera=0,yPivotJunctionCamera=0,zPivotJunctionCamera=50,
            xRotateStartingAngle=225,xRotateMinAngle=180,xRotateMaxAngle=270;
    
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
        Group mainGroup=new Group();
        mainGroup.getChildren().addAll(field, pyramid);
        Junction junction=new Junction();
        mainGroup.getChildren().add(junction);
        for(int i=0;i<5;i++){
            Road r=new Road();
            r.setTranslateY(i*500+500);
            mainGroup.getChildren().add(r);
        }
        for(int i=0;i<5;i++){
            Road r=new Road();
            r.setRotationAxis(Rotate.Z_AXIS);
            r.setRotate(90);
            r.setTranslateX(i*500+500);
            mainGroup.getChildren().add(r);
        }
        previewCamera=new PerspectiveCamera(true);
        previewCamera.setNearClip(0.1);
        previewCamera.setFarClip(10000);
        mainCamera=new PerspectiveCamera(true);
        mainCamera.setFarClip(10000);
        xRotateMainCamera = new Rotate(xRotateStartingAngle, Rotate.X_AXIS);
        translateMainCamera = new Translate(0, 0, -1000);
        mainCamera.getTransforms().addAll(xRotateMainCamera, translateMainCamera);
        Rotate rot1=new Rotate(-135, Rotate.X_AXIS);
        Rotate rot2=new Rotate(0, Rotate.Y_AXIS);
        Translate t1=new Translate(0, 0, -2000);
        previewCamera.getTransforms().addAll(rot1,rot2,t1);
        junctionCamera=new PerspectiveCamera(true);
        junctionCamera.setFarClip(10000);
        translateJunctionCamera=new Translate(0, 0, 50);
        xRotateJunctionCamera=new Rotate(xRotateStartingAngle, xPivotJunctionCamera, yPivotJunctionCamera, zPivotJunctionCamera, Rotate.X_AXIS);
        yRotateJunctionCamera=new Rotate(0, xPivotJunctionCamera, yPivotJunctionCamera, zPivotJunctionCamera, Rotate.Y_AXIS);
        zRotateJunctionCamera=new Rotate(0, xPivotJunctionCamera, yPivotJunctionCamera, zPivotJunctionCamera, Rotate.Z_AXIS);
        junctionCamera.getTransforms().addAll(translateJunctionCamera, zRotateJunctionCamera, yRotateJunctionCamera, xRotateJunctionCamera);
        PointLight pointLight=new PointLight();
        pointLight.setTranslateX(75);
        //mainGroup.getChildren().add(pointLight);
        scene = new Scene(mainGroup, 640, 480, true);
        scene.setCamera(mainCamera);
        scene.setOnKeyPressed(e->keyPressing(e));
        primaryStage.setScene(scene);
        primaryStage.setTitle("Traffic Simulator");
        primaryStage.show();
    }
    
    private void keyPressing(KeyEvent e){
        switch(e.getCode()){
            case DIGIT1:
                scene.setCamera(mainCamera);
                break;
            case DIGIT2:
                scene.setCamera(junctionCamera);
                break;
            case P:
                scene.setCamera(previewCamera);
                break;
            default:
                Camera currentCamera = scene.getCamera();
                if(currentCamera.equals(mainCamera)) keyPressingMainCamera(e);
                if(currentCamera.equals(junctionCamera)) keyPressingJunctionCamera(e);
        }
    }
    
    private void keyPressingMainCamera(KeyEvent e){
        switch(e.getCode()){
            case UP:
                translateMainCamera.setZ(translateMainCamera.getZ()+10);
                break;
            case DOWN:
                translateMainCamera.setZ(translateMainCamera.getZ()-10);
                break;
        }
    }
    
    private void keyPressingJunctionCamera(KeyEvent e){
        switch(e.getCode()){
            case UP:
                if(xRotateJunctionCamera.getAngle()<xRotateMaxAngle)
                    xRotateJunctionCamera.setAngle(xRotateJunctionCamera.getAngle()+1);
                break;
            case DOWN:
                if(xRotateJunctionCamera.getAngle()>xRotateMinAngle)
                    xRotateJunctionCamera.setAngle(xRotateJunctionCamera.getAngle()-1);
                break;
            case LEFT:
                zRotateJunctionCamera.setAngle(zRotateJunctionCamera.getAngle()+1);
                break;
            case RIGHT:
                zRotateJunctionCamera.setAngle(zRotateJunctionCamera.getAngle()-1);
                break;
            case X:
                System.out.println("junctionCamera: "+xRotateJunctionCamera.getAngle()+", "+
                        yRotateJunctionCamera.getAngle()+", "+zRotateJunctionCamera.getAngle());
        }
    }
    
    public static void main(String[] args){
        launch(args);
    }
}
