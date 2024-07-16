/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.decision;
/**
 *
 * @author Leo
 */
public final class Orilla{
    int carnivoros;
    int herviboros;
    char pos;
    public Orilla (char pos){
        this.carnivoros = 0;
        this.herviboros = 0;
        this.pos = pos;
        
    }
    
    public Orilla(Orilla orilla){
        this(orilla.getNCarnivoros(), orilla.getNHerviboros(), orilla.getPos());
    }
    
    public Orilla(int carnivoros, int herviboros, char pos){
        this.carnivoros = carnivoros;
        this.herviboros = herviboros;
        this.pos = pos;
    }
    
    public void addAnimal(Animal animal){
        if(animal.isCarnivoro()){
            this.carnivoros++;
        }else{
            this.herviboros++;
        }
    }
    
    public void removeAnimal(Animal animal){
        if(animal.isCarnivoro()){
            this.carnivoros--;
        }else{
            this.herviboros--;
        }
    }
    
    public int getNAnimales(){
        return getNHerviboros()+getNCarnivoros();
    }
    
    public int getNCarnivoros(){
        return this.carnivoros;
    }
    
    public int getNHerviboros(){
        return this.herviboros;
    }

    public char getPos() {
        return pos;
    }
    

    
    public boolean isCarnivorosMayorHerviboros(){
        return this.carnivoros > this.herviboros && this.herviboros>=1;
    }
    
    @Override
    public boolean equals(Object obj){
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }
        
        final Orilla o = (Orilla) obj;
        
        return this.carnivoros == o.getNCarnivoros() && this.herviboros == o.getNHerviboros() && this.pos == o.getPos();
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 43 * hash + this.carnivoros;
        hash = 43 * hash + this.herviboros;
        hash = 43 * hash + this.pos;
        return hash;
    }
}
