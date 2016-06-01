package com.tonyjs.phonebook;

import java.awt.Color;

public class ColorModel
{
	private static Color[] colors = new Color[]{
			new Color(244, 164, 189),
			new Color(18, 151, 147),
			new Color(61, 131, 243),
			new Color(244, 164, 189),
			new Color(237, 38, 54),
			new Color(105, 94, 133),
			new Color(239, 130, 100),
			new Color(85, 176, 112),
	};
	
	public static Color getRandomColor()
	{
		return colors[(int) (Math.random() * colors.length)];
	}
}