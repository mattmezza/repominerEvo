package net.sf.jeasyorm;

public final class RuntimeSQLException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public RuntimeSQLException(final Throwable cause) {
        super(cause);
    }

    public RuntimeSQLException(final String message) {
        super(message);
    }

    public RuntimeSQLException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
