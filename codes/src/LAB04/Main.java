package LAB04;

import java.io.*;

public class Main {
    public static void main(String[] args) throws UnsupportedEncodingException {
        HashTable table = new HashTable();
        String[] studentName = new String[48];
        try {
            FileInputStream fin = new FileInputStream("./src/student.txt");
            InputStreamReader reader = new InputStreamReader(fin);
            BufferedReader br = new BufferedReader(reader);
            String s = null;
            int index = 0;
            while ((s = br.readLine()) != null) {
                String[] info = s.split("\\s");
                Student student = new Student();
                studentName[index++] = info[2];
                student.setName(info[2]);
                student.setStudentNumber(info[1]);
                student.setGender(info[4]);
                student.setAge((int) (Math.random() * 3) + 18);
                student.setPhoneNumber(info[1]);
                int i = table.hashInsert(student);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < table.getSize(); i++) {
            Student student = table.getTable()[i];
            if (student != null)
                System.out.println(i + "\t" + student.getName());
            else
                System.out.println(i);
        }

        int count = 0;
        for (int j = 0; j < studentName.length; j++) {
            count += table.hashSearch1(studentName[j]);
        }
        System.out.println((double) count / (double) 48);

        long time = 0;
        for (int m = 0; m < 500; m++) {
            for (int j = 0; j < studentName.length; j++) {
                time += table.hashSearch2(studentName[j]);
            }
        }
        System.out.println(time / (double) 500 + " ns");

        System.out.println(table.hashDelete("宋怡景"));
        System.out.println(table.hashSearch("宋怡景"));

//        for (int k = 0; k < studentName.length; k++) {
//            System.out.println(table.hashSearch(studentName[k]).getName());
//        }

    }
}
