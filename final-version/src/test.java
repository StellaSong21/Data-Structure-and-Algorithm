import com.fudan.sw.dsa.project2.entity.Address;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class test {
    public static void main(String[] args) throws ParseException {
        ArrayList<Address> addresses = new ArrayList<>();
        Address address = new Address("get","1.0","1.0");
        addresses.add(address);
        ArrayList<Address> addresses1 = new ArrayList<>();
        addresses1.add(address);
        addresses1.get(0).setAddress("picec");
        System.out.println(addresses.get(0).getAddress());

    }
}
