package evolvability.thesis.orchestrator.transformers;

public class MeterPerSecondToKilometerPerHourTransformer implements Transformer {
    private static final String INPUT_UNIT = "m/s";
    private static final String OUTPUT_UNIT = "km/h";

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
        return data * 3.6;
    }
}
