package com.example.api.controller;

import com.example.api.model.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.awt.geom.Line2D;
import java.util.List;

//@RestController
//@RequestMapping("/polygons")
@RestController
public class SmartContractsController {

    private static  final Logger logger = LoggerFactory.getLogger(SmartContractsController.class);

    @GetMapping(value = "/")
    public ResponseEntity<String> pong(){
        logger.info("Démarrage des services OK .....");
        return new ResponseEntity<>("Réponse du serveur: " + HttpStatus.OK.name(), HttpStatus.OK);
    }

    @PostMapping(value = "/polygons")
    public boolean checkService(@RequestBody List<Point> coordinates) {

        System.out.println(coordinates.get(0).toString());

        if (coordinates.size() < 3) { // Un polygone doit avoir au moins 3 points
            return false;
        }
        // Vérifier si le dernier point est identique au premier point pour former une boucle
        if (!coordinates.get(0).equals(coordinates.get(coordinates.size() - 1))) {
            return false;
        }
        // Vérifier si les segments formés par les points se croisent
        for (int i = 0; i < coordinates.size() - 2; i++) {
            for (int j = i + 2; j < coordinates.size() - 1; j++) {
                if (Line2D.linesIntersect(
                        coordinates.get(i).getLatitude(),
                        coordinates.get(i).getLongitude(),
                        coordinates.get(i + 1).getLatitude(),
                        coordinates.get(i + 1).getLongitude(),
                        coordinates.get(j).getLatitude(),
                        coordinates.get(j).getLongitude(),
                        coordinates.get(j + 1).getLatitude(),
                        coordinates.get(j + 1).getLongitude())) {
                    return false;
                }
            }
        }
        return true;
    }
}
