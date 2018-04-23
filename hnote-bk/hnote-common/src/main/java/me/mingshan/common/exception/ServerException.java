package me.mingshan.common.exception;

import me.mingshan.common.model.ResultModel;
import org.springframework.http.HttpStatus;

/**
 * The exception of server.
 * @Author: Minsghan
 * @Date: Created in 20:11 2017/10/8
 * @Modified By:
 */
public class ServerException extends RuntimeException {
    private static final long serialVersionUID = -8183259784734482522L;
    private ResultModel result;
    private HttpStatus httpStatus;

    public ServerException() { }

    public ServerException(ResultModel result, HttpStatus httpStatus) {
        this.result = result;
        this.httpStatus = httpStatus;
    }

    public ResultModel getResult() {
        return this.result;
    }

    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }
}
