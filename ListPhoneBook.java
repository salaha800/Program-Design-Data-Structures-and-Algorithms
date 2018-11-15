import java.util.LinkedList;
import java.util.ListIterator;

/**
 * Simple list implementation of the PhoneBook interface.
 * @author Christine Zarges
 * @version 1.0, 26th October 2017
 */
public class ListPhoneBook implements PhoneBook {

    /**
     * Nested class that stores contact information.
     */
    private class Contact {

        private int phoneNumber;
        private String name;

        public Contact(int phoneNumber,String name) {
            this.name = name;
            this.phoneNumber = phoneNumber;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(int phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

    }

    private LinkedList<Contact> phoneBook;

    /**
     * A default constructor. Creates a List.
     */
    public ListPhoneBook() {
        phoneBook = new LinkedList<Contact>();
    }

    /**
     * Method to look up the owner of phone number.
     *
     * @param phoneNumber the phone number whose owner is to be returned
     * @return the name of the owner, or null if the number does not exist
     */
    @Override
    public String search(int phoneNumber) {
        // search for phone number
        for (Contact c : phoneBook) {
            if (c.getPhoneNumber() == phoneNumber) {
                return c.getName();
            }
        }
        return null;
    }

    /**
     * Method to add a new contact with phone number and name to the phone book. If the phone number already exists,
     * the query is ignored. Otherwise the new contact is added. Returns a boolean indicating if adding the contact
     * was successful.
     *
     * @param phoneNumber the phone number of the new contact
     * @param name        the name in the new contact
     * @return true if the entry was added, false if the phone number already exists
     */
    @Override
    public boolean add(int phoneNumber, String name) {
        // search for phone number
        for (Contact c : phoneBook) {
            if (c.getPhoneNumber() == phoneNumber) {
                return false;
            }
        }
        // add new contact if phone number does not exist
        Contact newContact = new Contact(phoneNumber, name);
        phoneBook.add(newContact);
        return true;
    }

    /**
     * Method to update the name associated with a given phone number. If the phone number does not exist, the
     * query is ignored. Otherwise, the associated name is updated and the old name returned.
     *
     * @param phoneNumber the phone number of the contact to be updated
     * @param name        the new name of the contact
     * @return the old name if the entry was updated, null if the phone number does not exist
     */
    @Override
    public String update(int phoneNumber, String name) {
        // search for phone number
        for (Contact c : phoneBook) {
            if (c.getPhoneNumber() == phoneNumber) {
                // update if found
                String oldName = c.getName();
                c.setName(name);
                return oldName;
            }
        }
        return null;
    }

    /**
     * Method to remove a contact with a given phone number.
     *
     * @param phoneNumber the phone number of the contact to be deleted
     * @return the name of the associated person, or null if the name does not exist
     */
    @Override
    public String remove(int phoneNumber) {
        // search for phone number
        ListIterator<Contact> iterator = phoneBook.listIterator();
        while(iterator.hasNext()){
            Contact c = iterator.next();
            if(c.getPhoneNumber() == phoneNumber){
                iterator.remove();
                return c.getName();
            }
        }
        return null;
    }
}