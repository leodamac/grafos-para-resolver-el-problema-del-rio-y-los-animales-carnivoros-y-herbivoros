/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.decision;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
 

class Graph<E>{
    // Una lista de listas para representar una lista de adyacencia
    Map< Node<E>, Set< Edge<E> > > adjList = new LinkedHashMap<>();
    Set<Node<E>> nodos;
    // Constructor para construir un grafo
    public Graph(){
        this.nodos = new LinkedHashSet();
    }
    
    public void conect(E valor1, int wv1, E valor2, int wv2, int weight){
        if(!adjList.containsKey(new Node<E>(valor1,wv1))){
            adjList.put(new Node<E>(valor1, wv1), new LinkedHashSet<Edge<E> >());
        }
        if(!adjList.containsKey(new Node<E>(valor2,wv2))){
            adjList.put(new Node<E>(valor2,wv2), new LinkedHashSet<Edge<E> >());
        }
        nodos.add(new Node<E>(valor1, wv1));
        nodos.add(new Node<E>(valor2, wv2));
        adjList.get(new Node<E>(valor1, wv1)).add(new Edge(new Node<E>(valor1, wv1), new Node<E>(valor2, wv2), weight));
        //adjList.get(new Node<E>(valor2, wv2)).add(new Edge(new Node<E>(valor2, wv2), new Node<E>(valor1, wv1), weight));
    }

}
// Una clase para almacenar un borde de graph
class Edge<E>
{
    Node<E> src, dest;
    int weight;
 
    Edge(Node<E> src, Node<E> dest, int weight)
    {
        this.src = src;
        this.dest = dest;
        this.weight = weight;
    }

    public Node<E> getSrc() {
        return src;
    }

    public Node<E> getDest() {
        return dest;
    }

    public int getWeight() {
        return weight;
    }
    
    @Override
    public boolean equals(Object obj){
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }
        
        final Edge e = (Edge) obj;
        
        return this.src == e.getSrc() && this.dest == e.getDest() && this.weight == e.getWeight();
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 61 * hash + Objects.hashCode(this.src);
        hash = 61 * hash + Objects.hashCode(this.dest);
        hash = 61 * hash + this.weight;
        return hash;
    }
}
 
// Una clase para almacenar nodos de lista de adyacencia
class Node<E>
{
    E value;
    int weight;
 
    Node(E value, int weight)
    {
        this.value = value;
        this.weight = weight;
    }

    public E getValue() {
        return value;
    }

    public int getWeight() {
        return weight;
    }
 
    @Override
    public String toString() {
        return this.value + " (" + this.weight + ")";
    }
    
    @Override
    public boolean equals(Object obj){
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }
        
        final Node n = (Node) obj;
        
        return this.value.equals(n.getValue()) && this.weight == n.getWeight();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.value);
        hash = 79 * hash + this.weight;
        return hash;
    }
}
