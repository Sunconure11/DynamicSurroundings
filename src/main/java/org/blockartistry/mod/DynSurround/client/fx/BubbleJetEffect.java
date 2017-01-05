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

package org.blockartistry.mod.DynSurround.client.fx;

import java.util.Random;

import javax.annotation.Nonnull;

import org.blockartistry.mod.DynSurround.api.effects.BlockEffectType;
import org.blockartistry.mod.DynSurround.client.fx.particle.ParticleBubbleJet;
import org.blockartistry.mod.DynSurround.client.fx.particle.ParticleJet;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class BubbleJetEffect extends JetEffect {

	public BubbleJetEffect(final int chance) {
		super(chance);
	}

	@Override
	@Nonnull
	public BlockEffectType getEffectType() {
		return BlockEffectType.BUBBLE_JET;
	}

	@Override
	public boolean trigger(final IBlockState state, final World world, final BlockPos pos, final Random random) {
		return world.getBlockState(pos.down()).getMaterial().isSolid() && super.trigger(state, world, pos, random);
	}

	@Override
	public void doEffect(final IBlockState state, final World world, final BlockPos pos, final Random random) {
		final int waterBlocks = countBlocks(world, pos, state, 1);
		final ParticleJet effect = new ParticleBubbleJet(waterBlocks, world, pos.getX() + 0.5D, pos.getY() + 0.1D,
				pos.getZ() + 0.5D);
		addEffect(effect);
	}

}
