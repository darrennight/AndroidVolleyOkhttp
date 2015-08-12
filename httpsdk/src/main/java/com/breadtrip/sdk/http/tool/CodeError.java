package com.breadtrip.sdk.http.tool;


import com.android.volley.VolleyError;

/**
 * Indicates that the error responded with an error response.
 */
@SuppressWarnings("serial")
public class CodeError extends VolleyError {

    private String code;

    public CodeError(String code) {
        this(code, null);
    }

    public CodeError(String code, String exceptionMessage) {
        super(exceptionMessage);

        this.setCode(code);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
