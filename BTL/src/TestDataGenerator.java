import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class TestDataGenerator {

    public static void main(String[] args) {

        try {

            List<Phone> list = new ArrayList<>();

            list.add(new Phone("P1", "iPhone 13", 18000, 5));
            list.add(new Phone("P2", "iPhone 14 Pro", 25000, 4));
            list.add(new Phone("P3", "Samsung S22", 15000, 3));
            list.add(new Phone("P4", "Samsung S23 Ultra", 28000, 6));
            list.add(new Phone("P5", "Xiaomi Redmi Note 12", 6000, 10));
            list.add(new Phone("P6", "Xiaomi 13T", 11000, 7));
            list.add(new Phone("P7", "Oppo Reno 10", 9000, 8));
            list.add(new Phone("P8", "Oppo Find X5", 20000, 3));
            list.add(new Phone("P9", "Vivo Y21", 4500, 12));
            list.add(new Phone("P10", "Vivo V27", 8500, 9));
            list.add(new Phone("P11", "Realme GT Neo", 10000, 6));
            list.add(new Phone("P12", "Nokia G21", 3500, 15));

            ObjectOutputStream oos =
                    new ObjectOutputStream(new FileOutputStream("Phone.bin"));

            oos.writeObject(list);
            oos.close();

            System.out.println("Phone.bin created with 12 records!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


