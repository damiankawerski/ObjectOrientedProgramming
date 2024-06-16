package source;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DeathCauseStatisticList {
    public List<DeathCauseStatistic> deathCauseStatistics = new ArrayList<>();
    private static DeathCauseStatisticList instance = null;

    private DeathCauseStatisticList() {}

    public static DeathCauseStatisticList getInstance() {
        if(instance == null) {
            instance = new DeathCauseStatisticList();
        }
        return instance;
    }

    public void repopulate(String path) {
        deathCauseStatistics.clear();
        try {
            deathCauseStatistics = Files.lines(Paths.get(path))
                    .skip(2)
                    .map(DeathCauseStatistic::fromCsvLine)
                    .toList();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<DeathCauseStatistic> mostDeadlyDiseases(int age, int n) {
        List<DeathCauseStatistic> result = this.deathCauseStatistics.stream().sorted((x,y) -> Integer.compare(y.bracketDeaths(age).deathCount, x.bracketDeaths(age).deathCount))
                .toList().subList(0,10);
        return result;
    }
}
