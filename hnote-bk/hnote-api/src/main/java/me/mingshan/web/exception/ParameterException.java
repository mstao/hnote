package me.mingshan.web.exception;

/**
 * The parameters of exception.
 * @Author: Minsghan
 * @Date: Created in 20:10 2017/10/8
 */
public class ParameterException extends RuntimeException {
    private static final long serialVersionUID = -7235137272015389906L;
    private String message;

    public ParameterException() { }

    public ParameterException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
