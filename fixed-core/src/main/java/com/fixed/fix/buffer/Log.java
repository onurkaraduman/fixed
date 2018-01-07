package com.fixed.fix.buffer;

public interface Log {

    void onIncoming(String message);

    void onOutgoing(String message);
}
