package TOOLS;

//从小到大排序
public class BubbleSort {
    private static int NUM = 5;
    //    private static int[] N={100};
    private static int[] N = {100, 1000, 5000, 7500, 10000, 12500, 15000, 17500, 20000, 22500, 25000};
    private static int[][] BS = new int[NUM][];
    private static int[] BSCT = new int[NUM];
    private static long[] BST = new long[NUM];
    private static String[] function = {"OBS", "FBS", "BBS", "EBBS", "EBS"};

    public static void main(String[] args) {
        long start, end;
        for (int n : N) {
            int[] array = getArray(n);
            for (int i = 0; i < NUM; i++) {
                BS[i] = new int[n];
                BST[i] = Long.MAX_VALUE;
            }
            for (int j = 0; j < 10; j++) {
                long temp = Long.MAX_VALUE;
                for (int k = 0; k < NUM; k++) {
                    BSCT[k] = 0;
                    System.arraycopy(array, 0, BS[k], 0, n);
                    start = System.nanoTime();
                    wrapper(k, BS[k], n, 0, n - 1);
                    end = System.nanoTime();
                    temp = end - start;
                    BST[k] = temp < BST[k] ? temp : BST[k];
                }
            }
            System.out.println("length: " + n);
            for (int k = 0; k < NUM; k++) {
                if (check(BS[k])) {
                    System.out.println(function[k] + ":\t" + (double) BST[k] / 1000000);
                }
            }
            System.out.println();
            for (int k = 0; k < NUM; k++) {
                if (check(BS[k])) {
                    System.out.println(function[k] + ":\t" + BSCT[k]);
                }
            }
            System.out.println();
        }
    }

    private static int[] wrapper(int i, int[] array, int size, int firstindex, int lastindex) {
        switch (i) {
            case 0:
                original_bubble_sort(array, i);
                break;
            case 1:
                flag_bubble_sort(array, i);
                break;
            case 2:
                bidirection_bubble_sort(array, i);
                break;
            case 3:
                enhance_bidirection_bubble_sort(array, i);
                break;
            case 4:
                enhance_bubble_sort(array, i, array.length, 0, array.length - 1);
                break;
            default:
                break;
        }
        return array;
    }

    // 后i项已经排好序，双层for循环
    private static int[] original_bubble_sort(int[] array, int k) {
        int length = array.length;
        for (int i = 0; i < length - 1; i++) {
            for (int j = 0; j < length - i - 1; j++) {
                BSCT[k]++;
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
        return array;
    }

    // 判断是否已经排好序，for循环
    private static int[] flag_bubble_sort(int[] array, int k) {
        boolean flag;
        int length = array.length;
        for (int i = 0; i < length - 1; i++) {
            flag = false;
            for (int j = 0; j < length - i - 1; j++) {
                BSCT[k]++;
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    flag = true;
                }
            }
            if (!flag) {
                break;
            }
        }
        return array;
    }

    // 记录上次交换的位置
    private static int[] mark_bubble_sort(int[] array, int k) {
        int length = array.length;
        int lastSwap = 0;
        int unorderedBorder = length - 1;
        for (int i = 0; i < length - 1; i++) {
            for (int j = 0; j < unorderedBorder; j++) {
                BSCT[k]++;
                if (array[j] > array[j + 1]) {
                    swap(array, j, j + 1);
                    lastSwap = j;
                }
            }
            unorderedBorder = lastSwap;
        }
        return array;
    }


    private static int[] bidirection_bubble_sort(int[] array, int k) {
        boolean flag = true;
        int length = array.length;
        for (int i = 0; i <= length / 2 && flag; i++) {
            flag = false;
            for (int j = i; j < length - i - 1; j++) {
                BSCT[k]++;
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    flag = true;
                }
            }
            for (int m = length - i - 1; m > i; m--) {
                BSCT[k]++;
                if (array[m] < array[m - 1]) {
                    int temp = array[m];
                    array[m] = array[m - 1];
                    array[m - 1] = temp;
                    flag = true;
                }
            }
        }
        return array;
    }

    private static int[] enhance_bidirection_bubble_sort(int[] array, int k) {
        boolean flag = true;
        int length = array.length;
        for (int i = 0; i <= length / 2 && flag; i++) {
            flag = false;
            for (int j = i; j < length - i - 1; j++) {
                BSCT[k]++;
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    flag = true;
                }
                BSCT[k]++;
                if (array[length - j - 1] < array[length - j - 2]) {
                    int temp = array[length - j - 1];
                    array[length - j - 1] = array[length - j - 2];
                    array[length - j - 2] = temp;
                    flag = true;
                }
            }
        }
        return array;
    }

    private static int[] enhance_bubble_sort(int[] array, int k, int size, int firstindex, int lastindex) {
        if (size > 1) {
            int temp = 0;
            int maxcounter = lastindex;
            int mincounter = firstindex;
            int max = array[lastindex];
            int min = array[firstindex];
            for (int i = firstindex; i <= lastindex; i++) {
                BSCT[k]++;
                if (array[i] >= max) {
                    max = array[i];
                    maxcounter = i;
                }
                BSCT[k]++;
                if (array[i] < min) {
                    min = array[i];
                    mincounter = i;
                }
            }
            if (firstindex == maxcounter && lastindex == mincounter) {
                array[firstindex] = min;
                array[lastindex] = max;
            } else if (firstindex == maxcounter && lastindex != mincounter) {
                temp = array[lastindex];
                array[lastindex] = max;
                array[firstindex] = min;
                array[mincounter] = temp;
            } else if (firstindex != maxcounter && lastindex == mincounter) {
                temp = array[firstindex];
                array[firstindex] = min;
                array[lastindex] = max;
                array[maxcounter] = temp;
            } else {
                temp = array[firstindex];
                array[firstindex] = min;
                array[mincounter] = temp;
                temp = array[lastindex];
                array[lastindex] = max;
                array[maxcounter] = temp;
            }
            firstindex = firstindex + 1;
            lastindex = lastindex - 1;
            size = size - 2;
            return enhance_bubble_sort(array, k, size, firstindex, lastindex);
        } else
            return array;
    }

    // 最原始的版本
    public static void bubbleSort1(int[] array) {
        int length = array.length;
        for (int i = 0; i < length - 1; i++) {
            for (int j = 0; j < length - 1; j++) {
                if (array[j] > array[j + 1]) {
                    swap(array, j, j + 1);
                }
            }
        }
    }

    // 每次在遍历的同时，判断是否已经排好序,for循环
    public static void bubbleSort31(int[] array) {
        int length = array.length;
        boolean flag; // 表示是否发生交换
        for (int i = 0; i < length - 1; i++) {
            flag = false;
            for (int j = 0; j < length - 1; j++) {
                if (array[j] > array[j + 1]) {
                    swap(array, j, j + 1);
                    flag = true;
                }
            }
            if (!flag) {
                break;
            }
        }
    }

    // 每次在遍历的同时，判断是否已经排好序,while循环
    public static void bubbleSort32(int[] array) {
        boolean flag = true;
        int length = array.length;
        while (flag) {
            flag = false;
            for (int j = 1; j < length - 1; j++) {
                if (array[j] > array[j + 1]) {
                    swap(array, j, j + 1);
                    flag = true;
                }
            }
        }
    }

    // 后i项已经排好序，while循环
    public static void bubbleSort22(int[] array) {
        int i = array.length;
        while (i > 2) {
            for (int j = 0; j < i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    swap(array, j, j + 1);
                }
            }
            i--;
        }
    }

    // 判断是否已经排好序，while循环
    public static void bubbleSort42(int[] array) {
        boolean flag = true;
        int i = array.length;
        while (flag) {
            flag = false;
            for (int j = 1; j < i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    swap(array, j, j + 1);
                    flag = true;
                }
            }
            i--;
        }
    }


    // 记录上次交换的位置，并判断是否排好序
    public static void bubbleSort61(int[] array) {
        int length = array.length;
        boolean flag;
        int lastSwap = 0;
        int unorderedBorder = length - 1;
        for (int i = 0; i < length - 1; i++) {
            flag = false;
            for (int j = 0; j < unorderedBorder; j++) {
                if (array[j] > array[j + 1]) {
                    swap(array, j, j + 1);
                    lastSwap = j;
                    flag = true;
                }
            }
            unorderedBorder = lastSwap;
            if (!flag) {
                break;
            }
        }
    }

    // 从前向后排序
    private static void preSort(int[] array, int length, int preIndex) {
        for (int i = preIndex + 1; i < length; i++) {
            if (array[preIndex] > array[i]) {
                swap(array, preIndex, i);
            }
        }
    }

    // 从后向前排序
    private static void backSort(int[] array, int backIndex) {
        for (int i = backIndex - 1; i >= 0; i--) {
            if (array[i] > array[backIndex]) {
                swap(array, i, backIndex);
            }
        }
    }

    private static int[] getArray(int n) {
        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = (int) (Math.random() * n);
//            array[i] = n - i;
//            array[i] = i;
        }
        return array;
    }

    private static boolean check(int[] array) {
        for (int i = 1; i < array.length; i++) {
            if (array[i - 1] > array[i]) {
                return false;
            }
        }
        return true;
    }

    private static void swap(int[] array, int i, int j) {
        int temp = array[j];
        array[j] = array[i];
        array[i] = temp;
    }
}
