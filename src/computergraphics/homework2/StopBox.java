/* */
package computergraphics.homework2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.DrawMode;

/**
 * A 3D box-shaped node which is used to mark vehicles when to stop.
 * <p>All instances of this class are added to a list of all instances in the application when created.
 * To create new StopBoxes, use {@link #createStopBox(double, double, double)} static method.
 */
public class StopBox extends Box{
    private boolean active=true;
    private static PhongMaterial activeMaterial=new PhongMaterial(Color.RED);
    private static PhongMaterial inactiveMaterial=new PhongMaterial(Color.GRAY);
    /**List of all stopBoxes in the application*/
    private static CopyOnWriteArrayList<StopBox> stopBoxes=new CopyOnWriteArrayList<>();
    private static boolean visible=true;
    
    /**
     * Creates new StopBox object and adds it to the list of all stop boxes 
     * @param width
     * @param height
     * @param depth
     * @return The created StopBox object
     */
    public static StopBox createStopBox(double width, double height, double depth){
        StopBox sb=new StopBox(width, height, depth);
        stopBoxes.add(sb);
        return sb;
    }
    
    public static StopBox createStopBox(){
        return createStopBox(DEFAULT_SIZE, DEFAULT_SIZE, DEFAULT_SIZE);
    }
    
    private StopBox(double width, double height, double depth){
        super(width, height, depth);
        setMaterial(activeMaterial);
        setDrawMode(DrawMode.LINE);
        setVisible(visible);
    }
    
    private StopBox(){
        this(DEFAULT_SIZE, DEFAULT_SIZE, DEFAULT_SIZE);
    }

    public boolean isActive() {
        return active;
    }

    /**
     * Sets this StopBox to be active or not. Inactive StopBox does not slow down vehicle passing through it.
     * @param active makes StopBox active or not
     */
    public void setActive(boolean active) {
        this.active = active;
        if(active) setMaterial(activeMaterial);
        else setMaterial(inactiveMaterial);
    }

    public static List<StopBox> getStopBoxes() {
        return stopBoxes;
    }
    
    public static void toggleStopBoxVisibility(){
        visible=!visible;
        for(StopBox sb:stopBoxes) sb.setVisible(visible);
    }
    
    public Bounds getBoundsInScene(){
        return localToScene(getBoundsInLocal());
    }
}
