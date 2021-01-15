package LAB01;

public class Main {
    private final static int N = 10000;

    public static void main(String[] args) {
        // write your code here
        int[] a = new int[N];
        int[] b = new int[N];
        int[] c = new int[N];
        for (int i = 0; i < N; i++) {
            int m = (int) (Math.random() * 1000);
            a[i] = m;
            b[i] = m;
        }

        timeOfMerge(a);
        timeOfInsertion(b);
        timeOfCombine(c);

    }

    private static void insert_sort(int[] a, int p, int q) {
        for (int i = p + 1; i < q + 1; i++)
            for (int j = i; j > p; j--)
                if (a[j - 1] > a[j]) {
                    int temp = a[j - 1];
                    a[j - 1] = a[j];
                    a[j] = temp;
                }
    }

    private static void merge(int[] A, int p, int q, int r) {
        int n1 = q - p + 1;
        int n2 = r - q;
        int L[] = new int[n1 + 1];
        int R[] = new int[n2 + 1];
        L[n1] = Integer.MAX_VALUE;
        R[n2] = Integer.MAX_VALUE;
        System.arraycopy(A, p, L, 0, n1);
        for (int j = 0; j < n2; j++) {
            R[j] = A[q + j + 1];
        }
        int i = 0;
        int j = 0;
        for (int k = p; k <= r; k++) {
            if (L[i] <= R[j]) {
                A[k] = L[i];
                i++;
            } else {
                A[k] = R[j];
                j++;
            }
        }
    }

    private static void mergeSort(int[] A, int p, int r, int k) {//归并排序，p为需要排序数组的开始位置，r为结束位置
        if (r - p <= k) {
            insert_sort(A, p, r);
        }
        if (p < r) {
            int q = (p + r) / 2;
            mergeSort(A, p, q, k);
            mergeSort(A, q + 1, r, k);
            merge(A, p, q, r);
        }
    }

    private static void timeOfMerge(int[] array) {


        int start2 = (int) System.nanoTime();
        mergeSort(array, 0, array.length - 1, 0);
        int end2 = (int) System.nanoTime();
        int time2 = end2 - start2;
        System.out.println("Merge time cost: " + time2 + "ns");
    }

    private static void timeOfInsertion(int[] array) {
        int start1 = (int) System.nanoTime();
        insert_sort(array, 0, array.length - 1);
        int end1 = (int) System.nanoTime();
        int time = end1 - start1;
        System.out.println("Insertion time cost: " + time + "ns");
    }

    private static void timeOfCombine(int[] array) {
        for (int k = 30; k < 130; k += 5) {
            int start2 = (int) System.nanoTime();
            mergeSort(array, 0, array.length - 1, k);
            int end2 = (int) System.nanoTime();
            int time1 = end2 - start2;
            System.out.println("Comebine time cost: " + time1 + "ns");
        }
    }

}
