package net.segoia.event.eventbus.vo.security;

public class InvalidSignatureException extends Exception{

    /**
     * 
     */
    private static final long serialVersionUID = -7090494788929497047L;

    public InvalidSignatureException() {
	super();
	// TODO Auto-generated constructor stub
    }

    public InvalidSignatureException(String message, Throwable cause, boolean enableSuppression,
	    boolean writableStackTrace) {
	super(message, cause, enableSuppression, writableStackTrace);
	// TODO Auto-generated constructor stub
    }

    public InvalidSignatureException(String message, Throwable cause) {
	super(message, cause);
	// TODO Auto-generated constructor stub
    }

    public InvalidSignatureException(String message) {
	super(message);
	// TODO Auto-generated constructor stub
    }

    public InvalidSignatureException(Throwable cause) {
	super(cause);
	// TODO Auto-generated constructor stub
    }

}
