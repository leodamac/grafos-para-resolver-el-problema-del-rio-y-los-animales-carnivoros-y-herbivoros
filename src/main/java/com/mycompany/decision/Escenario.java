/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.decision;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Leo
 */
public class Escenario {
    Orilla izquierda;
    Orilla derecha;
    Barca barca;
    boolean puedeGenerar = true;
    int nAnimales = 0;
    
    public Escenario(){
        this.izquierda = new Orilla('i');
        this.derecha = new Orilla(3,3,'d');
        this.barca = new Barca();
        this.nAnimales = this.getNAnimales();
    }
    
    public Escenario(Escenario escenario){
        this(escenario.getIzquierda(), escenario.getDerecha(), escenario.getBarca());
    }
    
    public Escenario(Escenario escenario, boolean vaciarBarca){
        this(escenario.getIzquierda(), escenario.getDerecha(), new Barca());
    }
    
    public Escenario(Orilla izquierda, Orilla derecha, Barca barca){
        this.izquierda = new Orilla(izquierda);
        this.derecha = new Orilla(derecha);
        this.barca = new Barca(barca);
    }
    
    public static List<Escenario> generarEscenarios(Escenario e){
        List<Escenario> escenarios = new LinkedList();
        if(e.puedeGenerar){
            int i = 1;
            while(i<=5){
                Escenario escenario = new Escenario(e);
                Orilla orilla = escenario.getOrilla(escenario.getBarca().getPos());
                escenario.cargar(orilla, i);
                if(escenario.escenarioPosible()){
                    escenario.verificarGenerar();
                    escenarios.add(escenario);
                }
                i++;
            }
        }
        return escenarios;
    }
    
    public void setPuedeGenerar(boolean b){
        this.puedeGenerar = b;
    }
    
    public Orilla getOrilla(char pos){
        Orilla orilla = null;
        switch(pos) {
            case 'i':
                orilla = this.izquierda;
              break;
            case 'd':
                orilla = this.derecha;
              break;
        }
        return orilla;
    }
    
    public Orilla getOrillaOpuesta(char pos){
        Orilla orilla = null;
        switch(pos) {
            case 'd':
                orilla = this.izquierda;
              break;
            case 'i':
                orilla = this.derecha;
              break;
        }
        return orilla;
    }
    
    public void verificarGenerar(){
        if(izquierda.getNAnimales()<2 && this.barca.isInIzquierda()){
            this.puedeGenerar = false;
        }else if(!this.isSeguro()){
            this.puedeGenerar = false;
        }else if(this.izquierda.getNAnimales() == 6){
            this.puedeGenerar = false;
        }
    }
    
    public boolean isSeguro(){
        return !(this.izquierda.isCarnivorosMayorHerviboros() || this.derecha.isCarnivorosMayorHerviboros());
    }
    
    public boolean escenarioPosible(){
        boolean b = true;
        if(this.izquierda.getNCarnivoros()<0 || this.derecha.getNHerviboros()<0){
            b = false;
        }
        
        if(this.izquierda.getNCarnivoros()>3 || this.derecha.getNHerviboros()>3){
            b = false;
        }
        
        if(this.getNAnimales()+this.barca.getNAnimales() != 6){
            b = false;
        }
        
        return b;
    }
    
    public boolean cargar(Orilla orilla, int tipo){
        Animal animal1;
        Animal animal2 = null;
        if(orilla.getNAnimales()>0 && this.barca.getPos() == orilla.getPos()){
            switch(tipo) {
                case 1:
                    animal1 = new Animal(true);
                  break;
                case 2:
                    animal1 = new Animal(false);
                  break;
                case 3:
                    animal1 = new Animal(true);
                    animal2 = new Animal(true);
                  break;
                case 4:
                    animal1 = new Animal(false);
                    animal2 = new Animal(false);
                  break;
                case 5:
                default:
                  animal1 = new Animal(false);
                  animal2 = new Animal(true);
                   
             }
            orilla.removeAnimal(animal1);
            this.getOrillaOpuesta(orilla.getPos()).addAnimal(animal1);
            if(animal2 != null){
                orilla.removeAnimal(animal2);
                this.getOrillaOpuesta(orilla.getPos()).addAnimal(animal2);
            }
            
            this.getBarca().moverPos(this.getOrillaOpuesta(orilla.getPos()).getPos());
            return true;
        }
        return false;
    }
    
    public boolean cargarIzquierda(int tipo){
        return cargar(this.izquierda, tipo);
    }
    
    public boolean cargarDerecha(int tipo){
        return cargar(this.derecha, tipo);
    }

    
    public Orilla getIzquierda(){
        return this.izquierda;
    }
    
    public Orilla getDerecha(){
        return this.derecha;
    }
    
    public Barca getBarca(){
        return this.barca;
    }

    public int getNAnimales() {
        return this.derecha.getNAnimales() + this.izquierda.getNAnimales();
    }
    
    @Override
    public boolean equals(Object obj){
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }
        
        final Escenario e = (Escenario) obj;
        return this.izquierda.equals(e.getIzquierda()) && this.derecha.equals(e.getDerecha()) && this.barca.equals(e.getBarca());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + Objects.hashCode(this.izquierda);
        hash = 13 * hash + Objects.hashCode(this.derecha);
        hash = 13 * hash + Objects.hashCode(this.barca);
        return hash;
    }
    
    @Override
    public String toString() {
        return "B: "+this.barca.getPos() + " I- L: " + this.izquierda.carnivoros + " -Ñ: " + this.izquierda.herviboros + " | D- L: " + this.derecha.carnivoros + " -Ñ: " + this.derecha.herviboros ;
    }
}
