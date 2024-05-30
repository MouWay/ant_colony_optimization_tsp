package com.lidraw.ant_colony_optimization.Service.Interface;

import com.lidraw.ant_colony_optimization.Model.ACORequest;
import com.lidraw.ant_colony_optimization.Model.ACOResponse;

public interface IACOService {
    ACOResponse solve(ACORequest request);
}
