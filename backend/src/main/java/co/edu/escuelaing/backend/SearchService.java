package co.edu.escuelaing.backend;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;

@Service
public class SearchService {
    
    public int binarysearch(int[] list, int value) {
        int init = 0;
        int end = list.length-1;
        while (init <= end) {
            int mid = init + (end-init)/2;
            if (list[mid] == value) {
                return mid;
            } else if (list[mid] < value) {
                init = mid + 1;
            } else {
                end = mid - 1;
            }
        }
        return -1;
    }

    public String fibonacci(int n) {
        if (n == 1) {
            return "0";
        } else if (n == 2) {
            return "0,1";
        }
        List<Integer> fibo = new ArrayList<>();
        fibo.add(0);
        fibo.add(1);
        for (int i = 2; i<=n ; i++) {
            fibo.add(fibo.get(i-2)+fibo.get(i-1));
        }

        return fibo.toString().replace("[", "")
        .replace("]","").replace(" ", "");
    }

    public String catalan(int n) {
        if (n == 1) {
            return "1";
        }
        List<Integer> cat = new ArrayList<>();
        cat.add(1);
        for (int i = 1; i<=n; i++) {
            cat.add(cat.get(i-1) * 2*(2*i+1) / (i+2));
        }
        return cat.toString().replace("[","")
        .replace("]","").replace(" ","");
    }

    public int productopunto(int[] list1, int[] list2) {
        int result = 0;
        for(int i = 0; i< list1.length; i++) {
            result = result + list1[i]*list2[i];
        }
        return result;
    }

}
