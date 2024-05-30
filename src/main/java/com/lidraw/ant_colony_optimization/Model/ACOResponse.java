package com.lidraw.ant_colony_optimization.Model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ACOResponse {
    private Double length;
    private String[] tour;
}
