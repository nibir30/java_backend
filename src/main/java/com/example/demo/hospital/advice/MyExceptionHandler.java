// package com.example.demo.hospital.advice;

// import org.springframework.web.bind.MethodArgumentNotValidException;
// import org.springframework.web.bind.annotation.ExceptionHandler;
// import org.springframework.web.bind.annotation.ResponseStatus;
// import org.springframework.web.bind.annotation.RestControllerAdvice;

// import com.example.demo.helpers.DebugHelper;

// import java.util.HashMap;
// import java.util.Map;

// import org.springframework.http.HttpStatus;

// @RestControllerAdvice
// public class MyExceptionHandler {
// String errorMessage = "";

// @ResponseStatus(HttpStatus.BAD_REQUEST)
// @ExceptionHandler(MethodArgumentNotValidException.class)
// public Map<String, String>
// handleInvalidArgument(MethodArgumentNotValidException ex) {
// ex.getBindingResult().getFieldErrors().forEach(error -> {
// errorMessage = errorMessage + error.getField() + ": " +
// error.getDefaultMessage() + ", ";
// DebugHelper.printData(error.getDefaultMessage());

// });
// HashMap<String, String> error = new HashMap<>();
// error.put("message", errorMessage);
// DebugHelper.printData(errorMessage);
// return error;
// }
// }
//