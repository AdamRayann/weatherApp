package org.spring.util;


import org.spring.model.CityTemperature;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvWriter {

    public static void saveToCsv(List<CityTemperature> data, String filePath) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("City,Temperature\n");
            for (CityTemperature ct : data) {
                writer.write(String.format("%s,%.1f\n", ct.getCity(), ct.getTemperature()));
            }
        }
    }
}
