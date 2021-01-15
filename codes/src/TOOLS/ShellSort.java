package TOOLS;

import java.util.Arrays;

//从小到大排序
public class ShellSort {
    public static void main(String[] args) {
        int[] array = {1, 3, 4, 2, 1, 3, 5, 5, 7, 0};
        System.out.println(Arrays.toString(shellSort(array)));
    }

    //increment取值为  increment/3（向下取整）+1
    public static int[] shellSort(int[] array) {
        int increment = array.length;

//        while (increment != 1) {
//            increment = (int) (Math.floor(increment / 3)) + 1;
//            for (int i = 0; i < increment; i++) {
//                for (int j = i + increment; j < array.length; j += increment) {
//                    int key = array[j];
//                    int m = j - increment;
//                    while (m >= 0 && array[m] > key) {
//                        array[m + increment] = array[m];
//                        m -= increment;
//                    }
//                    array[m + increment] = key;
//                }
//            }
//        }
        do {
            increment = (int) (Math.floor(increment / 3)) + 1;
            for (int i = 0; i < increment; i++) {
                for (int j = i + increment; j < array.length; j += increment) {
                    int key = array[j];
                    int m = j - increment;
                    while (m >= 0 && array[m] > key) {
                        array[m + increment] = array[m];
                        m -= increment;
                    }
                    array[m + increment] = key;
                }
            }
        } while (increment > 1);
        return array;
    }
}
