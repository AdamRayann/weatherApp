package org.spring.app;

import org.spring.model.CityTemperature;
import org.spring.service.CityService;
import org.spring.service.WeatherService;
import org.spring.util.CsvWriter;
import org.spring.util.RateLimiter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        try {
            CityService cityService = new CityService();
            WeatherService weatherService = new WeatherService();
            RateLimiter limiter = new RateLimiter(60); // 60 rpm

            List<String> cities = cityService.fetchAndValidateCities();
            ExecutorService executor = Executors.newFixedThreadPool(5);
            List<Future<CityTemperature>> futures = new ArrayList<>();

            for (String city : cities) {
                Future<CityTemperature> future = executor.submit(() -> {
                    limiter.acquire(); // ensure rate limit

                    try {
                        double temp = weatherService.getTemperatureForCity(city);
                        return new CityTemperature(city, temp);
                    } catch (Exception e) {
                        System.err.printf("%s : [ERROR] %s%n", city, e.getMessage());
                        return null;
                    }
                });
                futures.add(future);
            }

            executor.shutdown();
            executor.awaitTermination(1, TimeUnit.MINUTES);

            // Collect results
            List<CityTemperature> results = new ArrayList<>();
            for (Future<CityTemperature> future : futures) {
                CityTemperature ct = future.get();
                if (ct != null) {
                    results.add(ct);
                }
            }

            results.sort((a, b) -> Double.compare(b.getTemperature(), a.getTemperature()));//sorting

            System.out.println("\nTemperatures (High to Low):");
            for (CityTemperature ct : results) {
                System.out.printf("%s : %.1f°C%n", ct.getCity(), ct.getTemperature());
            }
            // Save to CSV
            try {
                CsvWriter.saveToCsv(results, "output.csv");
                System.out.println("\nResults saved to output.csv");
            } catch (IOException e) {
                System.err.println("Failed to save results to CSV: " + e.getMessage());
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}