/* */
package computergraphics.homework2;

import javafx.scene.image.Image;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

public class Junction extends Box{
    public Junction(){
        super(500, 500, 1);
        PhongMaterial mat=new PhongMaterial();
        mat.setDiffuseMap(new Image("computergraphics/homework2/images/junction.png"));
        setMaterial(mat);
    }
}
