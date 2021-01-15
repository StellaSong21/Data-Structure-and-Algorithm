package LAB04;

import java.io.UnsupportedEncodingException;

public class HashTable {
    private Student[] table;//创建一个hash表
    private int size = 113;//设置表的大小
    private int bias = 1;//用于设置h2（k），m-bias
    private final String DLT = "DELETE";

    public HashTable() {
        table = new Student[size];
    }

    public HashTable(int size) {
        this.size = size;
        table = new Student[size];
    }

    public HashTable(int size, int bias) {
        this.size = size;
        this.bias = bias;
        table = new Student[size];
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setBias(int bias) {
        this.bias = bias;
    }

    public Student[] getTable() {
        return table;
    }

    //插入操作，插入成功返回1，否则返回0
    public int hashInsert(Student student) throws UnsupportedEncodingException {
        String key = student.getName();
        table[hashIndex(key)] = student;
        if (hashSearch(student.getName()) != null) {
            return 1;
        } else return 0;
    }

    //删除操作，如果存在该数据，将此处之前对象的key值设为DELETE，并且返回1，如果该对象不存在，返回0
    public int hashDelete(String key) throws UnsupportedEncodingException {
        int h1 = hashFunction1(key);
        int h2 = hashFunction2(key);
        int i = 0;
        while (getStudent(h1, h2, i) != null) {
            if (getStudent(h1, h2, i).getName().equals(key)) {
                getStudent(h1, h2, i).setName(DLT);
                break;
            }
            i++;
        }
        if (getStudent(h1, h2, i).getName().equals(DLT)) return 1;
        else
            return 0;
    }

    //如果存在该对象，返回对象，否则返回null
    public Student hashSearch(String key) throws UnsupportedEncodingException {
        int h1 = hashFunction1(key);
        int h2 = hashFunction2(key);
        int i = 0;
        while (getStudent(h1, h2, i) != null) {
            if (getStudent(h1, h2, i).getName().equals(key)) {
                return getStudent(h1, h2, i);
            }
            i++;
        }
        return null;
    }

    //返回查找的次数
    public int hashSearch1(String key) throws UnsupportedEncodingException {
        int h1 = hashFunction1(key);
        int h2 = hashFunction2(key);
        int i = 0;
        while (getStudent(h1, h2, i) != null) {
            if (getStudent(h1, h2, i).getName().equals(key)) {
                return i;
            }
            i++;
        }
        return Integer.MAX_VALUE;
    }

    //返回查找的时间
    public long hashSearch2(String key) throws UnsupportedEncodingException {
        int h1 = hashFunction1(key);
        int h2 = hashFunction2(key);
        int i = 0;
        long time1 = System.nanoTime();
        long time2;
        while (getStudent(h1, h2, i) != null) {
            if (getStudent(h1, h2, i).getName().equals(key)) {
                time2 = System.nanoTime();
                return (time2 - time1);
            }
            i++;
        }
        time2 = System.nanoTime();
        return (time2 - time1);
    }

    private Student getStudent(int h1, int h2, int i) {
        return table[(h1 + i * h2) % size];
    }

    private int hashIndex(String key) throws UnsupportedEncodingException {
        int h1 = hashFunction1(key);
        int h2 = hashFunction2(key);
        int i = 0;
        while (getStudent(h1, h2, i) != null || (getStudent(h1, h2, i) != null && getStudent(h1, h2, i).getName().equals(DLT))) {
            i++;
        }
        return (h1 + i * h2) % size;
    }

    private int hash(String key) throws UnsupportedEncodingException {
        byte[] a = key.getBytes("GBK");
        int k = 0;
        for (int i = 0; i < a.length; i++){
            k += a[i];
        }
        return (Math.abs(k))%256;
    }

    private int hashFunction1(String key) throws UnsupportedEncodingException {
        return hash(key) % size;
    }

    private int hashFunction2(String key) throws UnsupportedEncodingException {
        return hash(key) % (size - bias) + 1;
    }
}
