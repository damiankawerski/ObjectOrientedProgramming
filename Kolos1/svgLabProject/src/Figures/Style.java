package Figures;

public class Style {
    public final String fillColor;
    public final String strokeColor;
    public final Double strokeWidth;

    public Style(String fillColor, String strokeColor, Double strokeWidth) {
        if(fillColor == null) { this.fillColor = "transparent"; }
        else { this.fillColor = fillColor; }
        if(strokeColor == null) {this.strokeColor = "black"; }
        else { this.strokeColor = strokeColor; }
        if(strokeWidth == null) { this.strokeWidth = 1.0; }
        else{ this.strokeWidth = strokeWidth; }
    }

    Style(Style other) {
        this.fillColor = other.fillColor;
        this.strokeWidth = other.strokeWidth;
        this.strokeColor = other.strokeColor;
    }

    public String toSvg() {
        StringBuilder svg = new StringBuilder();
        svg.append("style=\"fill:").append(fillColor).append(";stroke:").append(strokeColor);
        svg.append(";stroke-width:").append(strokeWidth).append("\" />");
        return svg.toString();
    }
}
