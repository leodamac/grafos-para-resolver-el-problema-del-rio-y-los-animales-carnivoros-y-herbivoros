/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.decision;

/**
 *
 * @author Leo
 */
public class Barca {
    int carnivoros;
    int herviboros;
    char pos;
    
    public Barca(){
        this.carnivoros = 0;
        this.herviboros = 0;
        this.pos = 'd';
    }

    public Barca(int carnivoros, int herviboros, char pos) {
        this.carnivoros = carnivoros;
        this.herviboros = herviboros;
        this.pos = pos;
    }
    
    public Barca(char pos) {
        this.carnivoros = 0;
        this.herviboros = 0;
        this.pos = pos;
    }

    public Barca(Barca barca){
        this(barca.getNCarnivoros(), barca.getNHerviboros(), barca.getPos());
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
    
    public void moverPos(char pos){
        this.pos = pos;
    }
    
    public void moverDerecha(){
        moverPos('d');
    }
    
    public void moverIzquierda(){
        moverPos('i');
    }
    
    public boolean isInDerecha(){
        return this.pos == 'd';
    }
    
    public boolean isInIzquierda(){
        return this.pos == 'i';
    }
    
    public char getPos(){
        return this.pos;
    }
    
    @Override
    public boolean equals(Object obj){
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }
        
        final Barca b = (Barca) obj;
        
        return this.pos == b.getPos();
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + this.pos;
        return hash;
    }
}
