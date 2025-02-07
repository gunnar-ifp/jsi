package net.sf.jsi;

import junit.framework.TestCase;

import net.sf.jsi.Area;


public class RectangleMaxValueTest extends TestCase
{
	public RectangleMaxValueTest(String name)
	{
		super(name);
	}


	public void testMaxValue()
	{
		RTree rTree = new RTree();
		rTree.add(new Area(8.0f, 6.0f, Float.MAX_VALUE, Float.MAX_VALUE), 1);
		rTree.add(new Area(1.0f, 5.0f, Float.MAX_VALUE, Float.MAX_VALUE), 2);
		rTree.add(new Area(10.0f, 6.0f, Float.MAX_VALUE, Float.MAX_VALUE), 3);
		rTree.add(new Area(5.0f, 8.0f, Float.MAX_VALUE, Float.MAX_VALUE), 4);
		rTree.add(new Area(6.0f, 1.0f, Float.MAX_VALUE, Float.MAX_VALUE), 6);
		rTree.add(new Area(3.0f, 1.0f, Float.MAX_VALUE, Float.MAX_VALUE), 7);
		rTree.add(new Area(9.0f, 8.0f, Float.MAX_VALUE, Float.MAX_VALUE), 8);
		rTree.add(new Area(5.0f, 7.0f, Float.MAX_VALUE, Float.MAX_VALUE), 9);
		rTree.add(new Area(2.0f, 5.0f, Float.MAX_VALUE, Float.MAX_VALUE), 10);
		rTree.add(new Area(2.0f, 2.0f, Float.MAX_VALUE, Float.MAX_VALUE), 11);
		rTree.add(new Area(5.0f, 3.0f, Float.MAX_VALUE, Float.MAX_VALUE), 14);
		rTree.add(new Area(7.0f, 3.0f, Float.MAX_VALUE, Float.MAX_VALUE), 15);
		rTree.add(new Area(6.0f, 3.0f, Float.MAX_VALUE, Float.MAX_VALUE), 16);
		rTree.delete(new Area(9.0f, 8.0f, Float.MAX_VALUE, Float.MAX_VALUE), 8);
		rTree.add(new Area(7.0f, 8.0f, Float.MAX_VALUE, Float.MAX_VALUE), 17);
		rTree.add(new Area(3.0f, 5.0f, Float.MAX_VALUE, Float.MAX_VALUE), 18);
		rTree.delete(new Area(5.0f, 7.0f, Float.MAX_VALUE, Float.MAX_VALUE), 9);
		rTree.add(new Area(4.0f, 7.0f, Float.MAX_VALUE, Float.MAX_VALUE), 19);
		rTree.delete(new Area(2.0f, 5.0f, Float.MAX_VALUE, Float.MAX_VALUE), 10);
		rTree.add(new Area(2.0f, 1.0f, Float.MAX_VALUE, Float.MAX_VALUE), 20);
		rTree.add(new Area(9.0f, 6.0f, Float.MAX_VALUE, Float.MAX_VALUE), 21);
		rTree.add(new Area(7.0f, 6.0f, Float.MAX_VALUE, Float.MAX_VALUE), 22);
		rTree.delete(new Area(10.0f, 6.0f, Float.MAX_VALUE, Float.MAX_VALUE), 3);
		rTree.add(new Area(3.0f, 4.0f, Float.MAX_VALUE, Float.MAX_VALUE), 23);
		rTree.delete(new Area(3.0f, 1.0f, Float.MAX_VALUE, Float.MAX_VALUE), 7);
		rTree.add(new Area(3.0f, 1.0f, Float.MAX_VALUE, Float.MAX_VALUE), 24);
		rTree.delete(new Area(8.0f, 6.0f, Float.MAX_VALUE, Float.MAX_VALUE), 1);
		rTree.add(new Area(3.0f, 6.0f, Float.MAX_VALUE, Float.MAX_VALUE), 25);
		rTree.delete(new Area(7.0f, 8.0f, Float.MAX_VALUE, Float.MAX_VALUE), 17);
		rTree.add(new Area(7.0f, 8.0f, Float.MAX_VALUE, Float.MAX_VALUE), 26);
		rTree.delete(new Area(2.0f, 1.0f, Float.MAX_VALUE, Float.MAX_VALUE), 20);
		rTree.add(new Area(0.0f, 1.0f, Float.MAX_VALUE, Float.MAX_VALUE), 27);
		rTree.delete(new Area(2.0f, 2.0f, Float.MAX_VALUE, Float.MAX_VALUE), 11);
		rTree.add(new Area(2.0f, 2.0f, Float.MAX_VALUE, Float.MAX_VALUE), 28);
		rTree.delete(new Area(5.0f, 8.0f, Float.MAX_VALUE, Float.MAX_VALUE), 4);
		rTree.add(new Area(4.0f, 2.0f, Float.MAX_VALUE, Float.MAX_VALUE), 29);
		rTree.delete(new Area(5.0f, 3.0f, Float.MAX_VALUE, Float.MAX_VALUE), 14);
		rTree.add(new Area(5.0f, 3.0f, Float.MAX_VALUE, Float.MAX_VALUE), 30);
		rTree.add(new Area(7.0f, 5.0f, Float.MAX_VALUE, Float.MAX_VALUE), 31);
		rTree.delete(new Area(7.0f, 5.0f, Float.MAX_VALUE, Float.MAX_VALUE), 31);
		rTree.add(new Area(7.0f, 5.0f, Float.MAX_VALUE, Float.MAX_VALUE), 32);
		rTree.add(new Area(5.0f, 8.0f, Float.MAX_VALUE, Float.MAX_VALUE), 33);
		rTree.delete(new Area(2.0f, 2.0f, Float.MAX_VALUE, Float.MAX_VALUE), 28);
		rTree.add(new Area(1.0f, 2.0f, Float.MAX_VALUE, Float.MAX_VALUE), 34);
		rTree.delete(new Area(0.0f, 1.0f, Float.MAX_VALUE, Float.MAX_VALUE), 27);
		rTree.add(new Area(0.0f, 1.0f, Float.MAX_VALUE, Float.MAX_VALUE), 35);
		rTree.add(new Area(8.0f, 5.0f, Float.MAX_VALUE, Float.MAX_VALUE), 36);
		rTree.delete(new Area(3.0f, 5.0f, Float.MAX_VALUE, Float.MAX_VALUE), 18);
		rTree.add(new Area(3.0f, 5.0f, Float.MAX_VALUE, Float.MAX_VALUE), 37);
		rTree.add(new Area(8.0f, 7.0f, Float.MAX_VALUE, Float.MAX_VALUE), 39);
		rTree.delete(new Area(7.0f, 6.0f, Float.MAX_VALUE, Float.MAX_VALUE), 22);
		rTree.add(new Area(5.0f, 2.0f, Float.MAX_VALUE, Float.MAX_VALUE), 40);
		rTree.delete(new Area(3.0f, 1.0f, Float.MAX_VALUE, Float.MAX_VALUE), 24);
		rTree.add(new Area(3.0f, 0.0f, Float.MAX_VALUE, Float.MAX_VALUE), 41);
		rTree.delete(new Area(7.0f, 8.0f, Float.MAX_VALUE, Float.MAX_VALUE), 26);
		rTree.add(new Area(7.0f, 5.0f, Float.MAX_VALUE, Float.MAX_VALUE), 42);
		rTree.delete(new Area(7.0f, 3.0f, Float.MAX_VALUE, Float.MAX_VALUE), 15);
		rTree.add(new Area(3.0f, 2.0f, Float.MAX_VALUE, Float.MAX_VALUE), 43);
		rTree.delete(new Area(4.0f, 2.0f, Float.MAX_VALUE, Float.MAX_VALUE), 29);
		rTree.add(new Area(4.0f, 2.0f, Float.MAX_VALUE, Float.MAX_VALUE), 44);
		rTree.delete(new Area(3.0f, 6.0f, Float.MAX_VALUE, Float.MAX_VALUE), 25);
		rTree.add(new Area(3.0f, 5.0f, Float.MAX_VALUE, Float.MAX_VALUE), 45);
		rTree.delete(new Area(3.0f, 2.0f, Float.MAX_VALUE, Float.MAX_VALUE), 43);
		rTree.add(new Area(1.0f, 0.0f, Float.MAX_VALUE, Float.MAX_VALUE), 46);
		rTree.delete(new Area(1.0f, 5.0f, Float.MAX_VALUE, Float.MAX_VALUE), 2);
		rTree.add(new Area(1.0f, 5.0f, Float.MAX_VALUE, Float.MAX_VALUE), 47);
		rTree.delete(new Area(9.0f, 6.0f, Float.MAX_VALUE, Float.MAX_VALUE), 21);
		rTree.add(new Area(7.0f, 6.0f, Float.MAX_VALUE, Float.MAX_VALUE), 48);
		rTree.delete(new Area(4.0f, 7.0f, Float.MAX_VALUE, Float.MAX_VALUE), 19);
		rTree.add(new Area(4.0f, 0.0f, Float.MAX_VALUE, Float.MAX_VALUE), 49);
		rTree.delete(new Area(5.0f, 3.0f, Float.MAX_VALUE, Float.MAX_VALUE), 30);
		rTree.add(new Area(0.0f, 2.0f, Float.MAX_VALUE, Float.MAX_VALUE), 50);
		rTree.delete(new Area(5.0f, 8.0f, Float.MAX_VALUE, Float.MAX_VALUE), 33);
		rTree.add(new Area(4.0f, 8.0f, Float.MAX_VALUE, Float.MAX_VALUE), 51);
		rTree.delete(new Area(3.0f, 5.0f, Float.MAX_VALUE, Float.MAX_VALUE), 45);
		rTree.add(new Area(3.0f, 5.0f, Float.MAX_VALUE, Float.MAX_VALUE), 52);
		rTree.delete(new Area(7.0f, 5.0f, Float.MAX_VALUE, Float.MAX_VALUE), 42);
		rTree.add(new Area(7.0f, 5.0f, Float.MAX_VALUE, Float.MAX_VALUE), 53);
		rTree.delete(new Area(5.0f, 2.0f, Float.MAX_VALUE, Float.MAX_VALUE), 40);
		rTree.add(new Area(3.0f, 2.0f, Float.MAX_VALUE, Float.MAX_VALUE), 54);
		rTree.delete(new Area(6.0f, 1.0f, Float.MAX_VALUE, Float.MAX_VALUE), 6);
		rTree.add(new Area(6.0f, 1.0f, Float.MAX_VALUE, Float.MAX_VALUE), 55);
		rTree.delete(new Area(4.0f, 8.0f, Float.MAX_VALUE, Float.MAX_VALUE), 51);
		rTree.add(new Area(4.0f, 8.0f, Float.MAX_VALUE, Float.MAX_VALUE), 56);
		rTree.delete(new Area(1.0f, 5.0f, Float.MAX_VALUE, Float.MAX_VALUE), 47);
		rTree.add(new Area(1.0f, 5.0f, Float.MAX_VALUE, Float.MAX_VALUE), 57);
		rTree.delete(new Area(0.0f, 2.0f, Float.MAX_VALUE, Float.MAX_VALUE), 50);
		rTree.add(new Area(0.0f, 2.0f, Float.MAX_VALUE, Float.MAX_VALUE), 58);
		rTree.delete(new Area(3.0f, 0.0f, Float.MAX_VALUE, Float.MAX_VALUE), 41);
		rTree.add(new Area(3.0f, 0.0f, Float.MAX_VALUE, Float.MAX_VALUE), 59);
		rTree.delete(new Area(7.0f, 5.0f, Float.MAX_VALUE, Float.MAX_VALUE), 53);
		rTree.add(new Area(0.0f, 5.0f, Float.MAX_VALUE, Float.MAX_VALUE), 60);
		rTree.delete(new Area(6.0f, 1.0f, Float.MAX_VALUE, Float.MAX_VALUE), 55);
		rTree.add(new Area(2.0f, 1.0f, Float.MAX_VALUE, Float.MAX_VALUE), 61);
		rTree.delete(new Area(7.0f, 5.0f, Float.MAX_VALUE, Float.MAX_VALUE), 32);
		rTree.add(new Area(0.0f, 5.0f, Float.MAX_VALUE, Float.MAX_VALUE), 62);
		rTree.delete(new Area(0.0f, 2.0f, Float.MAX_VALUE, Float.MAX_VALUE), 58);
		rTree.add(new Area(0.0f, 2.0f, Float.MAX_VALUE, Float.MAX_VALUE), 63);
		rTree.delete(new Area(3.0f, 4.0f, Float.MAX_VALUE, Float.MAX_VALUE), 23);
		rTree.add(new Area(3.0f, 3.0f, Float.MAX_VALUE, Float.MAX_VALUE), 64);
		rTree.delete(new Area(8.0f, 7.0f, Float.MAX_VALUE, Float.MAX_VALUE), 39);
	}
}
