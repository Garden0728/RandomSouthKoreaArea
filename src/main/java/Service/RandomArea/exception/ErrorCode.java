package Service.RandomArea.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode implements CoderInterface{
    NOT_FOUND(-200, "NOT Found Address"),
    RETRY_MAX_COUNT_EXCEEDED(-201, "Retry Max Count Exceeded"),
    MISS_COORDINATE(-202, "Missing Coordinate"),
    NOT_FOUND_POLYGON(-203, "Not Found Polygon"),
    SUCCESS(200, "Success");

    private final Integer code;
    private final String message;


}
