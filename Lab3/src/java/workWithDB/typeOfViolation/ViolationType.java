/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workWithDB.typeOfViolation;

/**
 *
 * @author 1
 */
public class ViolationType {
    private String name;
    private String typeOfLiability;
    private float minFine;
    private float maxFine;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeOfLiability() {
        return typeOfLiability;
    }

    public void setTypeOfLiability(String typeOfLiability) {
        this.typeOfLiability = typeOfLiability;
    }

    public float getMinFine() {
        return minFine;
    }

    public void setMinFine(float minFine) {
        this.minFine = minFine;
    }

    public float getMaxFine() {
        return maxFine;
    }

    public void setMaxFine(float maxFine) {
        this.maxFine = maxFine;
    }
    
    public ViolationType(String name, String typeOfLiability, float minFine, float maxFine) {
        this.name = name;
        this.typeOfLiability = typeOfLiability;
        this.minFine = minFine;
        this.maxFine = maxFine;
    }
    
}
