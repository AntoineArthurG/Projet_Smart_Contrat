package com.example.api.controller;

import com.example.api.model.Point;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.awt.geom.Line2D;
import java.util.List;

@RestController
@RequestMapping("/polygons")
public class SmartContractsController {

    @GetMapping
    public boolean checkService(@PathVariable List<Point> points) {

        if (points.size() < 3) { // Un polygone doit avoir au moins 3 points
            return false;
        }
        // Vérifier si le dernier point est identique au premier point pour former une boucle
        if (!points.get(0).equals(points.get(points.size() - 1))) {
            return false;
        }
        // Vérifier si les segments formés par les points se croisent
        for (int i = 0; i < points.size() - 2; i++) {
            for (int j = i + 2; j < points.size() - 1; j++) {
                if (Line2D.linesIntersect(
                        points.get(i).getLatitude(),
                        points.get(i).getLongitude(),
                        points.get(i + 1).getLatitude(),
                        points.get(i + 1).getLongitude(),
                        points.get(j).getLatitude(),
                        points.get(j).getLongitude(),
                        points.get(j + 1).getLatitude(),
                        points.get(j + 1).getLongitude())) {
                    return false;
                }
            }
        }
        return true;
    }
}
