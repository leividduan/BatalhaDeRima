package agents;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import llm.LLMClient;
import llm.Prompt;

public class RapperAgent extends Agent {
    private String topic = null;

    @Override
    protected void setup() {
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage msg = receive();

                if (msg != null) {
                    String received = msg.getContent();

                    if (received.startsWith("TOPIC:") && !received.contains(";RHYME:")) {
                        // Mensagem inicial do juiz
                        topic = received.substring(6).trim();
                        String prompt = Prompt.GetRhymePrompt(topic, "");
                        LLMClient client = new LLMClient();
                        String rhyme = client.invokeModel("gemma3:12b", prompt);

                        ACLMessage response = new ACLMessage(ACLMessage.INFORM);
                        response.addReceiver(new AID("judge", AID.ISLOCALNAME));
                        response.setContent(rhyme);
                        send(response);
                        block();

                    } else if (received.startsWith("TOPIC:") && received.contains(";RHYME:")) {
                        // Mensagem com a rima do oponente
                        String[] parts = received.split(";RHYME:");
                        topic = parts[0].substring(6).trim();
                        String rhymeA = parts[1].trim();

                        String prompt = Prompt.GetRhymePrompt(topic, rhymeA);
                        LLMClient client = new LLMClient();
                        String rhyme = client.invokeModel("gemma3:12b", prompt);

                        ACLMessage response = new ACLMessage(ACLMessage.INFORM);
                        response.addReceiver(new AID("judge", AID.ISLOCALNAME));
                        response.setContent(rhyme);
                        send(response);
                        block();

                    } else {
                        System.out.println(getLocalName() + " ERROR: unexpected message format.");
                    }
                } else {
                    block();
                }
            }
        });
    }
}