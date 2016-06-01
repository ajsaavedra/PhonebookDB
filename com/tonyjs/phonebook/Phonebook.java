package com.tonyjs.phonebook;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.text.ParseException;

@SuppressWarnings({"serial", "unchecked", "unused"})
public class Phonebook extends JFrame
{
	private JTextField streetLineOneTF, streetLineTwoTF, cityTF, fileNameTF;
	private JLabel titleLabel, firstNameLabel, lastNameLabel, streetOneLabel, optionalLabel,
		cityLabel, stateLabel, zipcodeLabel, phoneLabel, birthDayLabel, birthMonthLabel, birthYearLabel;
	private JButton addB, deleteB, sortAlphaB, sortByDateB, findB, saveB, updateB,
		loadB, displayB, cancelB, proceedB;
	private JFormattedTextField phoneNumberTF, firstNameTF, lastNameTF, zipcodeTF;
	private JEditorPane displayTA;
	private JScrollPane displaySP;
	private JComboBox<String> statesCB, birthMonthCB;
	private JComboBox<Integer> yearsCB, daysCB;
	private String[] states, months;
	private Integer[] years;
	private String fileName;
	private FriendsList friends;
	private Friend foundFriend;
	private boolean processingSave, processingDelete, duplicateRecord;
	private Container container;

	public Phonebook()
	{
		super("\u260E");
		setSize(900, 670);
		container = getContentPane();
		container.setLayout(null);
		container.setBackground(ColorModel.getRandomColor());
		setLocationRelativeTo(null);

		titleLabel = Utilities.getLabel("Phonebook DB", 600, 38, 320, 20, Color.white);
		titleLabel.setFont(new Font("Serif", Font.PLAIN, 40));
		container.add(titleLabel);

		firstNameLabel = Utilities.getLabel("First Name:", 200, 20, 20, 50, Color.white);
		container.add(firstNameLabel);
		
		try {
			firstNameTF = Utilities.getFormattedTF(250, 20, 15, 70, "**************", Utilities.getAlphabet());
			lastNameTF = Utilities.getFormattedTF(250, 20, 15, 120, "**************", Utilities.getAlphabet());
			zipcodeTF = Utilities.getFormattedTF(250, 20, 15, 370, "*****", "0123456789");
			phoneNumberTF = Utilities.getFormattedTF(110, 20, 15, 420, "'(###')###-####", "0123456789");
		} catch (ParseException e2) {
			e2.printStackTrace();
		}
		
		container.add(firstNameTF);
		container.add(lastNameTF);
		container.add(zipcodeTF);
		container.add(phoneNumberTF);

		lastNameLabel = Utilities.getLabel("Last Name:", 200, 20, 20, 100, Color.white);
		container.add(lastNameLabel);

		streetOneLabel = Utilities.getLabel("Street:", 50, 20, 20, 150, Color.white);
		container.add(streetOneLabel);

		streetLineOneTF = Utilities.getTextField(250, 20, 15, 170);
		container.add(streetLineOneTF);

		optionalLabel = Utilities.getLabel("Street 2 (optional):", 200, 20, 20, 200, Color.white);
		container.add(optionalLabel);

		streetLineTwoTF = Utilities.getTextField(250, 20, 15, 220);
		container.add(streetLineTwoTF);

		cityLabel = Utilities.getLabel("City:", 50, 20, 20, 250, Color.white);
		container.add(cityLabel);

		cityTF = Utilities.getTextField(250, 20, 15, 270);
		container.add(cityTF);

		stateLabel = Utilities.getLabel("State:", 60, 20, 20, 300, Color.white);
		container.add(stateLabel);

		states = StatesModel.getAllStates();
		statesCB = new JComboBox<String>(states);
		statesCB.setSize(250, 20);
		statesCB.setLocation(15, 320);
		container.add(statesCB);

		zipcodeLabel = Utilities.getLabel("Zipcode:", 60, 20, 20, 350, Color.white);
		container.add(zipcodeLabel);

		phoneLabel = Utilities.getLabel("Phone Number:", 100, 20, 20, 400, Color.white);
		container.add(phoneLabel);

		birthMonthLabel = Utilities.getLabel("Birth Month:", 100, 20, 20, 450, Color.white);
		container.add(birthMonthLabel);

		birthDayLabel = Utilities.getLabel("Birth Day:", 100, 20, 20, 500, Color.white);
		container.add(birthDayLabel);

		months = Utilities.getAllMonths();
		birthMonthCB = new JComboBox<String>(months);
		birthMonthCB.setSize(250, 20);
		birthMonthCB.setLocation(15, 470);
		
		
		birthMonthCB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selection = (String) birthMonthCB.getSelectedItem();
				if (selection == "April" || selection == "June" || selection == "September" || selection == "November") {
					daysCB.setModel(new DaysComboBoxModel(30));
				} else if (selection == "February") {
					daysCB.setModel(new DaysComboBoxModel(29));
				} else {
					daysCB.setModel(new DaysComboBoxModel(31));
				}
			}
		});
		container.add(birthMonthCB);

		daysCB = new JComboBox<>(new DaysComboBoxModel(31));
		daysCB.setSize(250, 20);
		daysCB.setLocation(15, 520);
		container.add(daysCB);
		
		birthYearLabel = Utilities.getLabel("Birth Year:", 100, 20, 20, 550, Color.white);
		container.add(birthYearLabel);

		years = Utilities.getYears();
		yearsCB = new JComboBox<>(years);
		yearsCB.setSize(250, 20);
		yearsCB.setLocation(15, 570);
		container.add(yearsCB);
		
		
		displayTA = new JEditorPane("text/html", "");
		displayTA.setEditable(false);
		displayTA.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, Boolean.TRUE);
		displayTA.setFont(new Font("Avenir", 0, 14));
		displayTA.setText("<h1><center>Welcome to the Phonebook DB</center></h1>"
				+ "To add a friend, fill out all fields, and hit the <b>Add Friend</b> button."
				+ "<br>To find a friend, fill out any field and click <b>Find Friend</b>."
				+ "<br>Clicking on the <b>Save</b> button will save your list of friends."
				+ "<br>If you have a list of friends already saved, load up the file whenever"
				+ "<br>by filling in the File Name field below, and click <b>Load</b>.");

		displaySP = new JScrollPane(displayTA, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		displaySP.setSize(530, 450);
		displaySP.setLocation(300, 70);
		container.add(displaySP);

		addB = Utilities.getButton("Add Friend", 120, 20, 15, 610, new AddBHandler());
		container.add(addB);

		displayB = Utilities.getButton("List All Friends", 110, 20, 300, 540, new DisplayBHandler());
		container.add(displayB);

		findB = Utilities.getButton("Find Friend", 110, 20, 150, 610, new FindBHandler());
		container.add(findB);

		sortAlphaB = Utilities.getButton("Alphabetize", 90, 20, 420, 540, new AlphaBHandler());
		container.add(sortAlphaB);

		sortByDateB = Utilities.getButton("Sort by Date", 90, 20, 520, 540, new DateBHandler());
		container.add(sortByDateB);

		deleteB = Utilities.getButton("Delete Friend", 95, 20, 620, 540, new DeleteBHandler());
		container.add(deleteB);

		updateB = Utilities.getButton("Update Friend", 100, 20, 725, 540, new UpdateBHandler());
		container.add(updateB);

		fileNameTF = Utilities.getTextField(200, 20, 300, 590);
		fileNameTF.setText("File Name");
		fileNameTF.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				fileNameTF.setText("");
			}

			@Override
			public void focusLost(FocusEvent e) {
				
			}
		});

		container.add(fileNameTF);

		loadB = Utilities.getButton("Load", 70, 20, 510, 590, new LoadBHandler());
		container.add(loadB);

		saveB = Utilities.getButton("Save", 70, 20, 590, 590, new SaveBHandler());
		container.add(saveB);

		proceedB = Utilities.getButton("OK", 50, 20, 670, 590, new ProceedBHandler());
		container.add(proceedB);

		cancelB = Utilities.getButton("Cancel", 70, 20, 730, 590, new CancelBHandler());
		container.add(cancelB);

		setVisible(true);
		setResizable(false);

		processingSave = false;
		duplicateRecord = false;

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		disableButtons();
		initializeFriendsList();
	}

	public void initializeFriendsList()
	{
		friends = new FriendsList();
	}

	public void resetAllFields()
	{
		firstNameTF.setText("");
		lastNameTF.setText("");
		streetLineOneTF.setText("");
		streetLineTwoTF.setText("");
		cityTF.setText("");
		statesCB.setSelectedIndex(0);
		zipcodeTF.setText("");
		phoneNumberTF.setText("");
		daysCB.setSelectedIndex(0);
		birthMonthCB.setSelectedIndex(0);
		yearsCB.setSelectedIndex(0);
	}

	public boolean validateTextFields()
	{
		boolean hasNameErrors = false;
		boolean hasAddressErrors = false;
		boolean hasPhoneError = false;
		
		if (firstNameTF.getText().trim().length() == 0) { 
			hasNameErrors = true;
		}
		if (lastNameTF.getText().trim().length() == 0) {
			hasNameErrors = true;
		}
		if (streetLineOneTF.getText().isEmpty()) {
			hasAddressErrors = true;	
		}
		if (cityTF.getText().isEmpty()) {
			hasAddressErrors = true;
		}
		if (zipcodeTF.getText().trim().length() == 0)  {
			hasAddressErrors = true;
		}
		if (phoneNumberTF.getText().equals("(   )   -    ")) {
			hasPhoneError = true;
		}
		if (hasNameErrors && hasAddressErrors && hasPhoneError) {
			JOptionPane.showMessageDialog(container, "All fields are required.");
			return true;
		} else if (hasNameErrors) {
			JOptionPane.showMessageDialog(container, "All name fields are required.");
			return true;
		} else if (hasAddressErrors) {
			JOptionPane.showMessageDialog(container, "All address fields are required.");
			return true;
		} else if (hasPhoneError) {
			JOptionPane.showMessageDialog(container, "A phone number is required.");
			return true;
		}

		return false;
	}

	public class AddBHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{	
			if (validateTextFields()) {
				return;
			}

			String firstName = capitalizeFirstLetter(firstNameTF.getText().trim());
			String lastName = capitalizeFirstLetter(lastNameTF.getText().trim());
			String street1 = streetLineOneTF.getText();
			String street2 = streetLineTwoTF.getText();
			String city = cityTF.getText();
			String state = statesCB.getSelectedItem().toString();
			int zipcode = Integer.parseInt(zipcodeTF.getText());
			String phone = phoneNumberTF.getText();
			Integer daySelected = (Integer) daysCB.getSelectedItem();
			int birthday = daySelected.intValue();
			String birthMonth = birthMonthCB.getSelectedItem().toString();
			Integer yearSelected = (Integer) yearsCB.getSelectedItem();
			int birthYear = yearSelected.intValue();

			Friend newFriend = new Friend(firstName, lastName, street1, street2, city, state,
					zipcode, phone, birthday, birthMonth, birthYear);

			if (friends.userExists(newFriend)) {
				foundFriend = newFriend;
				displayTA.setText(newFriend.getFullName() + " already exists in the DB.<br>Press <b>Update Friend</b> to "
						+ "replace old record or <b>Cancel</b> to cancel new entry.<br>");
				duplicateRecord = true;
				updateB.setEnabled(true);
				cancelB.setEnabled(true);
			} else {
				friends.addFriend(newFriend);
				displayTA.setText("Added <b>" + firstName + "</b> to your list of friends.");
				enableButtons();
			}
			resetAllFields();
		}
	}

	public class DisplayBHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			displayTA.setText("");
			displayTA.setText("Your list of friends includes:<br>"+friends.listAllFriends());
		}
	}

	public class DeleteBHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			Object[] possibilities = friends.addAll();
			String s = (String)JOptionPane.showInputDialog(
					container,
					"Select a friend:\n",
					"Delete Friend",
					JOptionPane.PLAIN_MESSAGE,
					null,
					possibilities,
					friends.first());

			//If a string was returned, say so.
			if ((s != null) && (s.length() > 0)) {
				friends.deleteFriend(s);
				displayTA.setText("");
				if (friends.getFriendCount() == 0) {
					displayTA.setText("Your friends list is empty.<br>");
					disableButtons();
				} else {
					displayTA.setText(s + " successfully deleted from your list of friends<br>");
				}
			}
		}
	}

	public class FindBHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			String nameToFind = "";
			if (firstNameTF.getText().trim().length() == 0 && lastNameTF.getText().trim().length() == 0) {
				JOptionPane.showMessageDialog(container, "A first or last name is required.");
			} else if (firstNameTF.getText().trim().length() != 0 && lastNameTF.getText().trim().length() != 0) {
				nameToFind = firstNameTF.getValue().toString().trim() + " " + lastNameTF.getValue().toString().trim(); 
			} else if (firstNameTF.getText().trim().length() != 0 && lastNameTF.getText().trim().length() == 0) {
				nameToFind = firstNameTF.getValue().toString().trim();
			} else {
				nameToFind = lastNameTF.getValue().toString().trim();
			}

			FriendsList toFind = friends.findFriend(nameToFind);
			String message = "";
			if (toFind.getFriendCount() != 0) {
				message = "Friends matching <b>" + nameToFind + "</b> found in your list:<br>";
				Friend pointer = toFind.getFirstFriend();
				while (pointer != null) {
					message += pointer.getFullName() + "<br>";
					pointer = pointer.getNext();
				}
			} else {
				message = "No friends matched that name.";
			}
			displayTA.setText(message);
		}
	}

	public class AlphaBHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if (friends.isAlphabetized()) {
				displayTA.setText("Phonebook already alphabetized.<br>");
			} else {
				friends = friends.sortFriendsAlphabetically();
				friends.setAlphabetized(true);
				displayTA.setText("Your phonebook was sorted by last name. The list is now:<br>"
						+ friends.listAllFriends());		
			}
		}
	}

	public class DateBHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if (friends.isSortedByAge()) {
				displayTA.setText("Phonebook already sorted by age.<br>");
			} else {
				friends = friends.sortFriendsByAge();
				friends.setAlphabetized(false);
				displayTA.setText("Your phonebook was sorted by age. The list is now:<br>"
						+ friends.listAllFriends());	
			}	
		}
	}

	public class SaveBHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			fileName = fileNameTF.getText();
			fileNameTF.setText("");
			if (fileName.compareTo("") > 0) {
				File theFile = new File(fileName);
				if (!theFile.exists()) {
					friends.saveToFile(fileName);
					displayTA.setText("Data saved to file " + fileName + ".\n");
				} else if (theFile.isDirectory()) {
					displayTA.setText("Error: " + fileName + " is a directory.\n");
				} else if (!theFile.canWrite()) {
					displayTA.setText("Cannot write data to " + fileName + ".\n");
				} else {
					processingSave = true;
					displayTA.setText("\nPress <b>OK</b> to overwrite file " + fileName
							+ " or press <b>Cancel<b> to cancel the save request.\n");
					proceedB.setEnabled(true);
					cancelB.setEnabled(true);
				}
			} else {
				displayTA.setText("You must enter a file name in order to save a file.\n");
			}
		}
	}

	public class LoadBHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			fileName = fileNameTF.getText();
			if (fileName.compareTo("") > 0) {
				File theFile = new File(fileName);
				if (!theFile.exists()) {
					displayTA.setText(fileName + " does not exist. Cannot load data.\n");
				} else if (!theFile.canRead()) {
					displayTA.setText("Cannot read from " + fileName);
				} else {
					String fromLoad = friends.loadFromFile(fileName);
					displayTA.setText("Data loaded from " + fileName + ":<br>"
							+ fromLoad + "\n");
					enableButtons();
				}
			} else {
				displayTA.setText("You must enter a file name in order to load one.");
			}
		}

	}

	public class CancelBHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if (processingSave) {
				displayTA.setText("Save request cancelled. " + fileName + " unchanged.\n");
				processingSave = false;
			} else if (duplicateRecord) {
				displayTA.setText("Friend information unchanged.\n");
				duplicateRecord = false;
			} else if (processingDelete) {
				displayTA.setText("Delete request cancelled.\n");
				processingDelete = false;
			} else {
				System.out.println("Cancel button being handled at inappropriate time" + e.toString());
			}
			proceedB.setEnabled(false);
			cancelB.setEnabled(false);
		}

	}

	public class ProceedBHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if (processingSave) {
				String errmsg = friends.saveToFile(fileName);
				displayTA.setText(fileName + " over written.\n" + errmsg + "\n");
				processingSave = false;
			} else {
				System.out.println("OK button being handled at inappropriate time" + e.toString());
			}
			proceedB.setEnabled(false);
			cancelB.setEnabled(false);
		}

	}

	public class UpdateBHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if (duplicateRecord) {
				if (friends.deleteFriend(foundFriend.getFullName())) {
					if (friends.addFriend(foundFriend)) {
						displayTA.setText("Record for " + foundFriend.getFullName() +
								" was changed.\n");
					} else {
						displayTA.setText("Error in updating record. User information deleted.\n");
					}
				} else {
					displayTA.setText("Error in deleting old record. No change in DB.\n");
				}
				duplicateRecord = false;
			} else {
				System.out.println("OK button being handled at inappropriate time" + e.toString());
			}
			updateB.setEnabled(false);
			cancelB.setEnabled(false);
		}
	}

	public String capitalizeFirstLetter(String original) {
		if (original == null || original.length() <= 1) {
			return original;
		}
		return original.substring(0, 1).toUpperCase() + original.substring(1);
	}

	public void disableButtons()
	{
		displayB.setEnabled(false);
		sortAlphaB.setEnabled(false);
		findB.setEnabled(false);
		sortByDateB.setEnabled(false);
		deleteB.setEnabled(false);
		saveB.setEnabled(false);
		updateB.setEnabled(false);
		cancelB.setEnabled(false);
		proceedB.setEnabled(false);
	}

	public void enableButtons()
	{
		displayB.setEnabled(true);
		sortAlphaB.setEnabled(true);
		findB.setEnabled(true);
		sortByDateB.setEnabled(true);
		deleteB.setEnabled(true);
		saveB.setEnabled(true);
	}

	public static void main(String[] args)
	{
		Phonebook myAppF = new Phonebook();
	}
}
