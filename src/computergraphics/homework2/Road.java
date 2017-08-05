/* */
package computergraphics.homework2;

import javafx.scene.image.Image;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

public class Road extends Box{
    public Road(){
        super(500, 500, 1);
        PhongMaterial mat=new PhongMaterial();
        mat.setDiffuseMap(new Image("computergraphics/homework2/images/road.png"));
        setMaterial(mat);
    }
}
