
package batalhaderima;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;

public class BatalhaDeRima {
    public static void main(String[] args) {
        Runtime rt = Runtime.instance();
        Profile profile = new ProfileImpl();
        profile.setParameter(Profile.MAIN_HOST, "localhost");
        profile.setParameter(Profile.MAIN_PORT, "1099");
        profile.setParameter(Profile.GUI, "true");

        ContainerController mainContainer = rt.createMainContainer(profile);

        try {
            AgentController juiz = mainContainer.createNewAgent("judge", "agents.JudgeAgent", null);
            juiz.start();

            AgentController rapper1 = mainContainer.createNewAgent("rapperA", "agents.RapperAgent", null);
            rapper1.start();

            AgentController rapper2 = mainContainer.createNewAgent("rapperB", "agents.RapperAgent", null);
            rapper2.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
