package evolvability.thesis.orchestrator.transformers;

public class CelsiusToKelvinTransformer implements Transformer {
    private static final String INPUT_UNIT = "°C";
    private static final String OUTPUT_UNIT = "K";

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
        return data + 273.15;
    }
}