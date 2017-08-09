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
    private StopBox[] localStopBoxes=new StopBox[4];
    public Junction(){
        junction=new Box(500, 500, 1);
        PhongMaterial mat=new PhongMaterial();
        mat.setDiffuseMap(new Image("computergraphics/homework2/images/junction.png"));
        junction.setMaterial(mat);
        getChildren().add(junction);
        for(int i=0;i<localStopBoxes.length;i++){
            localStopBoxes[i]=StopBox.createStopBox(200, 200, 200);
            localStopBoxes[i].getTransforms().addAll(new Rotate(i*90, Rotate.Z_AXIS),new Translate(100, 250+200/2, 100));
            if(i%2==0) localStopBoxes[i].setActive(true);
            else localStopBoxes[i].setActive(false);
            getChildren().add(localStopBoxes[i]);
        }
    }

    public StopBox[] getLocalStopBoxes() {
        return localStopBoxes;
    }
    
    public void toggleActiveStopBoxes(){
        for(StopBox sb:localStopBoxes) sb.setActive(!sb.isActive());
    }
}
