import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FindK {

    private static int partition(Integer[] arr, int left, int right) {
        int pivot = arr[right];
        Integer[] leftArr;
        Integer[] rightArr;

        leftArr = IntStream.range(left, right)
                .filter(i -> arr[i] < pivot)
                .map(i -> arr[i])
                .boxed()
                .toArray(Integer[]::new);

        rightArr = IntStream.range(left, right)
                .filter(i -> arr[i] > pivot)
                .map(i -> arr[i])
                .boxed()
                .toArray(Integer[]::new);

        int leftArraySize = leftArr.length;
        System.arraycopy(leftArr, 0, arr, left, leftArraySize);
        arr[leftArraySize + left] = pivot;
        System.arraycopy(rightArr, 0, arr, left + leftArraySize + 1,
                rightArr.length);

        return left + leftArraySize;
    }

    private static void swap(Integer[] arr, int n1, int n2) {
        int temp = arr[n2];
        arr[n2] = arr[n1];
        arr[n1] = temp;
    }

    static int findKthElementByQuickSelect(Integer[] arr, int left, int right, int k) {
        if (k >= 0 && k <= right - left + 1) {
            int pos = randomPartition(arr, left, right);
            if (pos - left == k) {
                return arr[pos];
            }
            if (pos - left > k) {
                return findKthElementByQuickSelect(arr, left, pos - 1, k);
            }
            return findKthElementByQuickSelect(arr, pos + 1,
                    right, k - pos + left - 1);
        }
        return 0;
    }

    private static int randomPartition(Integer[] arr, int left, int right) {
        int n = right - left + 1;
        int pivot = (int) (Math.random()) * n;
        swap(arr, left + pivot, right);
        return partition(arr, left, right);
    }

    static int findKthLargestBySorting(Integer[] arr, int k) {
        Arrays.sort(arr);
        int targetIndex = arr.length - k - 1;
        return arr[targetIndex];
    }

    static int findKthLargestBySorting(ArrayList<Integer> arr, int k) {
        Collections.sort(arr);
        int targetIndex = arr.size() - k - 1;
        return arr.get(targetIndex);
    }

    // NOTE The following code actually doesn't work. If I were smart enough I could rewrite it to do a average O(n) sort with an arrayList, but i'm currently not
    private static int partition(ArrayList<Integer> arr, int left, int right) {
        int pivot = arr.get(right);
        List<Integer> leftArr;
        List<Integer> rightArr;

        leftArr = IntStream.range(left, right)
                .filter(i -> arr.get(i) < pivot)
                .map(arr::get)
                .boxed()
                .collect(Collectors.toList());

        rightArr = IntStream.range(left, right)
                .filter(i -> arr.get(i) > pivot)
                .map(arr::get)
                .boxed()
                .collect(Collectors.toList());

        int leftArraySize = leftArr.size();
//        System.arraycopy(leftArr, 0, arr, left, leftArraySize);
        arr.clear();
        arr.addAll(leftArr);//.subList(0, left));

        int index = leftArraySize + left;
        if (index >= arr.size())
            arr.add(pivot);
        else
            arr.set(index, pivot);
//        arr[leftArraySize + left] = pivot;


//        System.arraycopy(rightArr, 0, arr, left + leftArraySize + 1,rightArr.size());
        arr.addAll(rightArr);//.subList(0, left + leftArraySize + 1));

        return left + leftArraySize;
    }

    private static void swap(ArrayList<Integer> arr, int n1, int n2) {
        int temp = arr.get(n2);
        arr.set(n2, arr.get(n1));
        arr.set(n1, temp);
    }

    static int findKthElementByQuickSelect(ArrayList<Integer> arr, int left, int right, int k) {
        if (k >= 0 && k <= right - left + 1) {
            int pos = randomPartition(arr, left, right);
            if (pos - left == k) return arr.get(pos);
            if (pos - left > k)
                return findKthElementByQuickSelect(arr, left, pos - 1, k);

            return findKthElementByQuickSelect(arr, pos + 1, right, k - pos + left - 1);
        }
        return 0;
    }

    private static int randomPartition(ArrayList<Integer> arr, int left, int right) {
        int n = right - left + 1;
        int pivot = (int) (Math.random()) * n;

        swap(arr, left + pivot, right);
        return partition(arr, left, right);
    }

}
