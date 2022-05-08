package ru.levprav.orionnitrogenerator.retrofit.models;

public class CodeRequestModel {
    private String code;
    private String message;
    private String requestCode;

    public void setRequestCode(String requestCode) {
        this.requestCode = requestCode;
    }

    public String getRequestCode() {
        return requestCode;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
