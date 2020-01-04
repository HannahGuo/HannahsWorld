package org.terasology.HannahsWorld.Houses;

import org.terasology.HannahsWorld.Houses.House;
import org.terasology.HannahsWorld.Houses.HouseFacet;
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

public class HouseRasterizer implements WorldRasterizer {
    private Block stone;

    @Override
    public void initialize() {
        stone = CoreRegistry.get(BlockManager.class).getBlock("CoreBlocks:Stone");
    }

    @Override
    public void generateChunk(CoreChunk chunk, Region chunkRegion) {
        HouseFacet houseFacet = chunkRegion.getFacet(HouseFacet.class);

        for (Map.Entry<BaseVector3i, House> entry : houseFacet.getWorldEntries().entrySet()) {
            // there should be a house here
            // create a couple 3d regions to help iterate through the cube shape, inside and out
            Vector3i centerHousePosition = new Vector3i(entry.getKey());
            int extent = entry.getValue().getExtent();
            centerHousePosition.add(0, extent, 0);
            Region3i walls = Region3i.createFromCenterExtents(centerHousePosition, extent);
            Region3i inside = Region3i.createFromCenterExtents(centerHousePosition, extent - 1);

            // loop through each of the positions in the cube, ignoring the inside
            for (Vector3i newBlockPosition : inside) {
                if (chunkRegion.getRegion().encompasses(newBlockPosition)
                        && !walls.encompasses(newBlockPosition)) {
                    chunk.setBlock(ChunkMath.calcBlockPos(newBlockPosition), stone);
                }
            }
        }
    }
}