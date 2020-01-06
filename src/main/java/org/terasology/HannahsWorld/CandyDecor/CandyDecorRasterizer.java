/*
 * Copyright 2019 MovingBlocks
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.terasology.HannahsWorld.CandyDecor;

import org.terasology.math.ChunkMath;
import org.terasology.math.Region3i;
import org.terasology.math.geom.BaseVector3i;
import org.terasology.math.geom.Vector3i;
import org.terasology.registry.CoreRegistry;
import org.terasology.world.block.Block;
import org.terasology.world.block.BlockManager;
import org.terasology.world.chunks.CoreChunk;
import org.terasology.world.generation.Region;
import org.terasology.world.generation.WorldRasterizer;

import java.util.Map;

public class CandyDecorRasterizer implements WorldRasterizer {
    private Block candyDark;

    @Override
    public void initialize() {
        candyDark = CoreRegistry.get(BlockManager.class).getBlock("HannahsWorld:CandyDark");
    }

    @Override
    public void generateChunk(CoreChunk chunk, Region chunkRegion) {
        CandyDecorFacet candyDecorFacet = chunkRegion.getFacet(CandyDecorFacet.class);

        for (Map.Entry<BaseVector3i, CandyDecor> entry : candyDecorFacet.getWorldEntries().entrySet()) {
            Vector3i candyPosition = new Vector3i(entry.getKey()).addY(0);

            Vector3i candyMinimumPos = new Vector3i(candyPosition).sub(1, 0, 0);

            int candyHeight = entry.getValue().getHeight();
            int candyWidth = entry.getValue().getWidth();

            Region3i candyRegion = Region3i.createFromMinAndSize(candyMinimumPos, new Vector3i(candyWidth, candyHeight, candyWidth));

            for (Vector3i newBlockPosition : candyRegion) {
                if (chunkRegion.getRegion().encompasses(newBlockPosition)) {
                        chunk.setBlock(ChunkMath.calcBlockPos(newBlockPosition), candyDark);
                }
            }
        }
    }
}