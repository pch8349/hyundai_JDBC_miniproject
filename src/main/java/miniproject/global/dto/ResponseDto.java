package miniproject.global.dto;

import lombok.Getter;

@Getter
public class ResponseDto<T> {
    private boolean success;
    private String message;
    private T data;

    public static <T> ResponseDto<T> success(){
        ResponseDto<T> dto = new ResponseDto<T>();
        dto.success = true;
        return dto;
    }

    public static <T> ResponseDto<T> success(T data){
        ResponseDto<T> response = new ResponseDto<>();
        response.success = true;
        response.data = data;
        return response;
    }

    public static <T> ResponseDto<T> fail(){
        ResponseDto<T> response = new ResponseDto<T>();
        response.success = false;
        return response;
    }

    public static <T> ResponseDto<T> fail(String message){
        ResponseDto<T> response = new ResponseDto<>();
        response.success = false;
        response.message = message;
        return response;
    }
}
