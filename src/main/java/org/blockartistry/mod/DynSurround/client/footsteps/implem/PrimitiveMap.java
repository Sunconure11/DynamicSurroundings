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

package org.blockartistry.mod.DynSurround.client.footsteps.implem;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.blockartistry.mod.DynSurround.ModLog;
import org.blockartistry.mod.DynSurround.client.footsteps.interfaces.IAcoustic;
import org.blockartistry.mod.DynSurround.client.footsteps.system.Isolator;
import org.blockartistry.mod.DynSurround.client.footsteps.util.property.simple.ConfigProperty;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class PrimitiveMap {

	private final Isolator isolator;
	private final Map<String, List<IAcoustic>> primitiveMap = new LinkedHashMap<String, List<IAcoustic>>();

	public PrimitiveMap(@Nonnull final Isolator isolator) {
		this.isolator = isolator;
	}

	@Nullable
	public List<IAcoustic> getPrimitiveMap(@Nonnull final String primitive) {
		return this.primitiveMap.get(primitive);
	}

	@Nullable
	public List<IAcoustic> getPrimitiveMapSubstrate(@Nonnull final String primitive, @Nonnull final String substrate) {
		return this.primitiveMap.get(primitive + "@" + substrate);
	}

	public void register(@Nonnull final String key, @Nonnull final String value) {
		this.primitiveMap.put(key, this.isolator.getAcoustics().compileAcoustics(value));
	}

	public void setup(@Nonnull final ConfigProperty props) {
		final Map<String, String> properties = props.getAllProperties();
		for (final Entry<String, String> entry : properties.entrySet()) {
			try {
				register(entry.getKey(), entry.getValue());
			} catch (final Exception e) {
				ModLog.info("Error making registration " + entry.getKey() + ": " + e.getMessage());
			}
		}
	}

}