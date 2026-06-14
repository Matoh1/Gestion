package com.jedi.jedis.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jedi.jedis.model.Jedi;

@Repository
public interface JediRepository extends JpaRepository <Jedi, Integer>{
}

