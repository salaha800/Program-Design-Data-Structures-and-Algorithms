import java.util.HashMap;


public class HashPhoneBook implements PhoneBook {
    HashMap<Integer, String> book = new HashMap<Integer, String>();

    @Override
    public String search(int phoneNumber) {
        return book.get(phoneNumber);
    }

    @Override
    public boolean add(int phoneNumber, String name) {
        if(search(phoneNumber)==null){
            book.put(phoneNumber, name);
            return true;
        }else return false;
    }

    @Override
    public String update(int phoneNumber, String name) {
        if(search(phoneNumber)!=null){
            String Oldname=search(phoneNumber);
            book.replace(phoneNumber,Oldname, name);
            return Oldname ;
        }else return null;
    }

    @Override
    public String remove(int phoneNumber) {
        //return book.remove(phoneNumber);
        if(search(phoneNumber)!=null)
        {
            String name=search(phoneNumber);
            book.remove(phoneNumber);
            return name;

        }
        else return null;


    }
}
