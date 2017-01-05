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

package org.blockartistry.mod.DynSurround.client.handlers.scanners;

import java.util.List;
import java.util.Random;

import javax.annotation.Nonnull;

import org.blockartistry.mod.DynSurround.client.fx.BlockEffect;
import org.blockartistry.mod.DynSurround.client.handlers.EnvironStateHandler.EnvironState;
import org.blockartistry.mod.DynSurround.client.sound.SoundEffect;
import org.blockartistry.mod.DynSurround.registry.BlockRegistry;
import org.blockartistry.mod.DynSurround.registry.RegistryManager;
import org.blockartistry.mod.DynSurround.registry.RegistryManager.RegistryType;
import org.blockartistry.mod.DynSurround.scanner.RandomScanner;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RandomBlockEffectScanner extends RandomScanner {

	private static final double RATIO = 0.0335671847202175D;

	protected final Random rand = RANDOM.get();
	protected final BlockRegistry blocks = RegistryManager.get(RegistryType.BLOCK);

	public RandomBlockEffectScanner(final int range) {
		super("RandomBlockEffectScanner", range, (int) (Math.pow(range * 2 - 1, 3) * RATIO));
	}
	
	@Override
	protected boolean interestingBlock(@Nonnull final IBlockState state) {
		return this.blocks.hasProfile(state);
	}

	@Override
	public void blockScan(@Nonnull final IBlockState state, @Nonnull final BlockPos pos) {

		final List<BlockEffect> chain = this.blocks.findEffectMatches(state);

		if (chain != null && !chain.isEmpty()) {
			final World world = EnvironState.getWorld();
			for (final BlockEffect effect : chain)
				effect.process(state, world, pos, this.rand);
		}

		final SoundEffect sound = this.blocks.getSound(state, this.rand);
		if (sound != null) {
			final World world = EnvironState.getWorld();
			sound.doEffect(state, world, pos, SoundCategory.BLOCKS, this.rand);
		}

	}

}
