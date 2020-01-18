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
import org.terasology.utilities.procedural.Noise;
import org.terasology.utilities.procedural.SimplexNoise;
import org.terasology.world.block.Block;
import org.terasology.world.block.BlockManager;
import org.terasology.world.chunks.CoreChunk;
import org.terasology.world.generation.Region;
import org.terasology.world.generation.WorldRasterizer;
import org.terasology.world.generation.WorldRasterizerPlugin;
import org.terasology.world.generator.plugin.RegisterPlugin;

import java.util.Map;

@RegisterPlugin
public class CandyDecorRasterizer implements WorldRasterizerPlugin {
    private Block diamondBar;
    private Block goldBar;
    private Block copperBar;
    private Block ironBar;
    private Block diamondOre;
    private Block goldOre;
    private Block copperOre;
    private Block ironOre;
    private Block stone;
    private int counter = 0;

    @Override
    public void initialize() {
        diamondBar = CoreRegistry.get(BlockManager.class).getBlock("CoreBlocks:DiamondBar");
        goldBar = CoreRegistry.get(BlockManager.class).getBlock("CoreBlocks:GoldBar");
        copperBar = CoreRegistry.get(BlockManager.class).getBlock("CoreBlocks:CopperBar");
        ironBar = CoreRegistry.get(BlockManager.class).getBlock("CoreBlocks:IronBar");
        diamondOre = CoreRegistry.get(BlockManager.class).getBlock("CoreBlocks:DiamondOre");
        goldOre = CoreRegistry.get(BlockManager.class).getBlock("CoreBlocks:GoldOre");
        copperOre = CoreRegistry.get(BlockManager.class).getBlock("CoreBlocks:CopperOre");
        ironOre = CoreRegistry.get(BlockManager.class).getBlock("CoreBlocks:IronOre");
        stone = CoreRegistry.get(BlockManager.class).getBlock("CoreBlocks:Stone");
    }

    public Block chooseBlock() {
        if(counter == 300) {
            counter = 0;
        }
        switch(counter){
            case 142:
                counter+=1;
                return diamondBar;
            case 87:
                counter+=1;
                return goldBar;
            case 1:
                counter+=1;
                return copperBar;
            case 152:
                counter+=1;
                return ironBar;
            case 231:
            case 111:
                counter+=1;
                return diamondOre;
            case 5:
            case 229:
                counter+=1;
                return goldOre;
            case 171:
            case 12:
                counter+=1;
                return copperOre;
            case 43:
            case 65:
                counter+=1;
                return ironOre;
        }
        counter += 1;
        return stone;
    }

    @Override
    public void generateChunk(CoreChunk chunk, Region chunkRegion) {
        CandyDecorFacet candyDecorFacet = chunkRegion.getFacet(CandyDecorFacet.class);
        SimplexNoise noise = new SimplexNoise(31495);
        for (Map.Entry<BaseVector3i, CandyDecor> entry : candyDecorFacet.getWorldEntries().entrySet()) {
            Vector3i candyPosition = new Vector3i(entry.getKey()).addY(0);

            Vector3i candyMinimumPos = new Vector3i(candyPosition).sub(1, 0, 0);

            int candyHeight = entry.getValue().getHeight();
            int candyWidth = entry.getValue().getWidth();

            Region3i candyRegion = Region3i.createFromMinAndSize(candyMinimumPos, new Vector3i(candyWidth, candyHeight, candyWidth));

            for (Vector3i newBlockPosition : candyRegion) {
                if (chunkRegion.getRegion().encompasses(newBlockPosition)) {
                        chunk.setBlock(ChunkMath.calcBlockPos(newBlockPosition), chooseBlock());
                }
            }
        }
    }
}