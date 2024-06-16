package source;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class ICDCodeTabularOptimizedForMemory implements ICDCodeTabular {
    private String path = "C:\\UserFiles\\GIT\\PowtorzenieDoKolosa1\\icd10.txt";



    @Override
    public String getDescription(String ICD) {
        try(Stream<String> lines = Files.lines(Paths.get(path))) {
            return lines.skip(88)
                    .map(String::trim)
                    .filter(s -> s.matches("[A-Z][0-9]{2}.*"))
                    .map(s -> s.split(" ", 2))
                    .filter(strings -> strings[0].equals(ICD))
                    .findFirst()
                    .map(strings -> strings[1])
                    .orElse("?");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
