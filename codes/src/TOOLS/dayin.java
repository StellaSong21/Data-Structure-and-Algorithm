package TOOLS;

import java.io.*;

public class dayin {
    public static void main(String args[]) {
        String infname = "D:\\LAB\\IdeaProjects\\LAB\\src\\TOOLS\\dayin.txt"; //默认的输入文件名
        try {
            File fin = new File(infname); //转入的文件对象
            BufferedReader in = new BufferedReader(new FileReader(fin)); //打开输入流
            String s;
            int i = 1;
            while ((s = in.readLine()) != null) {//读字符串
                System.out.println(i++ + ":" + s); //写出
            }
            in.close(); //关闭缓冲读入流及文件读入流的连接
        } catch (IOException e1) { //异常处理
            e1.printStackTrace();
        }
    }

}
