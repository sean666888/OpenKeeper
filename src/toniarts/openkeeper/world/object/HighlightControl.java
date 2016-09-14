/*
 * Copyright (C) 2014-2016 OpenKeeper
 *
 * OpenKeeper is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * OpenKeeper is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with OpenKeeper.  If not, see <http://www.gnu.org/licenses/>.
 */
package toniarts.openkeeper.world.object;

import com.jme3.material.Material;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.SceneGraphVisitor;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import java.util.logging.Logger;
import static toniarts.openkeeper.world.MapLoader.COLOR_FLASH;

/**
 *
 * @author ArchDemon
 */
public class HighlightControl extends AbstractControl {
    //private static final float TIME_MAX = 0.1f;
    //private float time = TIME_MAX;
    private boolean active = true;
    private static final Logger logger = Logger.getLogger(HighlightControl.class.getName());

    @Override
    public void setSpatial(Spatial spatial) {
        super.setSpatial(spatial);

        if (spatial != null) {
            setHighlight(true);
        }
    }

    @Override
    protected void controlUpdate(float tpf) {
        if (!isEnabled()) {
            return;
        }

        if (!active) {
            setHighlight(false);
            spatial.removeControl(HighlightControl.class);
        }

        active = false;
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        // nothing to render, maybe
    }

    public void activate() {
        active = true;
    }

    private void setHighlight(final boolean enabled) {
        spatial.depthFirstTraversal(new SceneGraphVisitor() {
            @Override
            public void visit(Spatial spatial) {
                if (!(spatial instanceof Geometry)) {
                    return;
                }

                try {
                    Material material = ((Geometry) spatial).getMaterial();
                    material.setColor("Ambient", COLOR_FLASH);
                    material.setBoolean("UseMaterialColors", enabled);
                } catch (Exception e) {
                    logger.warning(e.toString());
                }
            }
        });
    }
}
