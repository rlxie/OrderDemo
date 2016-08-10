package com.thoughtworks.orderdemo.resources;

/**
 * Created by rlxie on 8/9/16.
 */
public interface Global {

    final int SUCCESS = 0;

    final int ORDER = 10000;

    interface OrderErrorCode{
        final int ADD_ORDER_FAIL = ORDER + 1;

        final int DEL_ORDER_FAIL = ORDER + 2;

        final int ORDER_NO_HAS_NOT_NULL = ORDER + 3;

        final int CAN_NOT_FOUND_ORDER_BY_ORDERNO = ORDER + 4;

        final int ORDER_IS_ALREADY_EXIST = ORDER + 5;

        final int ORDER_CONTENT_HAS_NOT_NULL = ORDER + 6;
    }
}
