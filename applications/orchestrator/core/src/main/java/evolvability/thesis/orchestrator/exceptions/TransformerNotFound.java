package evolvability.thesis.orchestrator.exceptions;

public class TransformerNotFound extends Exception {
    public TransformerNotFound(final String unit, final String targetUnit) {
            super(String.format("Transformer for unit %s to %s not found", unit, targetUnit));
    }
}
