package uz.shaxzod.ticketapp.exceptions;


import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import uz.shaxzod.ticketapp.models.responseDto.ApiErrorResponse;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
//    private final TelegramBotService telegramBotService;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  @NonNull HttpHeaders headers,
                                                                  @NonNull HttpStatusCode status,
                                                                  @NonNull WebRequest request) {
        Map<String, String> validationErrors = new HashMap<>();
        List<ObjectError> validationErrorList = ex.getBindingResult().getAllErrors();

        for(int i = 0; i < validationErrorList.size() && i < 1; i++) {
            String errorMessage = validationErrorList.get(i).getDefaultMessage();
            validationErrors.put("message", errorMessage);
        }
        return new ResponseEntity<>(new ApiErrorResponse(validationErrors.get("message")), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CustomNotFoundException.class)
    public ApiErrorResponse handleNotFoundException(CustomNotFoundException exception) {
        return new ApiErrorResponse(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(CustomConflictException.class)
    public ApiErrorResponse handleAlreadyExistException(CustomConflictException exception) {
        return new ApiErrorResponse(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CustomBadRequestException.class)
    public ApiErrorResponse handleBadRequestException(CustomBadRequestException exception) {
        return new ApiErrorResponse(exception.getMessage());
    }


    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(InvalidTokenException.class)
    public ApiErrorResponse handleInvalidTokenException(InvalidTokenException exception) {
        return new ApiErrorResponse(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(CustomInvalidDtoException.class)
    public ApiErrorResponse handleInvalidDtoException(CustomInvalidDtoException exception) {
        return new ApiErrorResponse(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(CustomAlreadyExistException.class)
    public ApiErrorResponse handleCustomAlreadyExistException(CustomAlreadyExistException exception) {
        return new ApiErrorResponse(exception.getMessage());
    }


//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ExceptionHandler(CustomInternalErrorException.class)
//    public ApiErrorResponse handleInternalErrorException(CustomInternalErrorException exception) {
//        executorService.submit(() -> telegramBotService.sendException(exception.getMessage()));
//        return new ApiErrorResponse(exception.getMessage());
//    }

//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ExceptionHandler(Exception.class)
//    public ApiErrorResponse handleException(Exception exception) {
//        exception.printStackTrace();
//        executorService.submit(() -> telegramBotService.sendException(exception.getMessage()));
//        return new ApiErrorResponse(exception.getMessage());
//    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(SmsCodeException.class)
    public ApiErrorResponse handleSmsCodeException(SmsCodeException exception) {
        return new ApiErrorResponse(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(SmsApiServerException.class)
    public ApiErrorResponse handleSmsApiServerException(SmsApiServerException exception) {
        return new ApiErrorResponse(exception.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ApiErrorResponse handleDataIntegrityViolationException(DataIntegrityViolationException ex){
        return new ApiErrorResponse(ex.getMessage());
    }


//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(MalformedJwtException.class)
//    public ApiErrorResponse handleMalformedJwtException(MalformedJwtException e) {
//        return new ApiErrorResponse(e.getMessage());
//    }


/*    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UsernameNotFoundException.class)
    public ApiErrorResponse handleUsernameNotFoundException(UsernameNotFoundException e) {
        return new ApiErrorResponse("user name or password is incorrect");
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(BadCredentialsException.class)
    public ApiErrorResponse handleBadCredentialsException(BadCredentialsException e) {
        return new ApiErrorResponse("user name or password is incorrect");
    }
*/
//    @ResponseStatus(HttpStatus.UNAUTHORIZED)
//    @ExceptionHandler(ExpiredJwtException.class)
//    public ApiErrorResponse handleExpiredJwtException(ExpiredJwtException exception) {
//        return new ApiErrorResponse(exception.getMessage());
//    }

//    @ResponseStatus(HttpStatus.UNAUTHORIZED)
//    @ExceptionHandler(SignatureException.class)
//    public ApiErrorResponse handleSignatureException(SignatureException exception) {
//        return new ApiErrorResponse(exception.getMessage());
//    }

/*    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public ApiErrorResponse handleAccessDeniedException(AccessDeniedException exception) {
        return new ApiErrorResponse(exception.getMessage());
    }*/

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CustomIllegalArgumentException.class)
    public ApiErrorResponse handleCustomIllegalArgumentException(CustomIllegalArgumentException exception) {
        return new ApiErrorResponse(exception.getMessage());
    }


}
