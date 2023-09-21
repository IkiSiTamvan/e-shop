package com.sitamvan.eshop.util;

import java.util.List;

import org.springframework.http.HttpStatus;

public class ErrorDto {

        private final int status;
        private final String error;
        private final String message;
        private List<String> detailedMessages;

        public int getStatus() {
            return status;
        }

        public String getError() {
            return error;
        }

        public String getMessage() {
            return message;
        }

        public List<String> getDetailedMessages() {
            return detailedMessages;
        }

        public void setDetailedMessages(List<String> detailedMessages) {
            this.detailedMessages = detailedMessages;
        }

        public ErrorDto(HttpStatus httpStatus, String message) {
            this.status = httpStatus.value();
            this.error = httpStatus.getReasonPhrase();
            this.message = message;
        }

        public ErrorDto(int status, String message) {
            this.status = status;
            error = null;
            this.message = message;
        }

    }
