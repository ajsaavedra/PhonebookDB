package com.tonyjs.phonebook;

@SuppressWarnings("serial")
public class Friend implements java.io.Serializable
{
	private String firstName, lastName, addressLine1, addressLine2, city, state, phone, birthMonth;
	private int birthDay, birthYear, zipcode;
	private Friend next, prev;

	public Friend(String firstName, String lastName, String addressLine1, String addressLine2,
			String city, String state, int zipcode, String phone, int birthDay, String birthMonth, int birthYear)
	{
		this.firstName = firstName;
		this.lastName = lastName;
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.city = city;
		this.state = state;
		this.zipcode = zipcode;
		this.phone = phone;
		this.birthMonth = birthMonth;
		this.birthDay = birthDay;
		this.birthYear = birthYear;
	}

	public Friend(String firstName)
	{
		this.firstName = firstName;
		this.prev = null;
		this.next = null;
	}
	
	public Friend(String firstName, String lastName)
	{
		this.firstName = firstName;
		this.lastName = lastName;
		this.prev = null;
		this.next = null;
	}

	public Friend(String name, Friend prev, Friend next)
	{
		this.firstName = name;
		this.prev = prev;
		this.next = next;
	}

	public void setFirstName(String newName)
	{
		this.firstName = newName;
	}

	public void setZipcode(int newZip)
	{
		this.zipcode = newZip;
	}

	public String getFullName()
	{
		return firstName + " " + lastName;
	}
	
	public String getLastName()
	{
		return lastName;
	}
	
	public String getFirstName()
	{
		return firstName;
	}

	public int getZip()
	{
		return zipcode;
	}

	public void setNext(Friend next)
	{
		this.next = next;
	}

	public void setPrev(Friend prev)
	{
		this.prev = prev;
	}

	public Friend getNext()
	{
		return next;
	}

	public Friend getPrev()
	{
		return prev;
	}
	
	public String getStreetOne()
	{
		return addressLine1;
	}
	
	public String getStreetTwo()
	{
		return addressLine2;
	}
	
	public String getState()
	{
		return state;
	}
	
	public String getCity()
	{
		return city;
	}
	
	public String getAddress()
	{
		if (getStreetTwo().equals("")) {
			return getStreetOne() + "<br>" +
					getCity() + ", " + getState() + " " + getZip() ;
		} else {
			return getStreetOne() + "<br>" + getStreetTwo() + "<br>" +
					getCity() + ", " + getState() + " "+ getZip() ;
		}
	}
	
	public String getBirthday()
	{
		return birthMonth + " " + birthDay + ", " + birthYear;
	}
	
	public int getBirthDay()
	{
		return birthDay;
	}
	
	public String getBirthMonth()
	{
		return birthMonth;
	}
	
	public int getBirthYear()
	{
		return birthYear;
	}
	
	public String getPhone()
	{
		return phone;
	}
}
