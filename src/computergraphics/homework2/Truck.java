/* */
package computergraphics.homework2;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

//rotation point: between frontBox and rearBox
public class Truck extends Vehicle{
    private Box frontBox, rearBox;
    private PhongMaterial frontBoxMaterial, rearBoxMaterial;
    public Truck(){
        super.initVehicle(10, 3, 3, 0, -100, 0);
        frontBox=new Box(100,150,100);
        frontBoxMaterial=new PhongMaterial(Color.LIGHTPINK);
        frontBox.setMaterial(frontBoxMaterial);
        frontBox.setTranslateX(50);
        frontBox.setTranslateZ(50);
        rearBox=new Box(300, 150, 150);
        rearBoxMaterial=new PhongMaterial(Color.RED);
        rearBox.setMaterial(rearBoxMaterial);
        rearBox.setTranslateX(-150);
        rearBox.setTranslateZ(75);
        stopBox=StopBox.createStopBox(2000, 250, 150);
        stopBox.setTranslateX(-450-2000/2);
        getChildren().addAll(frontBox,rearBox,stopBox);
    }
    
}
