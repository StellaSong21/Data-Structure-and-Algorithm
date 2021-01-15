package TOOLS;

import java.util.Arrays;

//从小到大排序
public class MergeSort {
    public static void main(String[] args) {
        int[] array = {1, 3, 4, 2, 1, 3, 5, 5, 7, 0};
        mergeSort(array, 0, array.length - 1);
        System.out.println(Arrays.toString(array));
    }

    //p ≤ q ＜ r，指下标从p到r
    public static void merge(int[] array, int p, int q, int r) {
        int n1 = q - p + 1;
        int n2 = r - q;
        int[] left = new int[n1 + 1];
        int[] right = new int[n2 + 1];
        System.arraycopy(array, p, left, 0, n1);
        System.arraycopy(array, q + 1, right, 0, n2);
        left[n1] = Integer.MAX_VALUE;
        right[n2] = Integer.MAX_VALUE;
        int i = 0, j = 0;
        for (int k = p; k <= r; k++) {
            if (left[i] <= right[j]) {
                array[k] = left[i];
                i++;
            } else {
                array[k] = right[j];
                j++;
            }
        }
    }

    public static void mergeSort(int[] array, int p, int r) {
        if (p < r) {
            int q = (p + r) / 2;
            mergeSort(array, p, q);
            mergeSort(array, q + 1, r);
            merge(array, p, q, r);
        }
    }
}
