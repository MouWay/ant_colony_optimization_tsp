package com.lidraw.ant_colony_optimization.Repository;

import com.lidraw.ant_colony_optimization.Model.ACOLog;
import org.springframework.data.repository.CrudRepository;

public interface IACORepository extends CrudRepository<ACOLog, Long> {
}
