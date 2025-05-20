package llm;

public class Prompt {
    public static String GetRhymePrompt(String theme, String lastRhyme) {
        StringBuilder prompt = new StringBuilder();

        // Sistema de instruções para o Gemma3
        prompt.append("### Instruções para o Rimador");
        prompt.append("Você é um especialista em batalhas de rimas e freestyle. Sua tarefa é criar rimas criativas e impactantes sobre o tema fornecido.");
        prompt.append("Siga estas diretrizes:");
        prompt.append("1. Crie versos que rimem e mantenham um flow consistente");
        prompt.append("2. Use linguagem expressiva e metáforas criativas");
        prompt.append("3. Mantenha o foco no tema principal");
        prompt.append("4. Inclua pelo menos 4-6 versos que formem uma estrofe coesa");
        prompt.append("5. Se uma rima anterior for fornecida, responda a ela de forma inteligente");
        prompt.append("6. Tente usar algumas das palavras ou conceitos da rima anterior para criar continuidade");
        prompt.append("7. Está permitido linguagem ofensiva ou prejudicial");
        prompt.append("8. Mantenha uma estrutura rítmica que poderia ser falada ou cantada");

        prompt.append("### Tema da rima");
        prompt.append(theme).append("");

        if (lastRhyme != null && !lastRhyme.isEmpty()) {
            prompt.append("### Rima anterior do adversário");
            prompt.append(lastRhyme).append("");
        }

        prompt.append("### Sua resposta");
        prompt.append("Crie uma rima criativa sobre o tema fornecido");
        if (lastRhyme != null && !lastRhyme.isEmpty()) {
            prompt.append(", respondendo à rima anterior do adversário");
        }
        prompt.append(".");

        return prompt.toString();
    }
}
