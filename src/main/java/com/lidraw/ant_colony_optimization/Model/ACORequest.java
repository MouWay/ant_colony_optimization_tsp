package com.lidraw.ant_colony_optimization.Model;

import lombok.Data;

@Data
public class ACORequest {
    private String name;
    private String[] cities;
    private Double[][] distances;
}
