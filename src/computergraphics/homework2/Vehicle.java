/* */
package computergraphics.homework2;

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
    protected double maxSpeed, acceleratingRate, brakingRate, currentSpeed=maxSpeed;
    
    public Vehicle(double maxSpeed, double acceleratingRate, double brakingRate, double x, double y, double z) {
        checkingPoint=new Point3D(x, y, z);
        cameraDistance=new Translate(0, 0, -800);
        cameraAngleX=new Rotate(-135, Rotate.X_AXIS);
        cameraAngleZ=new Rotate(-90, Rotate.Z_AXIS);
        vehicleCamera=new PerspectiveCamera(true);
        vehicleCamera.setFarClip(10000);
        vehicleCamera.getTransforms().addAll(cameraAngleZ,cameraAngleX,cameraDistance);
        this.getChildren().add(vehicleCamera);
        this.maxSpeed=maxSpeed;
        this.acceleratingRate=acceleratingRate;
        this.brakingRate=brakingRate;
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

    /**Method to be called when the vehicle should calculate its new position
     * @param nanosecondsPassed nanoseconds passed since last update*/
    //check if it is in a stop box or not, then either slow down or speed up
    public void updatePosition(long nanosecondsPassed){
        double seconds=nanosecondsPassed/1e9;
        
    }
}
