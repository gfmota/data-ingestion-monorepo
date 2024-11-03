package evolvability.thesis.orchestrator.transformers;

public class KelvinToFahrenheitTransformer implements Transformer {
    private static final String INPUT_UNIT = "K";
    private static final String OUTPUT_UNIT = "°F";

    @Override
    public String getInputUnit() {
        return INPUT_UNIT;
    }

    @Override
    public String getOutputUnit() {
        return OUTPUT_UNIT;
    }

    @Override
    public Double transform(final Double data) {
        return (data - 273.15) * 9 / 5 + 32;
    }
}