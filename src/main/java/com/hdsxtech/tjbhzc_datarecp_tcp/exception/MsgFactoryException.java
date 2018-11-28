package com.hdsxtech.tjbhzc_datarecp_tcp.exception;


public class MsgFactoryException extends RuntimeException {

    private static final long serialVersionUID = 6315449332128372722L;

    public MsgFactoryException() {
        super();
    }

    public MsgFactoryException(String s) {
        super(s);
    }

    public MsgFactoryException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public MsgFactoryException(Throwable throwable) {
        super(throwable);
    }
}
