package TOOLS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

//对两位数
public class BucketSort {
    public static void main(String[] args) {
        int[] array = {12, 23, 43, 45, 33, 65, 90, 12, 76, 45, 87, 98, 75, 88, 99};
        bucketSort(array);
        System.out.println(Arrays.toString(array));
    }

    @SuppressWarnings("unchecked")
    public static void bucketSort(int[] array) {
        int length = array.length;
        List[] bucket = new ArrayList[10];
        for (int i = 0; i < 10; i++) {
            bucket[i] = new ArrayList<Integer>();
        }
        for (int j = 0; j < length; j++) {
            bucket[array[j] / 10].add(array[j]);
        }
        for (int k = 0; k < 10; k++) {
            if (!bucket[k].isEmpty()) {
                Collections.sort(bucket[k]);
            }
        }
        int m = 0;
        do {
            for (int n = 0; n < 10; n++) {
                for (int l = 0; l < bucket[n].size(); l++)
                    array[m++] = (int) bucket[n].get(l);
            }
        } while (m < array.length);
    }
}
