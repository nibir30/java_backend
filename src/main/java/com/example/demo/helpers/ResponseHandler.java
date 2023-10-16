package com.example.demo.helpers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseHandler {
    public static ResponseEntity<Map<String, Object>> generateResponse(HttpStatus status, boolean error, String message,
            Object responseObj) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            map.put("timestamp", new Date());
            map.put("status", status.value());
            map.put("isSuccess", error);
            map.put("message", message);
            map.put("data", responseObj);
            return new ResponseEntity<Map<String, Object>>(map, status);
        } catch (Exception e) {
            map.clear();
            map.put("timestamp", new Date());
            map.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            map.put("isSuccess", false);
            map.put("message", e.getMessage());
            map.put("data", null);
            return new ResponseEntity<Map<String, Object>>(map, status);
        }
    }
}
