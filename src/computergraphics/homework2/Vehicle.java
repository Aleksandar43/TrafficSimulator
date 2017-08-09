/* */
package computergraphics.homework2;

import javafx.geometry.Point3D;
import javafx.scene.Group;

/**
 * An abstract class which represents vehicle participating in traffic.
 */
public abstract class Vehicle extends Group{
    protected Point3D checkingPoint=new Point3D(0, 0, 0);
    /**Method to be called when the vehicle should calculate its new position.*/
    public abstract void updatePosition();
}
