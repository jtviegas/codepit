package com.tgedr.labs.challenge.leetcode;

import com.tgedr.labs.challenge.Solution;
import com.tgedr.labs.commons.ListNode;
import javafx.beans.binding.ListBinding;
import org.apache.commons.lang3.tuple.Pair;

public class AddTwoNumbers implements Solution<Pair<ListNode,ListNode>,ListNode> {

    @Override
    public ListNode goForIt(Pair<ListNode,ListNode> input) {

        ListNode a = input.getLeft();
        ListNode b = input.getRight();
        ListNode initialNode = ListNode.builder().build();
        ListNode r = null;

        int carryOver = 0;
        while( null != a || null != b || 0 < carryOver){

            int sum = carryOver;
            if( null != a ) sum += a.getVal();
            if( null != b ) sum += b.getVal();

            if( 0 < sum/10 )
                carryOver = sum / 10;
            else
                carryOver = 0;

            sum = sum % 10;

            if( null == r) {
                r = initialNode;
                r.setVal(sum);
            }
            else {
                ListNode next = ListNode.builder().val(sum).build();
                r.setNext(next);
                r = next;
            }

            if(null != a) a = a.getNext();
            if(null != b) b = b.getNext();
        }

        return initialNode;
    }
}
