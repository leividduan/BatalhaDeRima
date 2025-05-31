package utils;

import java.util.Random;

public class TopicGenerator {
    private static final String[] topics = {
        "amor", "futuro", "tecnologia", "amizade", "cidade", "liberdade"
    };

    public static String Generate() {
        return topics[new Random().nextInt(topics.length)];
    }
}
