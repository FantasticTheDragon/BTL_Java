import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PhoneManagerImpl implements PhoneManager {

    private List<Phone> phoneList;

    private final String FILE_NAME = "Phone.bin";

    public PhoneManagerImpl() {
        loadFromFile();
    }

    @Override
    public void addPhone(Phone p) {

        phoneList.add(p);

        saveToFile();
    }

    @Override
    public void editPhone(String id, Phone newPhone) {

        for (int i = 0; i < phoneList.size(); i++) {

            if (phoneList.get(i).getId().equals(id)) {

                phoneList.set(i, newPhone);

                saveToFile();

                return;
            }
        }
    }

    @Override
    public void delPhone(String id) {

        phoneList.removeIf(p -> p.getId().equals(id));

        saveToFile();
    }

    @Override
    public List<Phone> searchPhone(String keyword) {

        List<Phone> result = new ArrayList<>();

        for (Phone p : phoneList) {

            if (p.getId().toLowerCase().contains(keyword.toLowerCase())
                    || p.getName().toLowerCase().contains(keyword.toLowerCase())
                    || String.valueOf(p.getPrice()).contains(keyword)) {

                result.add(p);
            }
        }

        return result;
    }

    @Override
    public void sortedPhone(boolean ascending) {

        Collections.sort(phoneList, new Comparator<Phone>() {

            @Override
            public int compare(Phone o1, Phone o2) {

                if (ascending) {

                    return Double.compare(o1.getPrice(), o2.getPrice());

                } else {

                    return Double.compare(o2.getPrice(), o1.getPrice());
                }
            }
        });

        saveToFile();
    }

    @Override
    public List<Phone> getAllPhones() {

        return phoneList;
    }

    @Override
    public String generateId() {

        return "P" + (phoneList.size() + 1);
    }

    private void saveToFile() {

        try {

            ObjectOutputStream oos =
                    new ObjectOutputStream(
                            new FileOutputStream(FILE_NAME));

            oos.writeObject(phoneList);

            oos.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    private void loadFromFile() {

        try {

            ObjectInputStream ois =
                    new ObjectInputStream(
                            new FileInputStream(FILE_NAME));

            phoneList = (List<Phone>) ois.readObject();

            ois.close();

        } catch (Exception e) {

            phoneList = new ArrayList<>();
        }
    }
}