/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package workWithDB.action;

import java.time.LocalDateTime;

/**
 *
 * @author User
 */
public class ActionWithViolationDB {
    public String violationId;
    public String actionType;
    public LocalDateTime time;
    public String info;

    public ActionWithViolationDB(String violationId, String actionType, LocalDateTime time, String info) {
        this.violationId = violationId;
        this.actionType = actionType;
        this.time = time;
        this.info = info;
    }
    
}
