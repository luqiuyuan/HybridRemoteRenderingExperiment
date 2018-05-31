package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.light.PointLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.RenderManager;import com.jme3.renderer.ViewPort;
;
import com.jme3.scene.Spatial;
import java.util.ArrayList;
import java.util.Random;

/**
 * test
 * @author normenhansen
 */
public class Main extends SimpleApplication {
    
    // Constants
    // Model moving boundaries
    static float X_OFFSET_MIN = -3.0f, X_OFFSET_MAX = 3.0f;
    static float Y_OFFSET_MIN = -0.5f, Y_OFFSET_MAX = 0.5f;
    static float Z_OFFSET_MIN = -6.0f, Z_OFFSET_MAX = -1.0f;
    // Velocity boundaries
    static float VELOCITY_MIN = 0.0f, VELOCITY_MAX = 2.0f;
    
    // Models to use
    String[] environment_model_names = {"room.j3o"};
    String[] model_names = {"cat.j3o", "cat2.j3o"};
    ArrayList<Spatial> models;
    
    // Model positions and velocities
    ArrayList<Vector3f> positions;
    ArrayList<Vector3f> velocities;
    
    // Random generator
    Random rn;

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        // Setup environment models
        for (String environment_model_name : environment_model_names) {
            Spatial model = assetManager.loadModel("Models-High/" + environment_model_name);
            // Set materials
            Material material = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
            material.setColor("Diffuse", ColorRGBA.Green);
            model.setMaterial(material);
            model.setLocalTranslation(-10.0f, -10.0f, 30.0f);
            model = model.scale(60.0f);
            // Attach the environment model to the root node
            rootNode.attachChild(model);
        }
        
        // Initialize the random generator
        rn = new Random();
        
        // Setup 
        models = new ArrayList<>();
        positions = new ArrayList<>();
        for (String model_name : model_names) {
            Spatial model = assetManager.loadModel("Models-High/" + model_name);
            
            // Initialize position
            Vector3f position = new Vector3f();
            position.x = X_OFFSET_MIN + (X_OFFSET_MAX - X_OFFSET_MIN) * rn.nextFloat();
            position.y = Y_OFFSET_MIN + (Y_OFFSET_MAX - Y_OFFSET_MIN) * rn.nextFloat();
            position.z = Z_OFFSET_MIN + (Z_OFFSET_MAX - Z_OFFSET_MIN) * rn.nextFloat();
            positions.add(position);
            model.setLocalTranslation(position);
            
            // Attach the model to the root node
            rootNode.attachChild(model);
            
            // Attach the model to the model array list
            models.add(model);
        }
        
        // Setup light sources
        // Add a directional light
        DirectionalLight sun = new DirectionalLight();
        sun.setColor(new ColorRGBA(0.5f, 0.37f, 0.18f, 0.25f));
        sun.setDirection(new Vector3f(-0.577f, -.577f, 0.577f));
        rootNode.addLight(sun);
        // Add a point ligh
        PointLight lamp_light = new PointLight();
        lamp_light.setColor(new ColorRGBA(0.5f, 0.5f, 0.5f, 0.5f));
        lamp_light.setRadius(40000.0f);
        lamp_light.setPosition(new Vector3f(0.0f, 7.7f, 7.7f));
        rootNode.addLight(lamp_light);
        // Add an ambient light
        AmbientLight al = new AmbientLight();
        al.setColor(new ColorRGBA(0.3f, 0.3f, 0.3f, 0.25f));
        rootNode.addLight(al);
        
        // Setup the camera
        cam.setFrustumPerspective(45.0f, this.settings.getWidth() / (float)this.settings.getHeight(), 0.1f, 100000.0f);
        cam.setLocation(new Vector3f(0.0f, 0.0f, 0.0f));
        cam.lookAt(new Vector3f(0.0f, 0.0f, -1.0f), new Vector3f(0.0f, 1.0f, 0.0f));
        
        // Initialize velocities
        velocities = new ArrayList<>();
        for (int i = 0; i < model_names.length; i++) {
            velocities.add(generateNewVelocity());
        }
        
        // Attch renderer
        attachRenderer(false);
        attachRenderer(true);
    }

    @Override
    public void simpleUpdate(float tpf) {
        // Update positions
        for (int i = 0; i < model_names.length; i++) {
            Vector3f new_position = positions.get(i).add(velocities.get(i).mult(tpf));
            if (isOutOfBoundaries(new_position)) { // If the next-frame position is out of boundaries
                velocities.set(i, generateNewVelocity());
                new_position = positions.get(i);
            }
            
            positions.set(i, new_position);
            models.get(i).setLocalTranslation(new_position);
        }
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
    
    private boolean isOutOfBoundaries(Vector3f position) {
        return !(position.x >= X_OFFSET_MIN && position.x <= X_OFFSET_MAX
                && position.y >= Y_OFFSET_MIN && position.y <= Y_OFFSET_MAX
                && position.z >= Z_OFFSET_MIN && position.z <= Z_OFFSET_MAX);
    }
    
    private Vector3f generateNewVelocity() {
        Vector3f velocity = new Vector3f();
        velocity.x = rn.nextFloat() * 2 -1;
        velocity.y = rn.nextFloat() * 2 -1;
        velocity.z = rn.nextFloat() * 2 -1;
        velocity = velocity.normalize();
        velocity = velocity.mult(VELOCITY_MIN + rn.nextFloat() * (VELOCITY_MAX - VELOCITY_MIN));
        return velocity;
    }
    
    private void attachRenderer(boolean is_round2) {
        FrameSaver frame_saver = new FrameSaver(is_round2);
        ViewPort view_port;
        if (is_round2) {
            view_port = renderManager.createPostView("round 2", cam);
            view_port.setClearFlags(true, false, false);
        } else {
            view_port = renderManager.createPostView("round 1", cam);
            view_port.setClearFlags(true, true, true);
        }
        for (Spatial s : guiViewPort.getScenes()) {
            view_port.attachScene(s);
        }
        for (Spatial s : viewPort.getScenes()) {
            view_port.attachScene(s);
        }
        view_port.addProcessor(frame_saver);
    }
}
