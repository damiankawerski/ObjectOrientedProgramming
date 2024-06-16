package source;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class ICDCodeTabularOptimizedForTime implements ICDCodeTabular {
    private String path = "C:\\UserFiles\\GIT\\PowtorzenieDoKolosa1\\icd10.txt";
    private Map<String, String> icdDescriptions = new HashMap<>();

    public ICDCodeTabularOptimizedForTime() {
        try (Stream<String> lines = Files.lines(Paths.get(path))) {
            lines.skip(88)
                    .map(String::trim)
                    .filter(s -> s.matches("[A-Z][0-9]{2}.*"))
                    .map(s -> s.split(" ", 2))
                    .forEach(strings -> icdDescriptions.put(strings[0], strings[1]));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getDescription(String ICD) {
        return icdDescriptions.getOrDefault(ICD, "?");
    }
}
