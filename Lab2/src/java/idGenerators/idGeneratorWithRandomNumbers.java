/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package idGenerators;

import javax.inject.Inject;
import javax.inject.Named;
@Named("RandNum")
public class idGeneratorWithRandomNumbers implements IdGenerator{

    @Inject @RandomNumbers
    long number;
    @Override
    public String generate() {
        return "viol_"+Long.toString(number);
    }
    
}
