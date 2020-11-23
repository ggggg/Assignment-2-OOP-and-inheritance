package addressbook;

/**
 * @author Ido
 * A book entry.
 */
public class BookEntry {
    // the name of the contact
    private String name;
    // phone number of the contact
    private String phone;
    // address of the contact
    private String address;

    /**
     * @param name the name of the contact
     * @param phone the phone number of the contact
     * @param address the address of the contact
     */
    public BookEntry(String name, String phone, String address) {
        // store the variables
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

    /**
     * @return the entry's name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the entry's phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @return the entry's address
     */
    public String getAddress() {
        return address;
    }

    /**
     *
     * @param name the name of the contact
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @param address the address of the contact
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     *
     * @param phone the phone of the contact
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return the string version of the entry.
     */
    @Override
    public String toString() {
        return "BookEntry[" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ']';
    }
}
