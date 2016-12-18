/*
 * This file is part of Dynamic Surroundings, licensed under the MIT License (MIT).
 *
 * Copyright (c) OreCruncher
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package org.blockartistry.mod.DynSurround.server.services;

import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;

import org.blockartistry.mod.DynSurround.data.DimensionEffectData;
import org.blockartistry.mod.DynSurround.network.Network;
import org.blockartistry.mod.DynSurround.registry.DimensionRegistry;

public final class AtmosphereService {

	private static final float RESET = -10.0F;

	public static void initialize() {
		MinecraftForge.EVENT_BUS.register(new AtmosphereService());
	}

	@SubscribeEvent
	public void tickEvent(final TickEvent.WorldTickEvent event) {

		if (event.phase == Phase.END)
			return;

		final World world = event.world;
		final int dimensionId = world.provider.getDimension();
		final float sendIntensity = DimensionRegistry.hasWeather(world) ? DimensionEffectData.get(world).getRainIntensity()
				: RESET;

		// Set the RAIN intensity for all players in the current
		// dimension.
		Network.sendRainIntensity(sendIntensity, dimensionId);
	}

}