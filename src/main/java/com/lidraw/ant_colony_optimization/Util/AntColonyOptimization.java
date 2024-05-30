package com.lidraw.ant_colony_optimization.Util;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.Random;

public class AntColonyOptimization {

    public static final int MAX_ITERATIONS = 100;
    public static final int NUM_ANTS = 20;
    private static final double ALPHA = 1.0; // коэффициент влияния феромона
    private static final double BETA = 2.0; // коэффициент влияния дистанции
    private static final double RHO = 0.5; // коэффициент испарения феромона
    private static final double Q = 500; // количество феромона, добавляемого каждой муравьиной колонией

    private int numCities;
    private String[] cities;
    private Double[][] distances;
    private Double[][] pheromones;
    private int[][] antsTours;
    private Random random;

    public AntColonyOptimization(int numCities, String[] cities, Double[][] distances) {
        this.numCities = numCities;
        this.cities = cities;
        this.distances = distances;
        this.random = new Random();
        this.antsTours = new int[NUM_ANTS][numCities];
        this.pheromones = new Double[numCities][numCities];

        double initialValue = 1.0 / numCities;
        for (int i = 0; i < numCities; i++) {
            Arrays.fill(pheromones[i], initialValue);
        }
    }

    public int[] findBestTour() {
        System.out.println(cities.length);
        System.out.println(Arrays.deepToString(distances));
        int[] bestTour = null;
        double bestTourLength = Double.POSITIVE_INFINITY;

        for (int iteration = 0; iteration < MAX_ITERATIONS; iteration++) {
            for (int ant = 0; ant < NUM_ANTS; ant++) {
                int startCity = random.nextInt(numCities);
                antsTours[ant][0] = startCity;
                boolean[] visited = new boolean[numCities];
                visited[startCity] = true;

                for (int cityIndex = 1; cityIndex < numCities; cityIndex++) {
                    int currentCity = antsTours[ant][cityIndex - 1];
                    int nextCity = chooseNextCity(currentCity, visited);
                    antsTours[ant][cityIndex] = nextCity;
                    visited[nextCity] = true;
                }
            }

            updatePheromones();

            for (int ant = 0; ant < NUM_ANTS; ant++) {
                double tourLength = calculateTourLength(antsTours[ant]);
                if (tourLength < bestTourLength) {
                    bestTourLength = tourLength;
                    bestTour = Arrays.copyOf(antsTours[ant], numCities);
                }
            }
        }

        return bestTour;
    }

    private int chooseNextCity(int currentCity, boolean[] visited) {
        double[] probabilities = new double[numCities];
        double totalProbability = 0.0;

        for (int i = 0; i < numCities; i++) {
            if (!visited[i]) {
                probabilities[i] = Math.pow(pheromones[currentCity][i], ALPHA) * Math.pow(1.0 / distances[currentCity][i], BETA);
                totalProbability += probabilities[i];
            }
        }

        double randomValue = random.nextDouble() * totalProbability;
        double cumulativeProbability = 0.0;

        for (int i = 0; i < numCities; i++) {
            if (!visited[i]) {
                cumulativeProbability += probabilities[i];
                if (cumulativeProbability >= randomValue) {
                    return i;
                }
            }
        }

        for (int i = 0; i < numCities; i++) {
            if (!visited[i]) {
                return i;
            }
        }

        return currentCity;
    }

    private void updatePheromones() {
        for (int i = 0; i < numCities; i++) {
            for (int j = 0; j < numCities; j++) {
                pheromones[i][j] *= (1.0 - RHO);
            }
        }

        for (int ant = 0; ant < NUM_ANTS; ant++) {
            double tourLength = calculateTourLength(antsTours[ant]);
            for (int i = 0; i < numCities - 1; i++) {
                int from = antsTours[ant][i];
                int to = antsTours[ant][i + 1];
                pheromones[from][to] += Q / tourLength;
                pheromones[to][from] += Q / tourLength;
            }
        }
    }

    public double calculateTourLength(int[] tour) {
        double length = 0.0;
        for (int i = 0; i < numCities - 1; i++) {
            length += distances[tour[i]][tour[i + 1]];
        }
        length += distances[tour[numCities - 1]][tour[0]];
        return length;
    }
}
