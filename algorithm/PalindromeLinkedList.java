/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public boolean isPalindrome(ListNode head) {
        ListNode temp=head;
        int x=0;
        int m=1;
        int r=0;
        while(temp != null){
            x=x*10+temp.val;
            
            r=temp.val*m+r;
            m=m*10;
            
            temp = temp.next;
        }
        
        if(x==r){
            return true;
        }else{
            return false;
        }
    }
}