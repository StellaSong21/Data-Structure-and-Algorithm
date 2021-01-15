import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class test {
    public static void main(String[] args) throws ParseException {
        String start = "23:07";
        String end = "00:01";
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Date startt = sdf.parse(start);
        Date endt = sdf.parse(end);
        long time = (endt.getTime() - startt.getTime()) / (1000 * 60);
        System.out.println(time < 0 ? (60 * 24) + time : time);
    }
}
