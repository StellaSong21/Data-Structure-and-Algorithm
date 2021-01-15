//package LAB03;
//
//public class HeapSort {
//    private int d = 2;
//
//    public void setD(int d) {
//        this.d = d;
//    }
//
//    public int[] maxHeapify(int[] array, int i, int length) {
//        int largest = i;
//        int largestValue = array[i];
//        int left = i * d + 1;
//        int right = (i + 1) * d;
//        for (int j = left; j <= right; j++) {
//            if (j <= length - 1 && array[j] > largestValue) {
//                largest = j;
//                largestValue = array[j];
//            }
//        }
//        if (largest != i) {
//            array[largest] = array[i];
//            array[i] = largestValue;
//            maxHeapify(array, largest, length);
//        }
//        return array;
//    }
//
//    public void buildMaxHeap(int[] array) {
//        for (int i = (int) Math.floor(array.length / 2) - 1; i >= 0; i--) {
//            maxHeapify(array, i, array.length);
//        }
//
//    }
//
//    public int[] heapSort(int[] array) {
//        int[] result = new int[array.length];
//        int m = 0;
//        buildMaxHeap(array);
//        int length = array.length;
//        for (int i = length - 1; i >= 0; i--) {
//            result[m++] = array[0];
//            array[0] = array[i];
//            maxHeapify(array, 0, --length);
//        }
//        array = result;
//        return array;
//    }
//
//    public int[] extractMax(int[] array) {
//        if (array.length < 1) {
//            System.out.println("没有元素");
//            return null;
//        }
//        array[0] = array[array.length - 1];
//        int[] newArray = new int[array.length - 1];
//        System.arraycopy(array, 0, newArray, 0, array.length - 1);
//        maxHeapify(newArray, 0, newArray.length);
//        return newArray;
//    }
//
//    public int[] increaseKey(int[] array, int i, int key) {
//        if (key <= array[i]) {
//            System.out.println("key值没有增加");
//            return null;
//        }
//        array[i] = key;
//        while (i > 0) {
//            int parent = (int) Math.floor((i - 1) / d);
//            if (array[parent] < array[i]) {
//                int temp = array[i];
//                array[i] = array[parent];
//                array[parent] = temp;
//                i = parent;
//            }
//        }
//        return array;
//    }
//
//    public int[] insert(int[] array, int key) {
//        int[] newArray = new int[array.length + 1];
//        System.arraycopy(array, 0, newArray, 0, array.length);
//        array = newArray;
//        array[array.length - 1] = key - 1;
//        increaseKey(array, array.length - 1, key);
//        return array;
//    }
//
//    public void visible(int[] array) {
//        int h = (int) Math.ceil(Math.log(array.length * (d - 1) + 1) / Math.log(d) - 1);
//        int line = 0;
//        for (int i = 0; i <= h; i++) {
//            for (int j = line; j <= line + (int) Math.pow(d, i) - 1 && j < array.length; j++)
//                System.out.print(array[j] + "\t");
//            System.out.println();
//            line += (int) Math.pow(d, i);
//
//        }
//    }
//}
