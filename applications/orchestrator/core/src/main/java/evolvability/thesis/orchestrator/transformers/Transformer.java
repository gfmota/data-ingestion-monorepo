package evolvability.thesis.orchestrator.transformers;

public interface Transformer {
    String getInputUnit();

    String getOutputUnit();

    Double transform(Double data);
}
