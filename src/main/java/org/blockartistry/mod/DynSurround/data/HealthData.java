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

package org.blockartistry.mod.DynSurround.data;

import java.util.UUID;

import net.minecraft.entity.Entity;

public final class HealthData {
	public final UUID entityId;
	public final float posX;
	public final float posY;
	public final float posZ;
	public final boolean isCritical;
	public final int amount;

	public HealthData(final Entity entity, final boolean isCritical, final int amount) {
		this.entityId = entity.getUniqueID();
		this.posX = (float) entity.posX;
		this.posY = (float) entity.posY + entity.height;
		this.posZ = (float) entity.posZ;
		this.isCritical = isCritical;
		this.amount = amount;
	}

	public HealthData(final UUID id, final float x, final float y, final float z, final boolean isCritical,
			final int amount) {
		this.entityId = id;
		this.posX = x;
		this.posY = y;
		this.posZ = z;
		this.isCritical = isCritical;
		this.amount = amount;
	}
}