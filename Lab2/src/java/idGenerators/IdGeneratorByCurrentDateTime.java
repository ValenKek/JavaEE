package idGenerators;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author 1
 */
@CurrentTime
public class IdGeneratorByCurrentDateTime implements IdGenerator{
    @Override
    public String generate(){
       LocalDateTime datetime = LocalDateTime.now();
       DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ddMMyyHHmmss");
       String id = datetime.format(dtf);
       return id;
    }
}
