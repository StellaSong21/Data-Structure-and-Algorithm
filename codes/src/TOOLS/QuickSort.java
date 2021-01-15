package TOOLS;

import java.util.Arrays;

//从小到大排序
public class QuickSort {
    public static void main(String[] args) {
        int[] array = {1, 3, 4, 2, 1, 3, 5, 5, 7, 0};
        quickSort(array, 0, array.length - 1);
        System.out.println(Arrays.toString(array));
    }

    public static void quickSort(int[] array, int p, int r) {
        if (p < r) {
            int q = partition(array, p, r);
            quickSort(array, p, q - 1);
            quickSort(array, q + 1, r);
        }
    }

    public static int partition(int[] array, int p, int r) {
        int m = (int) (Math.random() * (r - p)) + p;
        int tmp = array[r];
        array[r] = array[m];
        array[m] = tmp;
        int x = array[r];
        int i = p - 1;
        for (int j = p; j < r; j++) {
            if (array[j] < x) {
                i += 1;
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }
        int temp = array[i + 1];
        array[i + 1] = array[r];
        array[r] = temp;
        return i + 1;
    }
}
