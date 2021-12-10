/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package workWithDB;
import java.util.List;
public interface DAO<T> {
    boolean create(T entity);
    boolean update(T entity);
    boolean delete(String id);
    List<T> getAll();
    T getByID(String ID);
}