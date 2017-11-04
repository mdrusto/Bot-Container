package yafta98.botcontainer.utilities;

public class WrapperException extends RuntimeException {
	
	private static final long serialVersionUID = 2991434480174811492L;

	public WrapperException(Throwable cause) {
		super(cause);
	}
	
	public WrapperException(String message, Throwable cause) {
		super(message, cause);
	}
}
