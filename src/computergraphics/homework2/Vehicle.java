/* */
package computergraphics.homework2;

import javafx.geometry.Point3D;
import javafx.scene.Group;

/**
 * An abstract class which represents vehicle participating in traffic.
 */
public abstract class Vehicle extends Group{
    protected Point3D checkingPoint=new Point3D(0, 0, 0);
    /**Method to be called when the vehicle should calculate its new position
     * @param nanosecondsPassed nanoseconds passed since last update*/
    //check if it is in a stop box or not, then either slow down or speed up
    public abstract void updatePosition(long nanosecondsPassed);
}
