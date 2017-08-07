/* */
package computergraphics.homework2;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class Junction extends Group{
    private Box junction;
    private StopBox[] stopBoxes=new StopBox[4];
    public Junction(){
        junction=new Box(500, 500, 1);
        PhongMaterial mat=new PhongMaterial();
        mat.setDiffuseMap(new Image("computergraphics/homework2/images/junction.png"));
        junction.setMaterial(mat);
        getChildren().add(junction);
        for(int i=0;i<stopBoxes.length;i++){
            stopBoxes[i]=new StopBox(200, 200, 200);
            stopBoxes[i].getTransforms().addAll(new Rotate(i*90, Rotate.Z_AXIS),new Translate(100, 250+200/2, 100));
            getChildren().add(stopBoxes[i]);
        }
        /*StopBox stopBox=new StopBox(50, 50, 50);
        stopBox.getTransforms().addAll(new Rotate(45, Rotate.Z_AXIS),new Translate(200, 600, 0));
        getChildren().add(stopBox);*/
    }
}
