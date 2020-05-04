package com.tgedr.labs.challenge;


import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

public class RemoveNthNodeFromEndofList {

    public static class ListNode {

        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }

        static ListNode create(int x, ListNode next){
            ListNode r = new ListNode(x);
            r.next = next;
            return r;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ListNode)) return false;

            ListNode node = (ListNode) o;

            if (val != node.val) return false;
            return next != null ? next.equals(node.next) : node.next == null;
        }

        @Override
        public int hashCode() {
            int result = val;
            result = 31 * result + (next != null ? next.hashCode() : 0);
            return result;
        }
    }

    static class Solution {

        public ListNode removeNthFromEnd(ListNode head, int n) {

            ListNode r = head;

            List<ListNode> nodes = new LinkedList<>();
            nodes.add(head);
            ListNode node = head;
            while( null != ( node = node.next ) )
                nodes.add(node);

            int index2remove = nodes.size()-n;

            if( 0 == index2remove )
                r = head.next;
            else if ( (nodes.size()-1) == index2remove ){
                ListNode previous = nodes.get(index2remove-1);
                previous.next = null;
            }
            else if ( 0 < index2remove && index2remove < nodes.size()-1 ) {
                ListNode previous = nodes.get(index2remove-1);
                ListNode next = nodes.get(index2remove+1);
                previous.next = next;
            }

            return r;
        }

    }


    @Test
    public void test() throws Exception {

        ListNode n = createList(new int[]{1,2,3,4,5});
        Solution o = new Solution();

        //1->2->3->4->5, and n = 2 => 1->2->3->5
        ListNode expected = createList(new int[]{1,2,3,5});
        ListNode actual = o.removeNthFromEnd(n, 2);
        Assert.assertEquals(expected, actual);

    }

    ListNode createList(int[] ns){

        int index = ns.length-1;

        ListNode n = null;
        for(int i=index; i>=0; i--){
            n = ListNode.create(ns[i], n);
        }
        return n;
    }

}


