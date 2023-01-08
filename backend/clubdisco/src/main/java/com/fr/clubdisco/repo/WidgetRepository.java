package com.fr.clubdisco.repo;

import com.fr.clubdisco.model.Widget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WidgetRepository extends JpaRepository<Widget, Long> {

    Widget findWidgetById(Long id);

    boolean existsByName(String name);
}
