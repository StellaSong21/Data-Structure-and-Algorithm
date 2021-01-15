package LAB04;

import java.io.UnsupportedEncodingException;

/*
 *该类用于寻找函数中的m
 */
public class Test {
    public static void main(String[] args) throws UnsupportedEncodingException{
        String[] names = {
                "苏沃", " 叶青", " 王宸", " 唐昕悦", "黄元敏", "刘铭涵", "刘海强", "李璠", "陆逸凡", "吴新铭", "金毅铭",
                "黎沈少杰", "周钰承", "林晨", "林国鹏", "俞继涛", "李翀", "邓朋", "李玎善", "卢永强", "王永立", "翟登展",
                "杜东方", "姜向阳", "石林", "王尚", "夏禹天", "梁伟业", "李江渝", "刘佳兴", "杨辉", "张思源", "王麒迪",
                "胡彦雯", "黄佳妮", "石睿欣", "吴琛宁", "吴楚盈", "张岑湲", "周君怡", "刘佳楠", "张逸涵", "胡宵宵",
                "刘书宁", "宋怡景", "黄蕙茹", "罗蓉", "贺曦"
        };

        int length = names.length;

        int p = 51;
        int q;

        //取m为素数，使得函数h2总是返回比m小的正整数
        do {
            q = 0;
            do {
                int res = names[q].hashCode() % (p - 1) + 1;
                if (res > 0 && res < p) {
                    q++;
                } else break;
                if (q == length) {
                    System.out.println(p);
                }
            } while (q < length);
            p++;
        } while (p <= 200);

        //取m为2的幂，使得h2总是返回奇数
        p = 4;
        do {
            q = 0;
            do {
                int res = hash(names[q]) % ((int) Math.pow(2, p) - 1);
                if (res % 2 == 0) {
                    q++;
                } else {
                    break;
                }
                if (q == length) {
                    System.out.println(p);
                }
            } while (q < length);
            p++;
        } while (p <= 1000);
    }
    private static int hash(String key) throws UnsupportedEncodingException {
        byte[] hashes = key.getBytes();
        int k = 0;
        for (int i = 0; i < hashes.length; i++){
            k += hashes[i];
        }
        return k;
    }
}
