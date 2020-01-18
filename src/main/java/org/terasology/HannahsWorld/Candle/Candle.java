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
package org.terasology.HannahsWorld.Candle;

public class Candle {
    public int getCandleHeight() {
        return getStickHeight() + getFlameHeight();
    }

    public int getCandleWidth() {
        return 1;
    }

    public int getStickHeight() {
        return 10;
    }

    public int getStickWidth() {
        return 1;
    }

    public int getFlameHeight() {
        return 3;
    }
}