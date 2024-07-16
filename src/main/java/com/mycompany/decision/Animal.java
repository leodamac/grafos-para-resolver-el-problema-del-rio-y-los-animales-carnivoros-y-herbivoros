/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.decision;

/**
 *
 * @author Leo
 */
public class Animal {
    boolean carnivoro = false;
    
    public Animal(boolean carnivoro){
        this.carnivoro = carnivoro;
    }
    
    public boolean isCarnivoro(){
        return this.carnivoro;
    }
}
