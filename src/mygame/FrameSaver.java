/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.app.Application;
import com.jme3.app.state.AppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.post.SceneProcessor;
import com.jme3.profile.AppProfiler;
import com.jme3.renderer.Camera;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.texture.FrameBuffer;
import com.jme3.texture.Image;
import com.jme3.util.BufferUtils;
import com.jme3.util.Screenshots;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

/**
 *
 * @author sunlu
 */
public class FrameSaver implements SceneProcessor, AppState {
    
    ByteBuffer byteBuffer;
    BufferedImage rawFrame;
    RenderManager renderManager;
    
    boolean isInitialized = false;

    @Override
    public void initialize(RenderManager rm, ViewPort vp) {
        Camera camera = vp.getCamera();
        int width = camera.getWidth();
        int height = camera.getHeight();
        byteBuffer = BufferUtils.createByteBuffer(width * height * 3);
        rawFrame = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        renderManager = rm;
        
        isInitialized = true;
    }

    @Override
    public void reshape(ViewPort vp, int w, int h) {}

    @Override
    public boolean isInitialized() {
        return isInitialized;
    }

    @Override
    public void preFrame(float tpf) {}

    @Override
    public void postQueue(RenderQueue rq) {}

    @Override
    public void postFrame(FrameBuffer out) {
        byteBuffer.clear();
        renderManager.getRenderer().readFrameBufferWithFormat(out, byteBuffer, Image.Format.BGR8);
        Screenshots.convertScreenShot(byteBuffer, rawFrame);
    }

    @Override
    public void cleanup() {}

    @Override
    public void setProfiler(AppProfiler profiler) {}

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        isInitialized = true;
    }

    @Override
    public void setEnabled(boolean active) {}

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public void stateAttached(AppStateManager stateManager) {}

    @Override
    public void stateDetached(AppStateManager stateManager) {}

    @Override
    public void update(float tpf) {}

    @Override
    public void render(RenderManager rm) {}

    @Override
    public void postRender() {}
    
    
    
}
