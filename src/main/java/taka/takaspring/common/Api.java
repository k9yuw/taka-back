package taka.takaspring.common;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Api<T> {

    public static final String SUCCESS_STATUS = "success";
    public static final String CREATED_SUCCESS_STATUS = "created success";
    public static final String FAIL_STATUS = "fail";
    public static final String ERROR_STATUS = "error";

    private String status;
    private String message;
    private T data;
    private Integer statusCode;

    public static <T> Api<T> ok(T data, String message) {
        return Api.<T>builder()
                .status(Api.SUCCESS_STATUS)
                .message(message)
                .data(data)
                .statusCode(200)
                .build();
    }

    public static <T> Api<T> ok(T data, String message, Pagination pagination) {
        return Api.<T>builder()
                .status(Api.SUCCESS_STATUS)
                .message(message)
                .data(data)
                .statusCode(200)
                .build();
    }

    public static <T> Api<T> created(T data, String message) {
        return Api.<T>builder()
                .status(Api.CREATED_SUCCESS_STATUS)
                .message(message)
                .data(data)
                .statusCode(201)
                .build();
    }

    public static Api error(String message) {
        return Api.builder()
                .status(Api.ERROR_STATUS)
                .message(message)
                .statusCode(500)
                .build();
    }

    public static Api fail(String message) {
        return Api.builder()
                .status(Api.FAIL_STATUS)
                .message(message)
                .statusCode(400)
                .build();
    }
}

