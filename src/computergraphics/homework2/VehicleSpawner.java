/* */
package computergraphics.homework2;

import javafx.application.Platform;
import javafx.geometry.Point3D;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

/**
 *
 * @author Aleksandar
 */
public class VehicleSpawner implements Runnable {
    
    private static int s = 0;
    private int id;
    private Point3D position;
    private double angle;
    private Truck lastGeneratedTruck;
    private Box checkingBox;

    public VehicleSpawner(double x, double y, double z) {
        this(x, y, z, 0);
    }

    public VehicleSpawner(double x, double y, double z, double angle) {
        id = s;
        s++;
        position = new Point3D(x, y, z);
        this.angle = angle;
        checkingBox = new Box(1000, 100, 1000);
        PhongMaterial m = new PhongMaterial(Color.rgb(255, 255, 255, 0.2));
        checkingBox.setMaterial(m);
        checkingBox.getTransforms().addAll(new Rotate(angle),new Translate(x, y, z));
        Main.addToMainGroup(checkingBox);
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                if (lastGeneratedTruck == null || !checkingBox.getBoundsInParent().intersects(lastGeneratedTruck.getBoundsInParent())) {
                    Truck t = new Truck();
                    t.moveToPoint(position.getX(), position.getY(), position.getZ());
                    t.rotate(angle);
                    lastGeneratedTruck = t;
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            Main.addToMainGroup(t);
                        }
                    });
                }
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                System.out.println("Thread Vehicle spawner interrupted");
            }
        }
    }
    
}
