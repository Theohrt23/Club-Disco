package com.fr.clubdisco.controller;

import com.fr.clubdisco.exception.config.ConfigConflictException;
import com.fr.clubdisco.exception.config.ConfigNotFoundException;
import com.fr.clubdisco.model.Config;
import com.fr.clubdisco.service.ConfigService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/config")
public class ConfigController {

    private final ConfigService configService;

    @PostMapping("{userId}")
    @PreAuthorize("hasRole('USER')")
    public Config createConfig(@PathVariable(value = "userId")Long userId) throws ConfigConflictException {
        return configService.createConfig(userId);
    }

    @PutMapping("/{configId}/{region}/{zip_code}")
    @PreAuthorize("hasRole('USER')")
    public Config setZipConfig(@PathVariable(value = "configId") Long configId, @PathVariable(value = "region") String region, @PathVariable(value = "zip_code") String zip_code) throws ConfigNotFoundException {
        return configService.setZippopotamConfig(configId,region,zip_code);
    }

    @PutMapping("/{configId}/{city}")
    @PreAuthorize("hasRole('USER')")
    public Config setWeatherConfig(@PathVariable(value = "configId") Long configId, @PathVariable(value = "city") String city) throws ConfigNotFoundException {
        return configService.setWeatherConfig(configId,city);
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('USER')")
    public Config getConfigByUserId(@PathVariable(value = "userId") Long userId) throws ConfigNotFoundException {
        return configService.getUserConfig(userId);
    }
}
