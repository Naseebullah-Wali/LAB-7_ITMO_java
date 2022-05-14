package common.Interaction;

import com.sun.istack.internal.NotNull;

import java.io.Serializable;

public class Response implements Serializable {
    // class for get Response value

    private ResponseCode responseCode;
    private String responseBody;


    public Response(ResponseCode responseCode, String responseBody) {
        this.responseCode = responseCode;
        this.responseBody = responseBody;
    }

    public ResponseCode getResponseCode() {
        return responseCode;
    }

    public String getResponseBody() {
        return responseBody;
    }

    @Override
    public String toString() {
        return "Response: [ " +
                 responseCode + "," +
                 responseBody + "," +
                ']';
    }
}
