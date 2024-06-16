public class CountryNotFoundException extends Exception {
    public CountryNotFoundException(String name) {
        super("Country " + name + " not found");
    }
}
