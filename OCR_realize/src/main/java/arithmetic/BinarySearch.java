package arithmetic;

public class BinarySearch {
    public static void main(String[] args) {

    }

    /**
     * 二分查找递归实现
     *
     * @param srcArray 有序数组
     * @param start    起始点
     * @param end      结束点
     * @param key      查找元素
     * @return key的数组下标，没找到返回-1
     */
    public static int binSerach(int[] srcArray, int start, int end, int key) {
        if (start >= end) {
            return -1;
        }
        // 中间数
        int mid = (end - start) / 2 + start;
        if (srcArray[mid] == key) {  // key 是否在中间
            return mid;
        }
        if (key > srcArray[mid]) {
            // 如果 查找元素 大于中间值，在 进一步 对半 查找
            return binSerach(srcArray, mid + 1, end, key);
        } else if (key < srcArray[mid]) {
            return binSerach(srcArray, start, mid - 1, key);
        }
        return -1;
    }

    // 二分查找普通循环实现
    public static int binSearch(int srcArray[], int key) {
        int mid = srcArray.length / 2;
        if (key == srcArray[mid]) {
            return mid;
        }

        int start = 0;
        int end = srcArray.length - 1;
        while (start <= end) {
            mid = (end - start) / 2 + start;
            if (key < srcArray[mid]) {
                end = mid - 1;
            } else if (key > srcArray[mid]) {
                start = mid + 1;
            } else {
                return mid;
            }
        }
        return -1;
    }
}
