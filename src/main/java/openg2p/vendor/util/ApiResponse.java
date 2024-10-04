package openg2p.vendor.util;

public class ApiResponse<T> {
    private int status;
    private String message;
    private String datetime;
    private T data;

    // Constructors, Getters, and Setters
    public ApiResponse(int status, String message, String datetime, T data) {
        this.status = status;
        this.message = message;
        this.datetime = datetime;
        this.data = data;
    }

    // Getters
    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getDatetime() {
        return datetime;
    }

    public T getData() {
        return data;
    }
}

