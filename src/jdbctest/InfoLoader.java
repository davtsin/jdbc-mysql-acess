/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbctest;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author denis
 */
public class InfoLoader {
    
    public static Set<Car> getCarsFromFile(String fileName) {
        Set<Car> cars = new HashSet<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] carInfo = line.split("=");
                cars.add(new Car(carInfo[0], Float.parseFloat(carInfo[1])));
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(JdbcTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(JdbcTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cars;
    }
    
    // test
    public static void main(String[] args) {
        System.out.println(InfoLoader.getCarsFromFile("resources/cars.txt"));
    }
}
