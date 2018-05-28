package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;

/**
 * test
 * @author normenhansen
 */
public class Main extends SimpleApplication {
    
    String[] environment_model_names = {"room.j3o"};

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        // Setup environment models
        for (int i = 0; i < environment_model_names.length; i++) {
            Spatial model = assetManager.loadModel("Models-Low/" + environment_model_names[i]);
            // Set materials
            Material material = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
            material.setColor("Diffuse", ColorRGBA.Green);
            model.setMaterial(material);
            model.setLocalTranslation(-10.0f, -10.0f, 30.0f);
            model = model.scale(60.0f);
            // Attach environment models to the root node
            rootNode.attachChild(model);
        }
        
        // Setup the camera
        cam.setFrustumPerspective(45.0f, this.settings.getWidth() / (float)this.settings.getHeight(), 0.1f, 100000.0f);
        cam.setLocation(new Vector3f(0.0f, 0.0f, 0.0f));
        cam.lookAt(new Vector3f(0.0f, 0.0f, -1.0f), new Vector3f(0.0f, 1.0f, 0.0f));
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
