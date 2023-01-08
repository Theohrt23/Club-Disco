package com.fr.clubdisco.service.impl;

import com.fr.clubdisco.exception.config.ConfigConflictException;
import com.fr.clubdisco.exception.config.ConfigNotFoundException;
import com.fr.clubdisco.model.Config;
import com.fr.clubdisco.repo.ConfigRepository;
import com.fr.clubdisco.service.ConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConfigServiceImpl implements ConfigService {

    private final ConfigRepository configRepository;

    @Override
    public Config createConfig(Long UserId) throws ConfigConflictException {
        Config config = new Config(UserId);
        configRepository.save(config);
        return config;
    }

    @Override
    public Config setZippopotamConfig(Long configId, String zip_code, String region) throws ConfigNotFoundException {
        if(!configRepository.existsById(configId)){
            throw new ConfigNotFoundException();
        }
        Config config = configRepository.findConfigById(configId);
        config.setZippopotam_region(region);
        config.setZippoptam_zip_code(zip_code);
        configRepository.save(config);
        return config;
    }

    @Override
    public Config setWeatherConfig(Long configId, String city) throws ConfigNotFoundException {
        if(!configRepository.existsById(configId)){
            throw new ConfigNotFoundException();
        }
        Config config = configRepository.findConfigById(configId);
        config.setWeather_city(city);
        configRepository.save(config);
        return config;
    }

    @Override
    public Config getUserConfig(Long userId) throws ConfigNotFoundException {
        if (configRepository.findConfigByUserId(userId) == null){
            throw new ConfigNotFoundException();
        }

        return configRepository.findConfigByUserId(userId);
    }


}
