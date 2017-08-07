/* */
package computergraphics.homework2;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.DrawMode;

/**
 * A 3D box-shaped node which is used to mark vehicles when to stop.
 */
public class StopBox extends Box{
    public StopBox(double width, double height, double depth){
        super(width, height, depth);
        PhongMaterial mat=new PhongMaterial(Color.rgb(255, 0, 0, 1));
        setMaterial(mat);
        setDrawMode(DrawMode.LINE);
    }
    
    public StopBox(){
        this(DEFAULT_SIZE, DEFAULT_SIZE, DEFAULT_SIZE);
    }
}
