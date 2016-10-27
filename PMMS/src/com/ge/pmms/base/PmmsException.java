package com.ge.pmms.base;

public class PmmsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3320593988261097968L;

	public PmmsException(Throwable cause,String msg)
    {  
        super(msg,cause);  
    }
	
	public PmmsException(String msg)
    {  
        super(msg);  
    }
}
