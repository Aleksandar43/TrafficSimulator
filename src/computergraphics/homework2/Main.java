package computergraphics.homework2;

import computergraphics.homework2.shapes.Pyramid;
import java.util.Iterator;
import java.util.List;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.AmbientLight;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

public class Main extends Application{
    private Scene scene;
    private Junction junction;
    private PerspectiveCamera previewCamera,mainCamera,junctionCamera;
    private static Group mainGroup;
    private Translate translateMainCamera,translateJunctionCamera;
    private Rotate xRotateMainCamera,zRotateMainCamera,xRotateJunctionCamera,yRotateJunctionCamera,zRotateJunctionCamera;
    private static double xPivotJunctionCamera=0,yPivotJunctionCamera=0,zPivotJunctionCamera=150,
            xRotateStartingAngle=225,xRotateMinAngle=180,xRotateMaxAngle=270,
            mainCameraDownLimit=-1000, mainCameraUpLimit=-10000;
    private static final double DISTANCE_AWAY=-6000, DISTANCE_SIDE=-100;
    
    private class TrafficTimer extends AnimationTimer{
        private long previous=0;
        @Override
        public void handle(long now) {
            if(previous==0) previous=now;
            List<Vehicle> allVehicles = Vehicle.getAllVehicles();
            Iterator<Vehicle> listIterator = allVehicles.listIterator();
            while(listIterator.hasNext()){
                Vehicle v = listIterator.next();
                if(Math.abs(v.getEnvironmentTranslate().getX())>10000 || Math.abs(v.getEnvironmentTranslate().getY())>10000){
                    v.remove();
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            mainGroup.getChildren().remove(v);
                        }
                    });
                }
                else v.updatePosition(now-previous);
            }
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
    
    /**
     * A method to add nodes to the main group because it has private access.
     * @param node Node to be added
     */
    public static void addToMainGroup(Node node){
        mainGroup.getChildren().add(node);
    }
    
    /**
     * A method to remove nodes from the main group because it has private access.
     * @param node Node to be added
     */
    public static void removeFromMainGroup(Node node){
        mainGroup.getChildren().remove(node);
    }
    
    private VehicleSpawner spawner,spawner2;
    private VehicleSpawner[] spawners;
    private Thread[] spawnerThreads;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        int FIELD_LENGTH=10000;
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
        mainGroup = new Group();
//        mainGroup.getChildren().add(field);
        mainGroup.getChildren().add(grass);
        mainGroup.getChildren().add(pyramid);
        junction = new Junction(7000, 8000);
        mainGroup.getChildren().add(junction);
        for(int i=0;i<10;i++){
            Road r=new Road();
            r.setTranslateY(i*500+500);
            mainGroup.getChildren().add(r);
        }
        for(int i=0;i<10;i++){
            Road r=new Road();
            r.setRotationAxis(Rotate.Z_AXIS);
            r.setRotate(90);
            r.setTranslateX(i*500+500);
            mainGroup.getChildren().add(r);
        }
        for(int i=0;i<10;i++){
            Road r=new Road();
            r.setTranslateY(-(i*500+500));
            mainGroup.getChildren().add(r);
        }
        for(int i=0;i<10;i++){
            Road r=new Road();
            r.setRotationAxis(Rotate.Z_AXIS);
            r.setRotate(-90);
            r.setTranslateX(-(i*500+500));
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
        Translate toCheck=new Translate(6000, 0, 0);
        previewCamera.getTransforms().addAll(rot1,rot2,t1,toCheck);
        junctionCamera=new PerspectiveCamera(true);
        junctionCamera.setFarClip(10000);
        translateJunctionCamera=new Translate(0, 0, 50);
        xRotateJunctionCamera=new Rotate(xRotateStartingAngle, xPivotJunctionCamera, yPivotJunctionCamera, zPivotJunctionCamera, Rotate.X_AXIS);
        yRotateJunctionCamera=new Rotate(0, xPivotJunctionCamera, yPivotJunctionCamera, zPivotJunctionCamera, Rotate.Y_AXIS);
        zRotateJunctionCamera=new Rotate(0, xPivotJunctionCamera, yPivotJunctionCamera, zPivotJunctionCamera, Rotate.Z_AXIS);
        junctionCamera.getTransforms().addAll(translateJunctionCamera, zRotateJunctionCamera, yRotateJunctionCamera, xRotateJunctionCamera);
        //dummy vehicle box
        dummy = new Box(350, 150, 150);
        PhongMaterial materialDummy=new PhongMaterial(Color.BROWN);
        dummy.setMaterial(materialDummy);
        translateDummy = new Translate(-3000, -87.5, 75);
        dummy.getTransforms().add(translateDummy);
        mainGroup.getChildren().add(dummy);
        //testing vehicle
        truck = new Truck();
        truck.moveToPoint(-3000, -75, 0);
        truck.rotate(90);
        mainGroup.getChildren().add(truck);
        truck2 = new Truck();
        truck2.moveToPoint(-6000, -75, 0);
        truck2.rotate(90);
        mainGroup.getChildren().add(truck2);
//        spawner=new VehicleSpawner(-6000, -100, 0);
//        Thread spawnerThread=new Thread(spawner, "Spawner");
//        spawnerThread.setDaemon(true);
//        spawnerThread.start();
//        spawner2=new VehicleSpawner(-6000, -100, 0, 90);
//        Thread spawnerThread2=new Thread(spawner2, "Spawner2");
//        spawnerThread2.setDaemon(true);
//        spawnerThread2.start();
        spawners=new VehicleSpawner[4];
        spawnerThreads=new Thread[4];
        for(int i=0;i<spawners.length;i++){
            spawners[i]=new VehicleSpawner(DISTANCE_AWAY, DISTANCE_SIDE, 0, i*90);
            spawnerThreads[i]=new Thread(spawners[i], "Spawner-"+i);
            spawnerThreads[i].setDaemon(true);
            spawnerThreads[i].start();
        }
        AmbientLight ambientLight=new AmbientLight(Color.WHITE);
        mainGroup.getChildren().add(ambientLight);
        scene = new Scene(mainGroup, 640, 480, true);
        scene.setFill(Color.SKYBLUE);
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
                translateMainCamera.setZ(translateMainCamera.getZ()*Math.pow(10, 0.1));
                if(translateMainCamera.getZ()<mainCameraUpLimit) translateMainCamera.setZ(mainCameraUpLimit);
                break;
            case DOWN:
                translateMainCamera.setZ(translateMainCamera.getZ()/Math.pow(10, 0.1));
                if(translateMainCamera.getZ()>mainCameraDownLimit) translateMainCamera.setZ(mainCameraDownLimit);
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
