package agents;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import org.json.JSONObject;
import gui.BattleGUI;
import llm.LLMClient;
import llm.Prompt;
import utils.TopicGenerator;

public class JudgeAgent extends Agent {
    private int round = 0;
    private final int MAX_ROUNDS = 3;
    private String topic = "";
    private String rhymeA = "";
    private String rhymeB = "";
    private int scoreA = 0;
    private int scoreB = 0;
    private BattleGUI gui;

    @Override
    protected void setup() {
        gui = new BattleGUI();

        // Comportamento inicial: gerar e enviar o primeiro tema
        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                topic = TopicGenerator.Generate();
                gui.updateRound(round + 1);
                gui.updateTopic(topic);
                ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                msg.addReceiver(new AID("rapperA", AID.ISLOCALNAME));
                msg.setContent("TOPIC:" + topic);
                send(msg);
                block();
            }
        });

        // Comportamento contínuo: receber rimas e gerenciar rodadas
        addBehaviour(new CyclicBehaviour() {
            public void action() {
                ACLMessage reply = receive();
                if (reply != null) {
                    String sender = reply.getSender().getLocalName();

                    if (sender.equals("rapperA")) {
                        rhymeA = reply.getContent();
                        ACLMessage toB = new ACLMessage(ACLMessage.INFORM);
                        toB.addReceiver(new AID("rapperB", AID.ISLOCALNAME));
                        toB.setContent("TOPIC:" + topic + ";RHYME:" + rhymeA);
                        send(toB);
                        block();
                    } else if (sender.equals("rapperB")) {
                        rhymeB = reply.getContent();
                        evaluateRound(rhymeA, rhymeB, topic);

                        round++;
                        if (round < MAX_ROUNDS) {
                            topic = TopicGenerator.Generate();
                            gui.updateRound(round + 1);
                            gui.updateTopic(topic);
                            ACLMessage next = new ACLMessage(ACLMessage.INFORM);
                            next.addReceiver(new AID("rapperA", AID.ISLOCALNAME));
                            next.setContent("TOPIC:" + topic);
                            send(next);
                            block();
                        } else {
                            String finalWinner;
                            if (scoreA > scoreB) finalWinner = "Rapper A ganhou a batalha!";
                            else if (scoreB > scoreA) finalWinner = "Rapper B ganhou a batalha!";
                            else finalWinner = "Batalha empatada!";
                            gui.showWinner(finalWinner);
                            doDelete();
                        }
                    }
                } else {
                    block();
                }
            }
        });
    }

    private void evaluateRound(String a, String b, String topic) {
        LLMClient client = new LLMClient();
        gui.updateRhymeA(a);
        client.speakText(a, "Charon");
        gui.updateRhymeB(b);
        client.speakText(b, "Orus");
        String winner;

        String promptA = Prompt.EvaluateSingleRhyme(topic, a);
        String promptB = Prompt.EvaluateSingleRhyme(topic, b);
        String responseA = client.invokeModel("gemma3:12b", promptA);
        String responseB = client.invokeModel("gemma3:12b", promptB);

        double score1 = Double.parseDouble(responseA);
        double score2 = Double.parseDouble(responseB);

        if (score1 > score2) {
            winner = "Rapper A";
            scoreA++;
        } else if (score2 > score1) {
            winner = "Rapper B";
            scoreB++;
        } else {
            winner = "Empate";
        }
        gui.updateScores(scoreA, scoreB);
        gui.showWinner(winner);
    }
}