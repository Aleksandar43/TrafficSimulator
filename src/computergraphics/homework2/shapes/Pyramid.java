/* */
package computergraphics.homework2.shapes;

import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
/**
 * 3D shape representing regular pyramid. The pyramid is created so that the
 * center of the pyramid base is at (0, 0, 0) and the apex is on the positive
 * side of z-axis. It can represent a cone if made with many base points.
*/
public class Pyramid extends MeshView{
    private float[] points, texCoords={0, 0, 1, 1};
    private int[] baseTriangles, lateralTriangles;
    private TriangleMesh mesh;
    
    /**
     * Basic constructor
     * @param radius radius of the base enclosing circle (circle passing through all of the base points)
     * @param height distance between the apex and the base
     * @param basePoints number of points in the base
     * @param withBase whether the base should be drawn
     */
    public Pyramid(float radius, float height, int basePoints, boolean withBase){
        mesh=new TriangleMesh();
        if(basePoints<3) basePoints=3;
        points=new float[basePoints*3+3];
        for(int i=0;i<basePoints;i++){
            float x=(float)(Math.PI*2*i/basePoints);
            points[3*i]=(float)Math.cos(x)*radius;
            points[3*i+1]=(float)Math.sin(x)*radius;
            points[3*i+2]=0;
        }
        points[basePoints*3]=0;
        points[basePoints*3+1]=0;
        points[basePoints*3+2]=height;
        lateralTriangles=new int[basePoints*6];
        for(int i=0;i<basePoints;i++){
            lateralTriangles[6*i]=i;
            lateralTriangles[6*i+2]=(i+1)%basePoints;
            lateralTriangles[6*i+4]=basePoints;
            lateralTriangles[6*i+1]=lateralTriangles[6*i+3]=lateralTriangles[6*i+5]=0;
        }
        baseTriangles=new int[(basePoints-2)*6];
        for(int j=1;j<basePoints-1;j++){
            int i=j-1;
            baseTriangles[6*i]=0;
            baseTriangles[6*i+2]=j;
            baseTriangles[6*i+4]=j+1;
            baseTriangles[6*i+1]=baseTriangles[6*i+3]=baseTriangles[6*i+5]=0;
        }
        mesh.getPoints().addAll(points);
        mesh.getTexCoords().addAll(texCoords);
        mesh.getFaces().addAll(lateralTriangles);
        if(withBase) mesh.getFaces().addAll(baseTriangles);
        this.setMesh(mesh);
    }
    
    /**
     * Constructs a 4-sided regular pyramid.
     * @param radius radius of the base enclosing circle (circle passing through all of the base points)
     * @param height distance between the apex and the base
     * @param withBase whether the base should be drawn
     */
    public Pyramid(float radius, float height, boolean withBase){
        this(radius, height, 4, withBase);
    }

    /**
     * Constructs a pyramid with the base drawn.
     * @param radius radius of the base enclosing circle (circle passing through all of the base points)
     * @param height distance between the apex and the base
     * @param basePoints number of points in the base
     */
    public Pyramid(float radius, float height, int basePoints){
        this(radius, height, basePoints, true);
    }
    
    /**
     * Constructs a 4-sided pyramid with the base drawn.
     * @param radius radius of the base enclosing circle (circle passing through all of the base points)
     * @param height distance between the apex and the base
     */
    public Pyramid(float radius, float height){
        this(radius, height, 4, true);
    }
    
    /**
     * Constructs a 4-sided pyramid with base enclosing circle radius of 10, height of 10 and the base drawn.
     */
    public Pyramid(){
        this(10, 10, 4, true);
    }
    
    /**
     * Method for setting whether the base should be drawn so it can be changed dynamically.
     * @param drawBase true if base should be drawn
     */
    public void setDrawBase(boolean drawBase){
        if(drawBase){
            mesh.getFaces().setAll(lateralTriangles);
            mesh.getFaces().addAll(baseTriangles);
        }
        else mesh.getFaces().setAll(lateralTriangles);
    }
}
