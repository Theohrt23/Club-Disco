package com.fr.clubdisco.exception.config;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Config already exist")
public class ConfigConflictException extends Exception{
}
