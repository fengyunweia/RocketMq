package main.Controller.LeeCode;

import lombok.Data;

/**
 * @author fengyunwei
 */
@Data
public class ListNode {
    int val;
    ListNode next;
    public ListNode() {}
    public ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}
