import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public abstract class Country {
    public final String name;
    static private Path deathsPath;
    static private Path confirmedCasesPath;

    public Country(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    static void setFiles(String deaths, String confirmed) throws FileNotFoundException {
        deathsPath = Paths.get(deaths);
        confirmedCasesPath = Paths.get(confirmed);
        File D = new File(deaths);
        File C = new File(confirmed);
        if (!D.exists() || !D.canRead()) {
            throw new FileNotFoundException(deaths);
        }
        if (!C.exists() || !C.canRead()) {
            throw new FileNotFoundException(confirmed);
        }
    }

    public static Country fromCsv(String name) {
        try (BufferedReader bf = new BufferedReader(new FileReader(deathsPath.toFile()))) {
            // Check if province and find columns
            String line;
            boolean hasProvincess = true;
            line = bf.readLine();
            CountryColumns cc = getCountryColumns(line, name); // <-- tutaj zacząc try
            if(cc.columnCount == 0) {
                hasProvincess = false;
            }
            // For provinces
            line = bf.readLine();
            if(hasProvincess) {
                List<Country> provinces = new ArrayList<>();
                String[] split = line.split(";");
                for(int i = cc.firstColumnIndex; i < cc.firstColumnIndex + cc.columnCount; i++) {
                    CountryWithoutProvinces temp = CountryWithoutProvinces.loadInfoFromFile(deathsPath.toString(), i, split[i]);
                    provinces.add(temp);
                }
                return new CountryWithProvinces(name, provinces.toArray(new Country[0]));
            } else {
                CountryWithoutProvinces result = CountryWithoutProvinces.loadInfoFromFile(deathsPath.toString(), cc.firstColumnIndex, name);
                return result;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (CountryNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private static class CountryColumns{
        public final int firstColumnIndex, columnCount;
        public CountryColumns(int firstColumnIndex, int columnIndex) {
            this.firstColumnIndex = firstColumnIndex;
            this.columnCount = columnIndex;
        }
    }

    private static CountryColumns getCountryColumns(String firstCsvLine, String name) throws CountryNotFoundException {
            int startIndex, endIndex;
            List<String> stringList = new ArrayList<>(Arrays.asList(firstCsvLine.split(";")));
            startIndex = stringList.indexOf(name);
            if(startIndex == -1) {
                throw new CountryNotFoundException(name);
            }
            endIndex = stringList.lastIndexOf(name);
            return new CountryColumns(startIndex, endIndex - startIndex);
    }

    public static Country[] fromCsv(String[] names) {
        List<Country> result = new ArrayList<>();
        for(String name : names) {
            try (BufferedReader bf = new BufferedReader(new FileReader(deathsPath.toFile()))) {
                // Check if province and find columns
                String line;
                boolean hasProvincess = true;
                line = bf.readLine();
                CountryColumns cc = getCountryColumns(line, name);
                if(cc.columnCount == 0) {
                    hasProvincess = false;
                }
                // For provinces
                line = bf.readLine();
                if(hasProvincess) {
                    List<Country> provinces = new ArrayList<>();
                    String[] split = line.split(";");
                    for(int i = cc.firstColumnIndex; i < cc.firstColumnIndex + cc.columnCount; i++) {
                        CountryWithoutProvinces temp = CountryWithoutProvinces.loadInfoFromFile(deathsPath.toString(), i, split[i]);
                        provinces.add(temp);
                    }
                    result.add(new CountryWithProvinces(name, provinces.toArray(new Country[0])));
                } else {
                    CountryWithoutProvinces cwp = CountryWithoutProvinces.loadInfoFromFile(deathsPath.toString(), cc.firstColumnIndex, name);
                    result.add(cwp);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (CountryNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
        return result.toArray(new Country[0]);
    }

    public abstract Integer getConfirmedCases(LocalDate date);
    public abstract Integer getDeaths(LocalDate date);

    public static List<Country> sortByDeaths(List<Country> list, LocalDate startDate, LocalDate endDate) {
        return list.stream()
                .sorted(Comparator.comparingInt(c -> getTotalDeaths(c, startDate, endDate)))
                .collect(Collectors.toList());
    }

    private static int getTotalDeaths(Country country, LocalDate startDate, LocalDate endDate) {
        int totalDeaths = 0;
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            int deaths = country.getDeaths(date);
            if (deaths > 0) {
                totalDeaths += deaths;
            }
        }
        return totalDeaths;
    }

    private static Map<LocalDate, Integer> totalPerDate(Path path) {
        Map<LocalDate, Integer> map = new HashMap<>();
        try {
            BufferedReader bf = new BufferedReader(new FileReader(path.toString()));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yy");
            String line = bf.readLine();
            line = bf.readLine();
            while ((line = bf.readLine()) != null) {
                List<String> split = Arrays.asList(line.split(";"));
                Integer sum = split.stream().skip(1).mapToInt(Integer::parseInt).sum();
                map.put(LocalDate.parse(split.get(0), formatter), sum);
            }
            bf.close();
            return map;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveToDataFile(Path path) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(path.toString()));
            Map<LocalDate, Integer> mapOfDeaths = totalPerDate(deathsPath);
            Map<LocalDate, Integer> mapOfInfections = totalPerDate(confirmedCasesPath);

            for (Map.Entry<LocalDate, Integer> entry : mapOfDeaths.entrySet()) {
                bw.write(entry.getKey().format(DateTimeFormatter.ofPattern("d.M.yy")));
                bw.write("\t");
                bw.write(entry.getValue().toString());
                bw.write("\t");
                bw.write(mapOfInfections.getOrDefault(entry.getKey(), 0).toString()); // GetOrDefault handles missing keys
                bw.newLine();
            }

            bw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
