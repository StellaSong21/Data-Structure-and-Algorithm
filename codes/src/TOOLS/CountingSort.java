package TOOLS;

import java.util.Arrays;

//从小到大排序
public class CountingSort {
    public static void main(String[] args) {
        int[] arr = {12, 2, 3, 5, 1, 34, 5, 6, 3, 7};
        countingSort(arr);
        System.out.println(Arrays.toString(arr));
    }


    //缺点：三个循环，其中两个重复的循环
    public static void countingSort(int[] array) {
        int max = array[0];
        int min = array[0];
        for (int anArray : array) {
            if (anArray > max)
                max = anArray;
            if (anArray < min)
                min = anArray;
        }
        int[] count = new int[max - min + 1];
        for (int anArray : array) {
            count[anArray - min]++;
        }
        int index = 0;
        for (int i = 0; i < max - min + 1; i++) {
            while (count[i] != 0) {
                array[index++] = i + min;
                count[i]--;
            }
        }
    }
}
