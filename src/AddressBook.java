/*
 * Name: Emily Lin
 * Description: This is an address book that stores names, addresses, emails and phone numbers for each contact. There 
                is a total of 100 contacts. It also has buttons such as add, delete, edit, save and search.   
 * Date: 31/10/14
 */

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.MaskFormatter;

public class AddressBook extends JFrame implements ActionListener {
    // ************** CLASS VARIABLE DECLARATIONS ***************************
    public static boolean contactAdded; // checks to see if a contact has been added 
    public static boolean alreadyExists; // keeps track of whether or not the contact already exists 
    public static boolean usingAdd; // keeps track of if add is being used
    public static boolean usingSave; // keeps track of if save is being used 
    public static String nameBeingSaved; // current name of the person being saved 

    public static DefaultListModel listModel; // this creates a listModel (will automatically update JList info)
    ArrayList<Contact> contactArray = new ArrayList<Contact>(); // array list stores each contact object

    // JLists and JScrollPane
    JList existingContacts; // Contains the names of the contacts inputted thus far 
    JScrollPane scrollPane = new JScrollPane(); // adds a scroll bar 

    // JLabels
    JLabel nameLabel = new JLabel("Name: ");
    JLabel addressLabel = new JLabel("Address: ");
    JLabel phoneLabel = new JLabel("Phone Number: ");
    JLabel emailLabel = new JLabel("Email: ");
    JLabel searchLabel = new JLabel("Search by name: ");

    // Formatter for JTextField
    MaskFormatter phoneFormat;

    // JTextFields
    JTextField nameField = new JTextField(31);
    JTextField addressField = new JTextField(30);
    JFormattedTextField phoneField;
    JTextField emailField = new JTextField(32);
    JTextField searchField = new JTextField(20);

    // JPanels
    JPanel namePanel = new JPanel(); // panel that contains the name information
    JPanel addressPanel = new JPanel(); // panel that contains the adress information
    JPanel phonePanel = new JPanel(); // panel that contains the phone information
    JPanel emailPanel = new JPanel(); // panel that contains the email information
    JPanel mainPanel = new JPanel(); // panel that holds all the other panels 
    JPanel buttonPanel = new JPanel(); // panel that holds all the buttons 
    JPanel searchPanel = new JPanel(); // panel that holds the search components 

    // Layouts for panels
    FlowLayout flow = new FlowLayout();

    // JButtons
    JButton save = new JButton("Save");
    JButton delete = new JButton("Delete");
    JButton edit = new JButton("Edit");
    JButton add = new JButton("Add");
    JButton search = new JButton("Search");

    // *********************** THE CONSTRUCTOR ************************************
    public AddressBook() {
        listModel = new DefaultListModel(); // will store all the names in the list  

        // Generic set-up
        setSize(500, 500);
        setTitle("Address Book");
        setResizable(false); 

        // These are the things utilized for the list 
        existingContacts = new JList(listModel); // gives list all of the elements stored within array name
        existingContacts.setBorder(BorderFactory.createEmptyBorder(5, 10, 2, 2)); // creates a border inside list 
        existingContacts.setLayoutOrientation(JList.VERTICAL); // Organizes the names in vertical order 
        existingContacts.setVisibleRowCount(-1); // will dynamically adjusts the list 
        existingContacts.setFixedCellHeight(20);
        existingContacts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // allows one item to be selected at a time
        existingContacts.addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent lse) {

                // When an item is selected, these buttons will be pressable 
                delete.setEnabled(true);
                edit.setEnabled(true);
                add.setEnabled(true);

                // Sets the textFields to uneditable mode 
                nameField.setEditable(false);
                addressField.setEditable(false);
                phoneField.setEditable(false);
                emailField.setEditable(false);

                // When item is selected, the information stored in the array will be outputted 
                if (existingContacts.getSelectedIndex() > -1) { // ensures that there is no index out of bounds error

                    for (Contact contact : contactArray) {
                        if ((existingContacts.getSelectedValue().toString()).equals(contact.getName())) {

                            // outputs information onto the respective JTextFields 
                            nameField.setText(contact.getName());
                            addressField.setText(contact.getAddress());
                            emailField.setText(contact.getEmail());
                            phoneField.setValue(contact.getPhoneNumber());

                            break;
                        }
                    }
                }

                if (existingContacts.getSelectedIndex() == -1) { // when nothing is selected 

                    // sets these buttons to unpressable 
                    delete.setEnabled(false);
                    edit.setEnabled(false);
                    save.setEnabled(false);

                    // sets fields to editable so that they can add contacts 
                    nameField.setEditable(true);
                    addressField.setEditable(true);
                    phoneField.setEditable(true);
                    emailField.setEditable(true);
                }

            }
        });

        // Scroll pane 
        scrollPane = new JScrollPane(existingContacts); // adds list to scroll pane
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); // will only add vertical scroll bar if needed
        scrollPane.setPreferredSize(new Dimension(450, 200)); // sets size of the scroll pane
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // creates interior border 

        // Name Panel 
        namePanel.setSize(new Dimension(10, 10));
        namePanel.add(nameLabel);
        namePanel.add(nameField);
        namePanel.setLayout(flow);

        // Address Panel 
        addressPanel.setSize(new Dimension(10, 10));
        addressPanel.add(addressLabel);
        addressPanel.add(addressField);
        addressPanel.setLayout(flow);

        // Formatting for the phone field 
        try {
            phoneFormat = new MaskFormatter("(###)-###-####");
        } catch (java.text.ParseException exc) {
            System.err.println("Something is wrong with the formatter." + exc.getMessage());
        }
        phoneField = new JFormattedTextField(phoneFormat); // sets the phone field to the formatter
        phoneField.setColumns(27); // specifies the size of the field 

        // Phone panel
        phonePanel.setSize(new Dimension(10, 10));
        phonePanel.add(phoneLabel);
        phonePanel.add(phoneField);
        phonePanel.setLayout(flow);

        // Email panel
        emailPanel.setSize(new Dimension(10, 10));
        emailPanel.add(emailLabel);
        emailPanel.add(emailField);
        emailPanel.setLayout(flow);

        // Button panel
        buttonPanel.add(add);
        buttonPanel.add(save);
        buttonPanel.add(edit);
        buttonPanel.add(delete);

        // Search panel
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(search);

        // Unless an item is selected, the following buttons will be "unpressable" 
        delete.setEnabled(false);
        save.setEnabled(false);
        edit.setEnabled(false);

        // Gives buttons actionListener
        add.addActionListener(this);
        delete.addActionListener(this);
        edit.addActionListener(this);
        save.addActionListener(this);
        search.addActionListener(this);

        // Main panel
        mainPanel.add(scrollPane, BorderLayout.NORTH);
        mainPanel.add(namePanel, BorderLayout.WEST);
        mainPanel.add(addressPanel, BorderLayout.WEST);
        mainPanel.add(phonePanel, BorderLayout.WEST);
        mainPanel.add(emailPanel, BorderLayout.WEST);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        mainPanel.add(searchPanel, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);
        setVisible(true);
    }
    
    // *********************** METHOD CALLED WHEN INTERACTIONS ARE MADE WITH JCOMPONENTS **************************

    public void actionPerformed(ActionEvent event) {
        String command = event.getActionCommand();

        if (command.equals("Add")) { // adding a contact 
            usingAdd = true;
            
            if (contactArray.size() > 99){ // ensures user doesn't input over 99 contacts 
                JOptionPane.showMessageDialog(mainPanel, "You cannot add over 100 contacts.");
            } else {
                
             if (existingContacts.getSelectedIndex() == -1) { // if nothing is selected, the information will be stored
                userValid(); // runs the method that checks validation
                if (contactAdded == true && alreadyExists == false) {
                    addContact();

                    // Informs the user that the user has now been added
                    JOptionPane.showMessageDialog(mainPanel, "This contact has now been added.");

                    existingContacts.clearSelection(); // deselects item on the list 

                    // Sets the text fields to empty
                    nameField.setText("");
                    addressField.setText("");
                    phoneField.setText("");
                    emailField.setText("");
                }
            }

            if (existingContacts.getSelectedIndex() > -1) { // if something has been selected in the list 
                existingContacts.clearSelection();

                // Sets the text fields to empty
                nameField.setText("");
                addressField.setText("");
                phoneField.setText("");
                emailField.setText("");

            }

            if (alreadyExists == true && contactAdded == false) { // if no one has been added

                // Informs the user that no contact has now been added 
                JOptionPane.showMessageDialog(mainPanel, "Sorry, this contact already exists.");
            }
          } 

            usingAdd = false; // re-intializes the variable that keeps track of whether or not add is being used 
        } else if (command.equals("Edit")) { // editing information 

            // Makes all textFields editable
            nameField.setEditable(true);
            addressField.setEditable(true);
            phoneField.setEditable(true);
            emailField.setEditable(true);

            // Button enabling/disabling section 
            save.setEnabled(true); // allows the save button to be pressed 
            edit.setEnabled(false); // sets the edit button to unpressable 
            add.setEnabled(false); // sets the save button to unpressable 

        } else if (command.equals("Save")) { // saves the updated information of the contacts 
            usingSave = true;

            nameBeingSaved = existingContacts.getSelectedValue().toString();

            userValid();

            if (contactAdded == true) {
                Contact contact = contactArray.get(existingContacts.getSelectedIndex());
                contact.setName(nameField.getText());
                contact.setAddress(addressField.getText());
                contact.setEmail(emailField.getText());
                contact.setPhoneNumber(phoneField.getText());

                // Outputs a message to the user
                JOptionPane.showMessageDialog(mainPanel, "The information has now been updated.");

                // Sets the text fields to empty
                nameField.setText("");
                addressField.setText("");
                phoneField.setText("");
                emailField.setText("");

                // delselects whatever item was selected
                existingContacts.clearSelection();
                sortListModel();
            }
            usingSave = false;

        } else if (command.equals("Delete")) { // deletes a contact 

            Contact deleted = contactArray.remove(existingContacts.getSelectedIndex());

            if (deleted != null) {
                JOptionPane.showMessageDialog(mainPanel, "This contact has now been deleted.");

                updateListModel();

                // Sets the text fields to empty
                nameField.setText("");
                addressField.setText("");
                phoneField.setText("");
                emailField.setText("");

                // delselects whatever item was selected
                existingContacts.clearSelection();
            }
        } else if (command.equals("Search")) {
            boolean doesNotExist = true;  // keeps track of whether or not the contact exists 

            if (searchField.getText().equals("")) { // if the search field is empty
                JOptionPane.showMessageDialog(mainPanel, "You must type in a name in the search bar.");
            } else {
                for (Contact contact : contactArray) { // traverses through array
                    if (searchField.getText().equals(contact.getName())) { // if there is a match
                        existingContacts.setSelectedValue(contact, true); // goes to the person with matching name
                        doesNotExist = false;
                        searchField.setText("");
                        break;
                    }
                }

                if (doesNotExist == true) {  // if the contact does not exist 
                    JOptionPane.showMessageDialog(mainPanel, "Sorry, this contact does not exist.");
                    searchField.setText("");
                }
            }
        }
    }
    
    // ************************* METHOD USED FOR USER VALIDATION ****************************

    public void userValid() { // user validation for the inputted values
        contactAdded = true; // keeps track of whether or not the contact has been added based on the validation
        alreadyExists = false; // keeps track of whether or not the contact to be added already exists 

        if (nameField.getText().trim().length() == 0) { // Ensures user will always type in a name
            JOptionPane.showMessageDialog(mainPanel, "Sorry, you must give this contact a name.");
            contactAdded = false; // user will not be added 
        }
        
        int index = nameField.getText().indexOf(" "); // stores index of the "space" in the user's name
        
        if (index == -1) { // ensures there is a last name
            JOptionPane.showMessageDialog(mainPanel, "Please input a last name.");
            contactAdded = false; 
        }
        
        if (index > -1){ // ensures that the last name contains characters 
             String tempName = nameField.getText().substring(index);
             if (tempName.trim().length() == 0 ){
                 JOptionPane.showMessageDialog(mainPanel, "Please input a last name.");
            contactAdded = false; 
             }
        }
    
        // This validation is used exclusively for the save button
        if (usingAdd == true) {
            if (existingContacts.getSelectedIndex() > -1) { // if something is selected 
                for (Contact contact : contactArray) { // checks to see if the contact already exists 
                    if (existingContacts.getSelectedValue().toString().equals(contact.getName())) {
                        alreadyExists = true; // informs program that this contact already exists 
                        contactAdded = false; // contact will not be added 
                        break;
                    }
                }
            }

            if (existingContacts.getSelectedIndex() == -1) {
                for (Contact contact : contactArray) { // checks to see if the information is already existing 
                    if (nameField.getText().equals(contact.getName())) {
                        alreadyExists = true; // informs program that this contact already exists 
                        contactAdded = false;
                        break;
                    }
                }
            }
        }

        // This validation is used exclusively for the save button
        if (usingSave == true) {
            for (Contact contact : contactArray) { // checks to see if the contact already exists 
                if (!contact.getName().equals(nameBeingSaved)) {
                    if (nameField.getText().equals(contact.getName())) {
                        alreadyExists = true; // informs program that this contact already exists 
                        contactAdded = false; // contact will not be added 
                        JOptionPane.showMessageDialog(mainPanel, "Sorry, this contact already exists.");
                        break;
                    }
                }
            }
        }

        // Checks if the user's inputted email is valid or not 
        if (emailField.getText().trim().length() == 0) { // if there are values within the email field 
            boolean emailAt = false; // contains the @ symbol
            boolean emailDot = false; // contains a period 

            // user validation for email field
            for (int i = 0; i < (emailField.getText().length()); i++) { // checks if there is an "@" and a "."
                if (emailField.getText().charAt(i) == '@') {
                    emailAt = true;
                }

                if (emailField.getText().charAt(i) == '.') {
                    emailDot = true;
                }
            }

            // if one of the necessary components to an email is missing then, 
            if (emailAt == false || emailDot == false) {
                JOptionPane.showMessageDialog(mainPanel, "Sorry, this is not a valid email.");
                contactAdded = false; // contact will not be added or will not be updated 

            }
        }
    }

    // **************************** METHOD USED TO ADD CONTACTS ******************************************
    public void addContact() { // method that adds a contact 
        listModel.addElement(nameField.getText()); // stores the name entered in the listModel

        // Stores information that the user added to the arrayList 
        contactArray.add(new Contact(nameField.getText(), addressField.getText(), phoneField.getText(), emailField.getText()));

        // calls the sort method
        sortListModel();
    }

    // ****************************** METHOD USED TO SORT CONTACTS *********************************************
    public void sortListModel() { // sorts names in alpha order 
        boolean alreadySorted; // keeps track of whether or not the array is already sorted
        String temp; // stores the temporary value
        String lastName; // stores the last name of the first index
        String lastName2; // stores the last name of the second index 
        
        for (int i = 0; i < (contactArray.size() - 1); i++){
            alreadySorted = true;
            for (int j = 0; j < (contactArray.size() - 1); j++){
                lastName = contactArray.get(j).getName().substring(contactArray.get(j).getName().indexOf(" ")); 
                lastName2 = contactArray.get(j + 1).getName().substring(contactArray.get(j + 1).getName().indexOf(" "));
                
                if (lastName.compareToIgnoreCase(lastName2) > 0){
                    temp = contactArray.get(j).getName();
                    contactArray.get(j).setName(contactArray.get(j+1).getName()); 
                    contactArray.get(j + 1).setName(temp);
                    alreadySorted = false;
                }
            }
            
            if (alreadySorted == true){ // if the array list is already sorted, break out of loop earlier 
                break; 
            }
        }

        // calls method that updates the listModel
        updateListModel();
    }

    // ********************************** METHOD USED TO UPDATE LIST MODEL *****************************************
    public void updateListModel() { // updates the list model
        listModel.removeAllElements(); // clears all the data in list model

        for (Contact contact : contactArray) { // traverses through the arrayList and adds only the names to listModel
            listModel.addElement(contact);
        }
    }

    // ***************************** MAIN METHOD ********************************************
    public static void main(String args[]) {
        AddressBook frame = new AddressBook();

    }

}
