/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package idGenerators;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.decorator.Delegate;
import javax.inject.Inject;
import javax.decorator.Decorator;

/**
 *
 * @author User
 */

@Decorator
public class IdWithDateDecorator implements IdGenerator{
    
    @Inject @Delegate @NumberInDB
    private IdGenerator idGenerator;

    @Override
    public String generate() {
        return idGenerator.generate()+LocalDateTime.now().format(DateTimeFormatter.ofPattern("_dd-MM-yy_HH:mm:ss"));
    }
    
}
