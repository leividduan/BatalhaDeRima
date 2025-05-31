
package agents;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import gui.BattleGUI;
import utils.TopicGenerator;

public class JudgeAgent extends Agent {
    private int round = 0;
    private final int MAX_ROUNDS = 3;
    private String rhymeA = "";
    private String rhymeB = "";
    private int scoreA = 0;
    private int scoreB = 0;
    private BattleGUI gui;

    @Override
    protected void setup() {
        gui = new BattleGUI();
        String topic = TopicGenerator.Generate();
        gui.updateRound(round + 1);
        gui.updateTopic(topic);
        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        msg.addReceiver(new AID("rapperA", AID.ISLOCALNAME));
        msg.setContent("TOPIC:" + topic);
        send(msg);

        addBehaviour(new CyclicBehaviour(this) {
            public void action() {
                ACLMessage reply = receive();
                if (reply != null) {
                    if (reply.getSender().getLocalName().equals("rapperA")) {
                        rhymeA = reply.getContent();
                        ACLMessage toB = new ACLMessage(ACLMessage.INFORM);
                        toB.addReceiver(new AID("rapperB", AID.ISLOCALNAME));
                        toB.setContent("TOPIC:" + topic + ";RHYME:" + rhymeA);
                        send(toB);
                    } else if (reply.getSender().getLocalName().equals("rapperB")) {
                        rhymeB = reply.getContent();
                        evaluateRound(rhymeA, rhymeB);
                        round++;
                        if (round < MAX_ROUNDS) {
                            String topic = TopicGenerator.Generate();
                            gui.updateRound(round + 1);
                            gui.updateTopic(topic);
                            ACLMessage next = new ACLMessage(ACLMessage.INFORM);
                            next.addReceiver(new AID("rapperA", AID.ISLOCALNAME));
                            next.setContent("TOPIC:" + topic);
                            send(next);
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

    private void evaluateRound(String a, String b) {
        gui.updateRhymeA(a);
        gui.updateRhymeB(b);
        String winner;
        if (a.length() > b.length()) {
            winner = "Rapper A";
            scoreA++;
        } else if (b.length() > a.length()) {
            winner = "Rapper B";
            scoreB++;
        } else {
            winner = "Empate";
        }
        gui.updateScores(scoreA, scoreB);
        gui.showWinner(winner);
    }
}
