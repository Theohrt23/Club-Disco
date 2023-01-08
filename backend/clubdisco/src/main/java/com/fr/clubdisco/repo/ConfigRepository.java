package com.fr.clubdisco.repo;

import com.fr.clubdisco.model.Config;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigRepository extends JpaRepository<Config, Long> {

    Config findConfigById(Long id);

    Config findConfigByUserId(Long user_id);
}
