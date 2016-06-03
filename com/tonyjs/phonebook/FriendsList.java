package com.tonyjs.phonebook;

import java.io.*;

public class FriendsList
{
	private int friendCount;
	private Friend head, tail;
	private boolean isAlphabetized, isSortedByAge;

	public FriendsList()
	{
		friendCount = 0;
		head = new Friend(null, null, tail);
		tail = new Friend(null, head, null);
		isAlphabetized = true;
		isSortedByAge = false;
	}
	
	public int getFriendCount()
	{
		return friendCount;
	}

	public String first()
	{
		return head.getNext().getFullName();
	}

	public boolean isStart(Friend friend)
	{
		return friend == head;
	}
	
	public boolean isAlphabetized()
	{
		return isAlphabetized;
	}
	
	public boolean isSortedByAge()
	{
		return isSortedByAge;
	}
	
	public void setAlphabetized(boolean value)
	{
		isAlphabetized = value;
		isSortedByAge = !value;
	}
	
	public Friend getFirstFriend()
	{
		return head.getNext();
	}
	
	public Friend getTailNode()
	{
		return tail;
	}


	public boolean addFriend(Friend newFriend)
	{
		if (friendCount == 0) {
			newFriend.setPrev(head);
			newFriend.setNext(tail);
			head.setNext(newFriend);
			tail.setPrev(newFriend);
		} else {
			Friend greaterNode = sortNewFriendByLastName(newFriend);
			newFriend.setPrev(greaterNode.getPrev());
			newFriend.setNext(greaterNode);
			greaterNode.getPrev().setNext(newFriend);
			greaterNode.setPrev(newFriend);
		}
		friendCount++;
		return true;
	}
	
	public void addFriendByAge(Friend newFriend)
	{
		if (friendCount == 0) {
			newFriend.setPrev(head);
			newFriend.setNext(tail);
			head.setNext(newFriend);
			tail.setPrev(newFriend);
		} else {
			Friend greaterNode = sortNewFriendByAge(newFriend);
			newFriend.setPrev(greaterNode.getPrev());
			newFriend.setNext(greaterNode);
			greaterNode.getPrev().setNext(newFriend);
			greaterNode.setPrev(newFriend);
		}
		friendCount++;
	}

	public boolean deleteFriend(String enemy)
	{
		if (friendCount > 0) {
			Friend pointer = head.getNext();
			while (pointer != tail) {
				if (pointer.getFullName().equals(enemy)) {
					pointer.getPrev().setNext(pointer.getNext());
					pointer.getNext().setPrev(pointer.getPrev());
					friendCount--;
					return true;
				}
				pointer = pointer.getNext();
			}
		}
		return false;
	}

	public FriendsList findFriend(String friend)
	{
		Friend pointer = head.getNext();
		FriendsList toReturn = new FriendsList();
		while (pointer != tail) {
			if (pointer.getFullName().equals(friend) ||
					pointer.getFirstName().equals(friend) ||
					pointer.getLastName().equals(friend)) {
				toReturn.addFriend(replicateFriend(pointer));
			}
			pointer = pointer.getNext();
		}
		return toReturn;
	}

	public boolean userExists(Friend friend)
	{
		if (friendCount < 1) {
			return false;
		}

		Friend pointer = head.getNext();

		while (pointer != tail ) {
			if (pointer.getFullName().equals(friend.getFullName())) {
				return true;
			}
			pointer = pointer.getNext();
		}
		return false;
	}

	public FriendsList sortFriendsAlphabetically()
	{
		Friend pointer = head.getNext();
		FriendsList toReplace = new FriendsList();
		
		while (pointer != tail) {
			Friend toInsert = replicateFriend(pointer);
			toReplace.addFriend(toInsert);
			pointer = pointer.getNext();
		}

		return toReplace;
	}

	public FriendsList sortFriendsByAge()
	{
		Friend pointer = head.getNext();
		FriendsList toReplace = new FriendsList();
		
		while (pointer != tail) {
			Friend toInsert = replicateFriend(pointer);
			toReplace.addFriendByAge(toInsert);
			pointer = pointer.getNext();
		}
		
		return toReplace;
	}

	public Friend replicateFriend(Friend toReplicate)
	{
		Friend toReturn = new Friend(toReplicate.getFirstName(), toReplicate.getLastName(),
				toReplicate.getStreetOne(), toReplicate.getStreetTwo(),
				toReplicate.getCity(), toReplicate.getState(), toReplicate.getZip(),
				toReplicate.getPhone(), toReplicate.getBirthDay(), toReplicate.getBirthMonth(),
				toReplicate.getBirthYear());
		return toReturn;
	}

	public Friend sortNewFriendByLastName(Friend newFriend)
	{
		Friend pointer = head.getNext();
		while (pointer != tail) {
			if (pointer.getLastName().compareTo(newFriend.getLastName()) > 0 ) {
				return pointer;
			}
			pointer = pointer.getNext();
		}
		return pointer;
	}

	public Friend sortNewFriendByAge(Friend newFriend)
	{
		Friend pointer = head.getNext();
		while (pointer != tail) {
			if (pointer.getBirthYear() > newFriend.getBirthYear()) {
				return pointer;
			}
			pointer = pointer.getNext();
		}
		return pointer;
	}

	public String listAllFriends()
	{
		String listOfFriends = "";
		if (friendCount == 0) {
			listOfFriends = "No friends found<br>";
		} else {
			Friend pointer = head.getNext();
			while (pointer != tail) {
				listOfFriends += pointer.getFullName() + "<br>";
				listOfFriends += pointer.getAddress() + "<br>";
				listOfFriends += pointer.getBirthday() + "<br>";
				listOfFriends += pointer.getPhone() + "<br><br>\n";
				pointer = pointer.getNext();
			}
		}
		return listOfFriends;
	}

	public String[] addAll()
	{
		String[] friendsList = new String[friendCount];
		Friend pointer = head;
		int i = 0;
		while (pointer.getNext() != tail) {
			friendsList[i] = pointer.getNext().getFullName();
			pointer = pointer.getNext();
			i++;
		}
		return friendsList;
	}

	public String saveToFile(String fileName)
	{
		String messageFromSave = "";
		try {
			ObjectOutputStream oOS = new ObjectOutputStream(new FileOutputStream(fileName));
			Friend pointer = head.getNext();
			while (pointer != tail) {
				oOS.writeObject(pointer);
				pointer = pointer.getNext();
			}
			oOS.flush();
			oOS.close();
		} catch (Exception e) {
			messageFromSave = e.toString();
		}
		return messageFromSave;
	}

	public String loadFromFile(String fileName)
	{
		String toReturn = "";
		try {
			@SuppressWarnings("resource")
			ObjectInputStream oIS = new ObjectInputStream(new FileInputStream(fileName));
			while (true) {
				Friend fromFile = (Friend)(oIS.readObject());
				if (userExists(fromFile)) {
					System.out.println(fromFile.getFullName() + " added to DB");
					toReturn += fromFile.getFullName() + " already in DB.<br>Record not added from file!<br>";
				} else {
					if (addFriend(fromFile)) {
						toReturn += fromFile.getFullName() + " successfully added to DB.<br>";
					} else {
						toReturn += fromFile.getFullName() + " not successfully added to DB.<br>";
					}
				}
			}
		} catch (EOFException eOF) {
		} catch (Exception e) {
			toReturn += e;
		}
		return toReturn;
	}
}
