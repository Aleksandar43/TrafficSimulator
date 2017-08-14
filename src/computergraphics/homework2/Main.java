package computergraphics.homework2;

import computergraphics.homework2.shapes.Pyramid;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

public class Main extends Application{
    private Scene scene;
    private Junction junction;
    private PerspectiveCamera previewCamera,mainCamera,junctionCamera;
    private Translate translateMainCamera,translateJunctionCamera;
    private Rotate xRotateMainCamera,zRotateMainCamera,xRotateJunctionCamera,yRotateJunctionCamera,zRotateJunctionCamera;
    private static double xPivotJunctionCamera=0,yPivotJunctionCamera=0,zPivotJunctionCamera=150,
            xRotateStartingAngle=225,xRotateMinAngle=180,xRotateMaxAngle=270;
    
    private class TrafficTimer extends AnimationTimer{
        private long previous=0;
        @Override
        public void handle(long now) {
            if(previous==0) previous=now;
            truck.updatePosition(now-previous);
            truck2.updatePosition(now-previous);
            translateDummy.setX(translateDummy.getX()+dummySpeed);
            StopBox[] stopBoxes = junction.getLocalStopBoxes();
            boolean intersecting=false; 
            for (StopBox stopBox : stopBoxes) {
                if(stopBox.isActive() && dummy.getBoundsInParent().intersects(stopBox.getBoundsInParent())){
                    intersecting=true;
                    break;
                }
            }
            if(intersecting){
                if(dummySpeed>0) dummySpeed=dummySpeed-0.5;
            }
            else{
                if(dummySpeed<15) dummySpeed=dummySpeed+0.5;
            }
            previous=now;
        }
    }
    
    private TrafficTimer trafficTimer=new TrafficTimer();
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        int FIELD_LENGTH=1000;
//        Rectangle field=new Rectangle(FIELD_LENGTH, FIELD_LENGTH, Color.GREEN);
//        field.setTranslateX(-FIELD_LENGTH/2);
//        field.setTranslateY(-FIELD_LENGTH/2);
        Box grass=new Box(FIELD_LENGTH, FIELD_LENGTH, 10);
        grass.setTranslateZ(-10);
        PhongMaterial grassMaterial=new PhongMaterial(Color.GREEN);
        grass.setMaterial(grassMaterial);
        Pyramid pyramid=new Pyramid(50, 50);
        PhongMaterial pyramidMaterial=new PhongMaterial(Color.YELLOW);
        pyramid.setMaterial(pyramidMaterial);
        pyramid.setTranslateX(100);
        Group mainGroup=new Group();
//        mainGroup.getChildren().add(field);
        mainGroup.getChildren().add(grass);
        mainGroup.getChildren().add(pyramid);
        junction = new Junction(7000, 7000);
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
        mainCamera.setFarClip(20000);
        xRotateMainCamera=new Rotate(180, Rotate.X_AXIS);
        zRotateMainCamera=new Rotate(180, Rotate.Z_AXIS);
        translateMainCamera=new Translate(0, 0, -10000);
        mainCamera.getTransforms().addAll(xRotateMainCamera, zRotateMainCamera, translateMainCamera);
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
        //dummy vehicle box
        dummy = new Box(350, 150, 150);
        PhongMaterial materialDummy=new PhongMaterial(Color.BROWN);
        dummy.setMaterial(materialDummy);
        translateDummy = new Translate(-3000, -87.5, 75);
        dummy.getTransforms().add(translateDummy);
        mainGroup.getChildren().add(dummy);
        //testing vehicle
        truck = new Truck();
        truck.moveToPoint(-2000, -75, 0);
        truck.rotate(90);
        mainGroup.getChildren().add(truck);
        truck2 = new Truck();
        truck2.moveToPoint(-4000, -75, 0);
        truck2.rotate(90);
        mainGroup.getChildren().add(truck2);
        scene = new Scene(mainGroup, 640, 480, true);
        scene.setCamera(mainCamera);
        scene.setOnKeyPressed(e->keyPressing(e));
        primaryStage.setScene(scene);
        primaryStage.setTitle("Traffic Simulator");
        primaryStage.show();
        trafficTimer.start();
    }
    private Truck truck,truck2;
    private Translate translateDummy;
    private Box dummy;
    private double dummySpeed=15;
    
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
            case V:
                StopBox.toggleStopBoxVisibility();
                break;
            case A:
                junction.toggleActiveStopBoxes();
                break;
            case T:
                scene.setCamera(truck.vehicleCamera);
                break;
            case Y:
                scene.setCamera(truck2.vehicleCamera);
                break;
            default:
                Camera currentCamera = scene.getCamera();
                if(currentCamera.equals(mainCamera)) keyPressingMainCamera(e);
                if(currentCamera.equals(junctionCamera)) keyPressingJunctionCamera(e);
                if(currentCamera.equals(truck.vehicleCamera)||currentCamera.equals(truck2.vehicleCamera)) keyPressingTruckCamera(e);
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
                break;
        }
    }
    
    private void keyPressingTruckCamera(KeyEvent e){
        switch(e.getCode()){
            case UP:
                truck.rotateVehicleCamera(-5);
                break;
            case DOWN:
                truck.rotateVehicleCamera(+5);
                break;
        }
    }

    @Override
    public void stop() throws Exception {
        //
    }
    
    public static void main(String[] args){
        launch(args);
    }
}
