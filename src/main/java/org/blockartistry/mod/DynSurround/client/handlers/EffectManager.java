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

package org.blockartistry.mod.DynSurround.client.handlers;

import java.util.ArrayList;
import java.util.List;

import org.blockartistry.mod.DynSurround.ModLog;
import org.blockartistry.mod.DynSurround.ModOptions;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.relauncher.Side;

@SideOnly(Side.CLIENT)
public class EffectManager {

	private static final List<EffectHandlerBase> effectHandlers = new ArrayList<EffectHandlerBase>();

	public static void register(final EffectHandlerBase handler) {
		effectHandlers.add(handler);
		if (handler.hasEvents()) {
			MinecraftForge.EVENT_BUS.register(handler);
		}
	}

	private EffectManager() {
	}

	public static void initialize() {
		final EffectManager handler = new EffectManager();
		MinecraftForge.EVENT_BUS.register(handler);

		register(new EnvironStateHandler());
		register(new AreaSurveyHandler());
		register(new FogEffectHandler());
		register(new BlockEffectHandler());

		if (ModOptions.blockedSounds.length > 0 || ModOptions.culledSounds.length > 0)
			register(new SoundCullHandler());

		if (ModOptions.enableFootstepSounds)
			register(new FootstepsHandler());

		if (ModOptions.auroraEnable)
			register(new AuroraEffectHandler());

		if (ModOptions.enableBiomeSounds)
			register(new AreaSoundEffectHandler());

		if (ModOptions.suppressPotionParticles)
			register(new PotionParticleScrubHandler());
		
		if (ModOptions.enableDamagePopoffs)
			register(new PopoffEffectHandler());

		if(ModOptions.enableSpeechBubbles)
			register(new SpeechBubbleHandler());
		
		ModLog.info("Registered client handlers:");
		for(final EffectHandlerBase h: effectHandlers) {
			ModLog.info("* %s", h.getHandlerName());
		}
	}
	
	@SubscribeEvent
	public void clientTick(final TickEvent.ClientTickEvent event) {
		if (Minecraft.getMinecraft().isGamePaused())
			return;

		final World world = FMLClientHandler.instance().getClient().theWorld;
		if (world == null)
			return;

		if (event.phase == Phase.START) {
			final EntityPlayer player = FMLClientHandler.instance().getClient().thePlayer;
			for (final EffectHandlerBase handler : effectHandlers)
				handler.process(world, player);
		}
	}
}