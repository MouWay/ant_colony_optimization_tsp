package com.lidraw.ant_colony_optimization.Service;

import com.lidraw.ant_colony_optimization.Model.ACOLog;
import com.lidraw.ant_colony_optimization.Model.ACORequest;
import com.lidraw.ant_colony_optimization.Model.ACOResponse;
import com.lidraw.ant_colony_optimization.Repository.IACORepository;
import com.lidraw.ant_colony_optimization.Service.Interface.IACOService;
import com.lidraw.ant_colony_optimization.Util.AntColonyOptimization;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ACOService implements IACOService {
    private IACORepository repository;

    @Override
    public ACOResponse solve(ACORequest request) {
        var aco = new AntColonyOptimization(request.getCities().length, request.getCities(), request.getDistances());
        var bestTour = aco.findBestTour();
        var bestTourString = new String[bestTour.length];

        for (int i = 0; i < bestTour.length; i++) {
            int currentCityIndex = bestTour[i];
            bestTourString[i] = request.getCities()[currentCityIndex];
        }

        double bestTourLength = aco.calculateTourLength(bestTour);
        saveResult(request.getName(), AntColonyOptimization.MAX_ITERATIONS, AntColonyOptimization.NUM_ANTS, bestTourLength);
        return new ACOResponse(bestTourLength, bestTourString);
    }

    private void saveResult(String name, int iterations, int antsCount, Double tourLength){
        var result = new ACOLog();
        result.setName(name);
        result.setIterations(iterations);
        result.setAntCount(antsCount);
        result.setResult(tourLength);
        repository.save(result);
    }
}
