package org.terasology.HannahsWorld.Candle;

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
import java.util.concurrent.ThreadLocalRandom;

public class CandleRasterizer implements WorldRasterizer {
    private Block candlePink;
    private Block lava;

    @Override
    public void initialize() {
        candlePink = CoreRegistry.get(BlockManager.class).getBlock("HannahsWorld:CandlePink");
        lava = CoreRegistry.get(BlockManager.class).getBlock("CoreBlocks:Lava");
    }

    @Override
    public void generateChunk(CoreChunk chunk, Region chunkRegion) {
        CandleFacet candleFacet = chunkRegion.getFacet(CandleFacet.class);

        for (Map.Entry<BaseVector3i, Candle> entry : candleFacet.getWorldEntries().entrySet()) {
            Vector3i candlePosition = new Vector3i(entry.getKey()).addY(0);
            int heightAdd = ThreadLocalRandom.current().nextInt(0, 2);
            int height = entry.getValue().getCandleHeight() + heightAdd;
            int width = entry.getValue().getCandleWidth();
            int stickHeight = entry.getValue().getStickHeight() + heightAdd;
            int stickWidth = entry.getValue().getStickWidth();
            int flameHeight = entry.getValue().getFlameHeight() + heightAdd;

            Vector3i candleMinimumPos = new Vector3i(candlePosition).sub(0, 0, 0);

            Region3i candleRegion = Region3i.createFromMinAndSize(candleMinimumPos, new Vector3i(width, height, width));
            Region3i candleStick = Region3i.createFromMinAndSize(candlePosition, new Vector3i(1, stickHeight, stickWidth));
            Region3i candleFlame = Region3i.createFromMinAndSize(new Vector3i(candleMinimumPos).addY(stickHeight - 1),
                    new Vector3i(width, flameHeight, width));

            for (Vector3i newBlockPosition : candleRegion) {
                if (chunkRegion.getRegion().encompasses(newBlockPosition)) {
                    if (candleStick.encompasses(newBlockPosition)) {
                        chunk.setBlock(ChunkMath.calcBlockPos(newBlockPosition), candlePink);
                    } else if (!candleStick.encompasses(newBlockPosition)) {
                        if (candleFlame.encompasses(newBlockPosition) || candleFlame.encompasses(newBlockPosition)) {
                            chunk.setBlock(ChunkMath.calcBlockPos(newBlockPosition), lava);
                        }
                    }
                }
            }
        }
    }
}