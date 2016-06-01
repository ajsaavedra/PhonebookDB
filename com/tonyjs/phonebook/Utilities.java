package com.tonyjs.phonebook;


import java.awt.Color;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.Calendar;

import javax.swing.*;
import javax.swing.text.*;

public class Utilities
{
	public static JTextField getTextField(int theWidth, int theHeight, int theLocationX, int theLocationY)
	{
		JTextField toReturn = new JTextField();
		toReturn.setSize(theWidth, theHeight);
		toReturn.setLocation(theLocationX, theLocationY);
		return toReturn;
	}
	
	public static JFormattedTextField getFormattedTF(int theWidth, int theHeight, int theLocationX, 
			int theLocationY, String theMask, String theValidInput) throws ParseException
	{
		MaskFormatter formatter = new MaskFormatter(theMask);
		formatter.setValidCharacters(theValidInput);
		JFormattedTextField toReturn = new JFormattedTextField(formatter);
		toReturn.setSize(theWidth, theHeight);
		toReturn.setLocation(theLocationX, theLocationY);
		return toReturn;
	}

	public static JLabel getLabel(String theTitle, int theWidth, int theHeight, int theLocationX, 
			int theLocationY, Color theColor)
	{
		JLabel toReturn = new JLabel(theTitle);
		toReturn.setSize(theWidth, theHeight);
		toReturn.setLocation(theLocationX, theLocationY);
		toReturn.setForeground(theColor);
		return toReturn;
	}

	public static JButton getButton(String theTitle, int theWidth, int theHeight, int theLocationX, 
			int theLocationY, ActionListener theHandler)
	{
		JButton toReturn = new JButton(theTitle);
		toReturn.setSize(theWidth, theHeight);
		toReturn.setLocation(theLocationX, theLocationY);
		toReturn.addActionListener(theHandler);
		return toReturn;
	}
	
	public static String[] getAllMonths()
	{
		String[] monthsArray = {"January", "February", "March", "April", "May", "June",
				"July", "August", "September", "October", "November", "December"};
		return monthsArray;
	}
	
	public static Integer[] getYears() {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		int range = year - 1930;
		Integer[] years = new Integer[range + 1];
		for (int i = 0; i < years.length; i++) {
			years[i] = year--;
		}
		return years;
	}
	
	public static Integer[] getDays(int range) {
		Integer[] days = new Integer[range];
		for (int i = 0; i < range; i++) {
			days[i] = i+1;
		}
		return days;
	}
	
	public static String getAlphabet()
	{
		String lowerCase = "abcdefghijklmnopqrstuvwxyz";
		String upperCase = " ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String alphabet = lowerCase + upperCase;
		return alphabet;
	}
}
