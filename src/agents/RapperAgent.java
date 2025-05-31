package agents;

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
                        topic = received.substring(6).trim();

                        String prompt = Prompt.GetRhymePrompt(topic, "");
                        LLMClient client = new LLMClient();
                        String rhyme = client.invokeModel("gemma3:12b", prompt);

                        ACLMessage reply = msg.createReply();
                        reply.setPerformative(ACLMessage.INFORM);
                        reply.setContent(rhyme);
                        send(reply);

                    } else if (received.startsWith("TOPIC:") && received.contains(";RHYME:")) {
                        String[] parts = received.split(";RHYME:");
                        topic = parts[0].substring(6).trim();
                        String rhymeA = parts[1].trim();

                        String prompt = Prompt.GetRhymePrompt(topic, rhymeA);
                        LLMClient client = new LLMClient();
                        String rhyme = client.invokeModel("gemma3:12b", prompt);

                        ACLMessage reply = msg.createReply();
                        reply.setPerformative(ACLMessage.INFORM);
                        reply.setContent(rhyme);
                        send(reply);

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