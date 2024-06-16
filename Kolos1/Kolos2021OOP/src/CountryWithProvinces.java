import java.time.LocalDate;

public class CountryWithProvinces extends Country{
    private Country[] provinces;

    public CountryWithProvinces(String name, Country[] provinces) {
        super(name);
        this.provinces = provinces;
    }


    @Override
    public Integer getConfirmedCases(LocalDate date) {
        int sum = 0;
        for(Country province : provinces) {
            sum += province.getConfirmedCases(date);
        }
        return sum;
    }

    @Override
    public Integer getDeaths(LocalDate date) {
        int sum = 0;
        for(Country province : provinces) {
            sum += province.getDeaths(date);
        }
        return sum;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.name);
        for(Country province : provinces) {
            sb.append(province.toString() + "\n");
        }
        return sb.toString();
    }
}
