package ru.aston.purchases.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UnsupportedStatus extends RuntimeException {
    private final String message;
}
