import jdk.nashorn.internal.objects.NativeUint8Array;
import main.Controller.LeeCode.ListNode;
import org.apache.commons.collections.map.LinkedMap;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

public class LeeCodeTest {
    public static void main(String[] args) {

        /**
         * 第一题，两数之和
         */
        //System.out.println(Arrays.toString(twoSum(new int[]{2, 11, 7, 15}, 9)));

        /**
         * 第二题 两数相加
         */
        //twoAdd();

        /**
         * 无重复字符的最长字符串
         */
        lengthOfLongestSubstring("abcabcbb");



    }

    public static int lengthOfLongestSubstringVIP(String s) {
        // 哈希集合，记录每个字符是否出现过
        Set<Character> occ = new HashSet<Character>();
        int n = s.length();
        // 右指针，初始值为 -1，相当于我们在字符串的左边界的左侧，还没有开始移动
        int rk = -1, ans = 0;
        for (int i = 0; i < n; ++i) {
            if (i != 0) {
                // 左指针向右移动一格，移除一个字符
                occ.remove(s.charAt(i - 1));
            }
            while (rk + 1 < n && !occ.contains(s.charAt(rk + 1))) {
                // 不断地移动右指针
                occ.add(s.charAt(rk + 1));
                ++rk;
            }
            // 第 i 到 rk 个字符是一个极长的无重复字符子串
            ans = Math.max(ans, rk - i + 1);
        }
        return ans;
    }

    public static int lengthOfLongestSubstring(String s) {
        HashMap<Character, Integer> map = new HashMap<>();
        int max = 0, start = 0;
        for (int end = 0; end < s.length(); end++) {
            char ch = s.charAt(end);
            if (map.containsKey(ch)){
                start = Math.max(map.get(ch)+1,start);
            }
            max = Math.max(max,end - start + 1);
            map.put(ch,end);
        }
        return max;
    }

    private static void twoAdd() {
        ListNode A3 = new ListNode();
        ListNode A2 = new ListNode();
        ListNode A1 = new ListNode();

        ListNode B3 = new ListNode();
        ListNode B2 = new ListNode();
        ListNode B1 = new ListNode();
        A1.setVal(2);
        A2.setVal(4);
        A3.setVal(3);

        B1.setVal(5);
        B2.setVal(6);
        B3.setVal(4);

        A3.setNext(null);
        A2.setNext(A3);
        A1.setNext(A2);

        B3.setNext(null);
        B2.setNext(B3);
        B1.setNext(B2);

        ListNode head = null, tail = null;
        int carry = 0;
        while (A1 != null || B1 != null) {
            int n1 = A1 != null ? A1.getVal() : 0;
            int n2 = B1 != null ? B1.getVal() : 0;
            int sum = n1 + n2 + carry;
            if (head == null) {
                head = tail = new ListNode(sum % 10);
            } else {
                tail.setNext(new ListNode(sum % 10));
                tail = tail.getNext();
            }
            carry = sum / 10;
            if (A1 != null) {
                A1 = A1.getNext();
            }
            if (B1 != null) {
                B1 = B1.getNext();
            }
        }
        if (carry > 0) {
            tail.setNext(new ListNode(carry));
        }

        }

    public static int[] twoSum(int[] nums, int target) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i=0 ;i<nums.length ;i++){
            if(map.containsKey(target-nums[i])){
                return new int[]{map.get(target-nums[i]),i};
            }
            map.put(nums[i],i);
        }
        return new int[0];
    }
}
