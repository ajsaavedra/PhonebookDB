package com.tonyjs.phonebook;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

@SuppressWarnings({ "serial", "rawtypes" })
public class DaysComboBoxModel extends AbstractListModel implements ComboBoxModel
{
	Integer[] days;
	int selection = 1;

	public DaysComboBoxModel(int dayRange)
	{
		days = Utilities.getDays(dayRange);
	}
	
	@Override
	public int getSize()
	{
		return days.length;
	}

	@Override
	public Object getElementAt(int index)
	{
		return days[index];
	}

	@Override
	public void setSelectedItem(Object anItem)
	{
		selection = (Integer) anItem;
	}

	@Override
	public Object getSelectedItem()
	{
		return selection;
	}

}
