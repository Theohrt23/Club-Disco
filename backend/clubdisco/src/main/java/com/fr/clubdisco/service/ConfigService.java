package com.fr.clubdisco.service;

import com.fr.clubdisco.exception.config.ConfigConflictException;
import com.fr.clubdisco.exception.config.ConfigNotFoundException;
import com.fr.clubdisco.model.Config;

public interface ConfigService {

    Config createConfig(Long UserId) throws ConfigConflictException;

    Config setZippopotamConfig(Long configId, String zip_code, String region) throws ConfigNotFoundException;

    Config setWeatherConfig(Long configId, String city) throws ConfigNotFoundException;

    Config getUserConfig(Long userId) throws ConfigNotFoundException;
}
