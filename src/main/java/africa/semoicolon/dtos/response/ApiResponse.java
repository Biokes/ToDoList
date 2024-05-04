package africa.semoicolon.dtos.response;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ApiResponse {
    private boolean isSuccessful;
    private Object data;
}
