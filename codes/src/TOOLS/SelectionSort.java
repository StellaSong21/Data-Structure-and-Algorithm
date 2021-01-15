package TOOLS;

import java.util.Arrays;

//从小到大排序
public class SelectionSort {
    public static void main(String[] args) {
        int[] array = {1, 3, 4, 2, 1, 3, 5, 5, 7, 0};
        System.out.println(Arrays.toString(selectionSort(array)));
    }

    public static int[] selectionSort(int[] array) {
        int length = array.length;
        for (int i = 0; i < length; i++) {
            int min = array[i];
            int minIndex = i;
            for (int j = i; j < length; j++) {
                if (array[j] < min) {
                    min = array[j];
                    minIndex = j;
                }
            }
            array[minIndex] = array[i];
            array[i] = min;
        }
        return array;
    }
}
