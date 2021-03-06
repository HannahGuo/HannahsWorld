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

import org.terasology.HannahsWorld.Candle.CandleProvider;
import org.terasology.HannahsWorld.Candle.CandleRasterizer;
import org.terasology.HannahsWorld.CandyDecor.CandyDecorProvider;
import org.terasology.HannahsWorld.CandyDecor.CandyDecorRasterizer;
import org.terasology.engine.SimpleUri;
import org.terasology.registry.In;
import org.terasology.world.generation.BaseFacetedWorldGenerator;
import org.terasology.world.generation.WorldBuilder;
import org.terasology.world.generator.RegisterWorldGenerator;
import org.terasology.world.generator.plugin.WorldGeneratorPluginLibrary;

@RegisterWorldGenerator(id = "HannahsWorld", displayName = "Hannahs World")
public class HannahsWorldGenerator extends BaseFacetedWorldGenerator {
    public HannahsWorldGenerator(SimpleUri uri) {
        super(uri);
    }

    @In
    private WorldGeneratorPluginLibrary worldGeneratorPluginLibrary;
    @Override
    protected WorldBuilder createWorld() {
        return new WorldBuilder(worldGeneratorPluginLibrary)
                .addProvider(new SurfaceProvider())
                .addProvider(new CandleProvider())
                .addProvider(new CandyDecorProvider())
                .addRasterizer(new HannahWorldRasterizer())
                .addRasterizer(new CandleRasterizer())
                .addRasterizer(new CandyDecorRasterizer());
    }
}