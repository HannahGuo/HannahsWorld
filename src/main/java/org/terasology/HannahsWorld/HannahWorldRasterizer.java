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
package org.terasology.HannahsWorld;

import org.terasology.math.ChunkMath;
import org.terasology.math.TeraMath;
import org.terasology.math.geom.Vector3i;
import org.terasology.registry.CoreRegistry;
import org.terasology.world.block.Block;
import org.terasology.world.block.BlockManager;
import org.terasology.world.chunks.CoreChunk;
import org.terasology.world.generation.Region;
import org.terasology.world.generation.WorldRasterizer;
import org.terasology.world.generation.facets.SurfaceHeightFacet;

import java.util.concurrent.ThreadLocalRandom;

public class HannahWorldRasterizer implements WorldRasterizer {
    private Block candyYellow;
    private Block candyRed;
    private Block candyBlue;
    private Block candyGreen;
    private Block candyWhite;
    private Block dirt;
    private Block mud;

    @Override
    public void initialize() {
        candyYellow = CoreRegistry.get(BlockManager.class).getBlock("HannahsWorld:CandyYellow");
        candyRed = CoreRegistry.get(BlockManager.class).getBlock("HannahsWorld:CandyRed.block");
        candyBlue = CoreRegistry.get(BlockManager.class).getBlock("HannahsWorld:CandyBlue");
        candyGreen = CoreRegistry.get(BlockManager.class).getBlock("HannahsWorld:CandyGreen");
        candyWhite = CoreRegistry.get(BlockManager.class).getBlock("HannahsWorld:CandyWhite");
        dirt = CoreRegistry.get(BlockManager.class).getBlock("CoreBlocks:Dirt");
        mud = CoreRegistry.get(BlockManager.class).getBlock("CoreBlocks:Mud");
    }

    private Block returnCandyBlock(int block) {
        switch (block) {
            case 1:
                return candyYellow;
            case 2:
                return candyRed;
            case 3:
                return candyBlue;
            case 4:
                return candyGreen;
        };
        return candyWhite;
    }

    @Override
    public void generateChunk(CoreChunk chunk, Region chunkRegion) {
        SurfaceHeightFacet surfaceHeightFacet = chunkRegion.getFacet(SurfaceHeightFacet.class);
        for (Vector3i position : chunkRegion.getRegion()) {
            int surfaceHeight = TeraMath.floorToInt(surfaceHeightFacet.getWorld(position.x, position.z));
            int randomNum = ThreadLocalRandom.current().nextInt(1, 100);
            if (position.y < surfaceHeight && position.y > surfaceHeight - 4) {
                chunk.setBlock(ChunkMath.calcBlockPos(position), returnCandyBlock(randomNum));
            } else if (position.y < surfaceHeight - 4){
                chunk.setBlock(ChunkMath.calcBlockPos(position), mud);
                if(position.y % 4 == 0) {
                    chunk.setBlock(ChunkMath.calcBlockPos(position), dirt);
                }
            }
        }
    }
}