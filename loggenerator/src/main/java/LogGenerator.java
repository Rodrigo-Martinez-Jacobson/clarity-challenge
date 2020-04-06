import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class LogGenerator {

    static int delay = 100;
    final static List<String> hostNames = Arrays.asList(
            "Link",
            "Zelda",
            "GLaDOS",
            "Cloud",
            "Kratos",
            "Snake",
            "Duke",
            "Sefirot",
            "Illidan",
            "Arthas",
            "Mario",
            "Bowser",
            "Alucard",
            "Shepard",
            "Ezio",
            "Rayman",
            "Spyro",
            "Samus",
            "Sonic",
            "Banjo",
            "Pikachu",
            "Cortana",
            "Megaman",
            "Geralt"
    );

    @SuppressWarnings("InfiniteLoopStatement")
    public static void main(String... args) throws InterruptedException {
        if (args.length > 0) delay = Integer.parseInt(args[0]);
        while (true) {
            System.out.println(generateLogLine());
            Thread.sleep(delay);
        }
    }

    private static String generateLogLine() {
        int[] randomNumbers = ThreadLocalRandom
                .current()
                .ints(0, hostNames.size())
                .distinct()
                .limit(2)
                .toArray();
        return String.format("%d %s %s", Instant.now().toEpochMilli(), hostNames.get(randomNumbers[0]), hostNames.get(randomNumbers[1]));
    }
}
