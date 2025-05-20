/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package agents;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import llm.LLMClient;
import llm.Prompt;


/**
 *
 * @author leividduan
 */
public class Juiz extends Agent{
    protected void setup (){
        addBehaviour (new OneShotBehaviour(this){
        public void action ( ){
            var prompt = Prompt.GetRhymePrompt("algodão doce", "");
            LLMClient client = new LLMClient();
            var result = client.invokeModel("gemma3:12b", prompt);
            System.out.println(result);
        }
        });
    }
}
