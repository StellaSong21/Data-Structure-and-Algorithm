package TOOLS;

import java.util.Arrays;
import java.util.List;

//从小到大排序
public class InsertionSort {
    public static void main(String[] args) {
        int[] arr = {12, 2, 3, 5, 1, 34, 5, 6, 3, 7};
        sort(arr, 1);
        System.out.println(Arrays.toString(arr));
    }

    //d表示排序目标在数组中的间隔
    public static void sort(int[] array, int d) {
        for (int j = 1; j < array.length; j++) {
            int key = array[j];
            int i = j - d;
            while (i >= 0 && array[i] > key) {
                array[i + d] = array[i];
                i -= d;
            }
            array[i + d] = key;
        }
    }
}
