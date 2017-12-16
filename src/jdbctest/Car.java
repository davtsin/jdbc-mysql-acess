/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbctest;

/**
 *
 * @author denis
 */
public class Car {

    private String model;
    private float price;

    public Car(String model, float price) {
        this.model = model;
        this.price = price;
    }

    public String getModel() {
        return model;
    }

    public float getPrice() {
        return price;
    }
    
    @Override
    public String toString() {
        return "Car{" + "model=" + model + ", price=" + price + '}';
    }

}
