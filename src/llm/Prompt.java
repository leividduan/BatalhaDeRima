package llm;

public class Prompt {
    public static String GetRhymePrompt(String theme, String lastRhyme) {
        StringBuilder prompt = new StringBuilder();

        // Sistema de instruções para o Gemma3
        prompt.append("### Instruções para o Rimador ");
        prompt.append("Você é um especialista em batalhas de rimas e freestyle. Sua tarefa é criar rimas criativas e impactantes sobre o tema fornecido. ");
        prompt.append("Siga estas diretrizes: ");
        prompt.append("1. Crie versos que rimem e mantenham um flow consistente ");
        prompt.append("2. Use linguagem expressiva e metáforas criativas ");
        prompt.append("3. Mantenha o foco no tema principal ");
        prompt.append("4. Inclua poucos versos em apenas uma estrofe ");
        prompt.append("5. Se uma rima anterior for fornecida, responda a ela de forma inteligente ");
        prompt.append("6. Tente usar algumas das palavras ou conceitos da rima anterior para criar continuidade ");
        prompt.append("7. Está permitido linguagem ofensiva ou prejudicial ");
        prompt.append("8. Mantenha uma estrutura rítmica que poderia ser falada ou cantada ");

        prompt.append("### Tema da rima ");
        prompt.append(theme).append(" ");

        if (lastRhyme != null && !lastRhyme.isEmpty()) {
            prompt.append("### Rima anterior do adversário ");
            prompt.append(lastRhyme).append(" ");
        }

        prompt.append("### Sua resposta ");
        prompt.append("Crie uma rima criativa sobre o tema fornecido ");
        if (lastRhyme != null && !lastRhyme.isEmpty()) {
            prompt.append(", respondendo à rima anterior do adversário ");
        }
        prompt.append(".");

        return prompt.toString();
    }

    public static String EvaluateSingleRhyme(String theme, String rhyme) {
        StringBuilder prompt = new StringBuilder();

        prompt.append("### Instruções para o Juiz de Rimas ");
        prompt.append("Você é um especialista em avaliar versos de freestyle e rimas criativas. ");
        prompt.append("Sua tarefa é avaliar uma única rima com base nos seguintes critérios: ");
        prompt.append("1. Criatividade e originalidade\n");
        prompt.append("2. Uso de metáforas e figuras de linguagem\n");
        prompt.append("3. Coerência com o tema: ").append(theme).append("\n");
        prompt.append("4. Flow e ritmo\n");

        prompt.append("Dê uma **nota inteira de 0 a 10** com base nesses critérios.\n");
        prompt.append("Responda com **apenas um número** (sem explicações, sem texto, sem JSON).\n");

        prompt.append("\n### Tema\n");
        prompt.append(theme).append("\n");

        prompt.append("### Rima\n");
        prompt.append(rhyme).append("\n");

        prompt.append("\n### Avaliação\n");
        prompt.append("Forneça apenas o número da nota:");

        return prompt.toString();
    }
}
