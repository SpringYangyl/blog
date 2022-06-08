//public class ListDemo {
//    public static void main(String[] args) {
//        ListNode head = new ListNode(1);
//        head.next = new ListNode(2);
//        head.next.next = new ListNode(3);
//        head.next.next.next = new ListNode(4);
//        head.next.next.next.next = new ListNode(5);
//        ListDemo demo = new ListDemo();
//        demo.reverseKGroup(head,2);
//
//    }
//    public ListNode reverseKGroup(ListNode head, int k) {
//        int length = help(head);
//        ListNode dummy = new ListNode(0);
//        ListNode pre = dummy,cur = head,next;
//        dummy.next = head;
//        for(int i = 0;i<length/k;i++){
//            for(int j = 0;j<k-1;j++){
//                next = cur.next;
//                //2345
//                cur.next = next.next;
//                //1 345
//                next.next = pre.next;
//                //3 4 5
//                pre.next = next;
//                //021345
//            }
//            pre = cur;
//            cur = pre.next;
//
//        }
//        return dummy.next;
//    }
//    public int help(ListNode head){
//        int cnt = 0;
//        while(head!=null){
//            cnt++;
//            head = head.next;
//        }
//        return cnt;
//    }
//}
//class ListNode {
//      int val;
//     ListNode next;
//     ListNode() {}
//      ListNode(int val) { this.val = val; }
//      ListNode(int val, ListNode next) { this.val = val; this.next = next; }
//  }
