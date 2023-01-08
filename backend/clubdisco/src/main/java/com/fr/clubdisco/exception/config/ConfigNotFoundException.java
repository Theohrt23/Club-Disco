package com.fr.clubdisco.exception.config;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Config not found")
public class ConfigNotFoundException extends Exception{
}
