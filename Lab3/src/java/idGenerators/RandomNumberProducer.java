/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package idGenerators;

import java.util.Random;
import javax.enterprise.inject.Produces;

/**
 *
 * @author 1
 */
public class RandomNumberProducer {
    @Produces @RandomNumbers
    public long generate(){
       return Math.abs(new Random().nextInt());
    }
}
