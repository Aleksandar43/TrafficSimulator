/* */
package computergraphics.homework2;

import java.util.ArrayList;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

/**
 * An abstract class which represents vehicle participating in traffic.
 */
public abstract class Vehicle extends Group{
    /**Point used to detect if the vehicle is in braking zone (represented by {@link StopBox}).*/
    protected Point3D checkingPoint;
    /**Camera which tracks the vehicle.*/
    protected PerspectiveCamera vehicleCamera;
    protected Translate cameraDistance,environmentTranslate;
    protected Rotate cameraAngleX,cameraAngleZ,environmentRotate;
    //in meters/second and meters/second^2
    protected double maxSpeed, acceleratingRate, brakingRate, currentSpeed;
    
    public Vehicle(double maxSpeed, double acceleratingRate, double brakingRate, double x, double y, double z) {
        checkingPoint=new Point3D(x, y, z);
        cameraDistance=new Translate(0, 0, -1000);
        cameraAngleX=new Rotate(-135, Rotate.X_AXIS);
        cameraAngleZ=new Rotate(-90, Rotate.Z_AXIS);
        vehicleCamera=new PerspectiveCamera(true);
        vehicleCamera.setFarClip(10000);
        vehicleCamera.getTransforms().addAll(cameraAngleZ,cameraAngleX,cameraDistance);
        this.getChildren().add(vehicleCamera);
        this.maxSpeed=maxSpeed;
        this.acceleratingRate=acceleratingRate;
        this.brakingRate=brakingRate;
        currentSpeed=maxSpeed;
        environmentTranslate=new Translate();
        environmentRotate=new Rotate(0, Rotate.Z_AXIS);
        getTransforms().addAll(environmentRotate,environmentTranslate);
    }
 
    public Vehicle() {
        this(15,15,15,0,0,0);
    }
    
    public Vehicle(double x, double y, double z) {
        this(15,15,15,x,y,z);
    }
    
    public Translate getEnvironmentTranslate() {
        return environmentTranslate;
    }

    public Rotate getEnvironmentRotate() {
        return environmentRotate;
    }

    public void moveToPoint(double x, double y, double z){
        environmentTranslate.setX(x);
        environmentTranslate.setY(y);
        environmentTranslate.setZ(z);
    }
    
    public void rotate(double angle){
        environmentRotate.setAngle(environmentRotate.getAngle()+angle);
    }

    public void rotateVehicleCamera(double angle){
        cameraAngleX.setAngle(cameraAngleX.getAngle()+angle);
    }
    /**Method to be called when the vehicle should calculate its new position
     * @param nanosecondsPassed nanoseconds passed since last update*/
    //check if it is in a stop box or not, then either slow down or speed up
    public void updatePosition(long nanosecondsPassed){
        double seconds=nanosecondsPassed/1e9;
        //1 unit represents 1cm
        environmentTranslate.setX(environmentTranslate.getX()+currentSpeed*100*seconds);
        ArrayList<StopBox> stopBoxes = StopBox.getStopBoxes();
        boolean intersecting=false; 
        for (StopBox sb : stopBoxes) {
            if(sb.isActive() && sb.getBoundsInParent().contains(localToParent(checkingPoint))){
                intersecting=true;
                break;
            }
        }
        if(intersecting){
                currentSpeed=currentSpeed-brakingRate*seconds;
                if(currentSpeed<0) currentSpeed=0;
        } else{
                currentSpeed=currentSpeed+acceleratingRate*seconds;
                if(currentSpeed>maxSpeed) currentSpeed=maxSpeed;
        }
    }
}
