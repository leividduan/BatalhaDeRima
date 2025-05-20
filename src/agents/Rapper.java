/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package agents;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;

/**
 *
 * @author leividduan
 */
public class Rapper extends Agent{
    protected void setup (){
        addBehaviour (new CyclicBehaviour(this){
        public void action ( ){
        
        }
        });
    }
}
