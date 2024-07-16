/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.decision;


import java.util.Comparator;
import java.util.List;

/**
 *
 * @author nhale
 * @param <E>
 */
public class Tree<E> {
    private TreeNode<E> root;
    private Tree<E> parent;
    
    public Tree (){
        this.root = null; 
    }

    public Tree(E content){
        this.root = new TreeNode<E> (content);
    }

    public Tree<E> getParent() {
        return parent;
    }

    public void setParent(Tree<E> parent) {
        this.parent = parent;
    }

    public E getRoot() {
        return root.getContent();
    }
    
    private TreeNode getRootNode () {
        return this.root;
    }
    
    public List<Tree<E>> getChildren(){
        return getRootNode().getChildren();
    }
   
    private void setRootNode(TreeNode<E> root) {
        this.root = root;
    }
    
    public void setRoot (E content) {
        this.root.setContent(content);
    }    
    public boolean isEmpty () {
        return this.root == null;
    }
    
    public boolean isLeaf () {
        return this.root.getChildren().isEmpty();
    }
    public boolean add(E content){
        if(content== null){
            return false;
        }       
        this.root.getChildren().add(new Tree<E>(content));
        return true;
    }
    
    public boolean add(Tree<E> content){
        if(content== null){
            return false;
        }       
        this.root.getChildren().add(content);
        return true;
    }
    
    public boolean remove(E content,Comparator<E> cmp){
        if(content == null){
            return false;
        }
        for(Tree<E> t:this.root.getChildren()){
            if(cmp.compare(t.getRoot(),content)==0){
                this.root.getChildren().remove(t);
                return true;
            }
        }
        return false;
    }
    
    public boolean esHoja(){
        return this.getChildren().isEmpty();
    }
    
    public int getProfundidad(){
        if(this.esHoja()){
            return 0;
        }else{
            int max = Integer.MIN_VALUE;
            for(Tree<E> hijo: this.getChildren()){
                if(hijo.getProfundidad()>max){
                    max = hijo.getProfundidad();
                }
            }
            return 1+max;
        }
    }
    
    @Override
    public String toString(){
        return this.getRoot().toString();
    }
}
