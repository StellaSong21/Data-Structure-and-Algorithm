package TOOLS;

public class RedixSort {
    public static void main(String[] args) {
        int[] array = {1, 3, 5, 2, 78, 12, 3, 543};
    }

    public static void redixSort(int[] array) {
        int max = array[0];
        for (int a : array) {
            max = (a > max) ? a : max;
        }
        int n = 0;
        while (max != 0) {
            max = max / 10;
            n++;
        }



    }
}
