package com.tgedr.labs.commons;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ListNode {

    private int val;
    private ListNode next;


    public static ListNode reverse(ListNode a){
        if( null == a )
            throw new IllegalArgumentException("null input !!!");

        ListNode r = ListNode.builder().val(a.val).build();

        while( null != a ) {

            if( null != a.getNext() )
                r = ListNode.builder().val(a.getNext().val).next(r).build();

            a = a.getNext();
        }

        return r;
    }
}
