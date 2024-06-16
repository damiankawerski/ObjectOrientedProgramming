import java.util.List;

public class AmbigiousProductException extends Exception {
    private List<String> productList;

    AmbigiousProductException(List<String> list) {
        super("Products are ambiguous");
        this.productList = list;
    }

    @Override
    public String getMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.getMessage()).append(": ");
        for (String product : productList) {
            sb.append(product).append(", ");
        }
        sb.delete(sb.length() - 2, sb.length()); // Usuwa ostatnią przecinek i spację
        return sb.toString();
    }
}