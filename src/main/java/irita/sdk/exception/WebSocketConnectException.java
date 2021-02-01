package irita.sdk.exception;

public class WebSocketConnectException extends RuntimeException {
    private static final long serialVersionUID = 4868608554854884110L;

    public WebSocketConnectException() {
    }

    public WebSocketConnectException(String message) {
        super(message);
    }

    public WebSocketConnectException(String message, Throwable cause) {
        super(message, cause);
    }

    public WebSocketConnectException(Throwable cause) {
        super(cause);
    }
}
