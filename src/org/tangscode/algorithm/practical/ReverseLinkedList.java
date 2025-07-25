package org.tangscode.algorithm.practical;

// 定义链表节点
class ListNode {
    int val;
    ListNode next;
    
    ListNode(int val) {
        this.val = val;
        this.next = null;
    }
}

public class ReverseLinkedList {
    
    // 非递归（迭代）方式反转链表
    public ListNode reverseListIterative(ListNode head) {
        ListNode prev = null;    // 前一个节点
        ListNode curr = head;    // 当前节点
        
        while (curr != null) {
            ListNode nextTemp = curr.next;  // 保存下一个节点
            curr.next = prev;               // 反转指针
            prev = curr;                    // 移动prev指针
            curr = nextTemp;                // 移动curr指针
        }
        return prev;  // prev将成为新的头节点
    }
    
    // 递归方式反转链表
    public ListNode reverseListRecursive(ListNode head) {
        // 基准情况：空链表或只有一个节点，直接返回
        if (head == null || head.next == null) {
            return head;
        }
        
        // 递归反转剩余节点
        ListNode newHead = reverseListRecursive(head.next);
        
        // 将当前节点的下一个节点的next指向当前节点
        head.next.next = head;
        // 将当前节点的next置为null
        head.next = null;
        
        // 返回新的头节点
        return newHead;
    }
    
    // 辅助方法：打印链表
    public void printList(ListNode head) {
        ListNode curr = head;
        while (curr != null) {
            System.out.print(curr.val + " ");
            curr = curr.next;
        }
        System.out.println();
    }
    
    // 测试
    public static void main(String[] args) {
        ReverseLinkedList solution = new ReverseLinkedList();
        
        // 创建测试链表: 1 -> 2 -> 3 -> 4 -> 5
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);
        
        System.out.println("原链表:");
        solution.printList(head);
        
        // 测试迭代方式
        ListNode reversedIterative = solution.reverseListIterative(head);
        System.out.println("迭代方式反转后:");
        solution.printList(reversedIterative);
        
        // 再次反转回来用于测试递归方式
        head = solution.reverseListIterative(reversedIterative);
        System.out.println("再次反转恢复原链表:");
        solution.printList(head);
        
        // 测试递归方式
        ListNode reversedRecursive = solution.reverseListRecursive(head);
        System.out.println("递归方式反转后:");
        solution.printList(reversedRecursive);
    }
}
