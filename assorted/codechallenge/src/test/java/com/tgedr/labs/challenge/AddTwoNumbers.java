package com.tgedr.labs.challenge;

import com.tgedr.labs.commons.ListNode;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Assert;
import org.junit.Test;

public class AddTwoNumbers {

    Solution<Pair<ListNode,ListNode>,ListNode> solution = new com.tgedr.labs.challenge.leetcode.AddTwoNumbers();
    @Test
    public void test() throws Exception {

        ListNode a = ListNode.builder().val(2).next(
            ListNode.builder().val(4).next(
                    ListNode.builder().val(3).build()).build()).build();
        ListNode b = ListNode.builder().val(5).next(
                ListNode.builder().val(6).next(
                        ListNode.builder().val(4).build()).build()).build();
        ListNode expected = ListNode.builder().val(7).next(
                ListNode.builder().val(0).next(
                        ListNode.builder().val(8).build()).build()).build();

        Assert.assertEquals(expected, solution.goForIt(Pair.of(a,b)));

    }

    @Test
    public void test2() throws Exception {

        ListNode a = ListNode.builder().val(5).build();
        ListNode b = ListNode.builder().val(5).build();
        ListNode expected = ListNode.builder().val(0).next(
                ListNode.builder().val(1).build()).build();

        Assert.assertEquals(expected, solution.goForIt(Pair.of(a,b)));

    }

}
