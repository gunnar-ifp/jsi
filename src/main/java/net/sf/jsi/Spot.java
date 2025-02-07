//   Point.java
//   Java Spatial Index Library
//   Copyright (C) 2002-2005 Infomatiq Limited.
//
//  This library is free software; you can redistribute it and/or
//  modify it under the terms of the GNU Lesser General Public
//  License as published by the Free Software Foundation; either
//  version 2.1 of the License, or (at your option) any later version.
//
//  This library is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
//  Lesser General Public License for more details.
//
//  You should have received a copy of the GNU Lesser General Public
//  License along with this library; if not, write to the Free Software
//  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA

package net.sf.jsi;

/**
 * Currently hardcoded to 2 dimensions, but could be extended.
 */
public class Spot
{
	/**
	 * The (x, y) coordinates of the spot.
	 */
	public float x, y;


	/**
	 * Constructor.
	 *
	 * @param x The x coordinate of the spot
	 * @param y The y coordinate of the spot
	 */
	public Spot(float x, float y)
	{
		this.x = x;
		this.y = y;
	}


	/**
	 * Copy from another spot into this one
	 */
	public void set(Spot other)
	{
		x = other.x;
		y = other.y;
	}


	/**
	 * Print as a string in format "(x, y)"
	 */
	@Override
	public String toString()
	{
		return "(" + x + ", " + y + ")";
	}


	/**
	 * @return X coordinate rounded to an int
	 */
	public int xInt()
	{
		return Math.round(x);
	}


	/**
	 * @return Y coordinate rounded to an int
	 */
	public int yInt()
	{
		return Math.round(y);
	}
}
