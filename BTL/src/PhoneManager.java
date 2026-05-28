import java.util.List;

public interface PhoneManager {

    void addPhone(Phone p);

    void editPhone(String id, Phone newPhone);

    void delPhone(String id);

    List<Phone> searchPhone(String keyword);

    void sortedPhone(boolean ascending);

    List<Phone> getAllPhones();

    String generateId();
}