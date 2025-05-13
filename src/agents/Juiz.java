/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package agents;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

/**
 *
 * @author leividduan
 */
public class Juiz extends Agent{
    protected void setup (){
        addBehaviour (new CyclicBehaviour(this){
        public void action ( ){
        
        } // fim do a c t i on ( )
        }); // fim do addBehaviour ( )
    }
}
