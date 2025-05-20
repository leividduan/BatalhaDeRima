/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package batalhaderima;

/**
 *
 * @author leividduan
 */
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;

public class BatalhaDeRima {
    public static void main(String[] args) {
        // Get the JADE runtime
        Runtime rt = Runtime.instance();

        // Create a default profile (can also specify host, port, etc.)
        Profile profile = new ProfileImpl();
        profile.setParameter(Profile.MAIN_HOST, "localhost");
        profile.setParameter(Profile.MAIN_PORT, "1099");
        profile.setParameter(Profile.GUI, "true"); // optional: shows the RMA GUI

        // Create the main container (the first container must be the main one)
        ContainerController mainContainer = rt.createMainContainer(profile);

        try {
            // Start an agent inside the container (optional)
            AgentController juiz = mainContainer.createNewAgent(
                "Juiz", // Agent name
                "agents.Juiz", // Fully qualified class name
                null // Arguments to the agent's setup method
            );
            juiz.start();

            // Start an agent inside the container (optional)
            AgentController rapper1 = mainContainer.createNewAgent(
                    "Rapper1", // Agent name
                    "agents.Rapper", // Fully qualified class name
                    null // Arguments to the agent's setup method
            );
            rapper1.start();

            // Start an agent inside the container (optional)
            AgentController rapper2 = mainContainer.createNewAgent(
                    "Rapper2", // Agent name
                    "agents.Rapper", // Fully qualified class name
                    null // Arguments to the agent's setup method
            );
            rapper2.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}