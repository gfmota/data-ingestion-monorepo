package evolvability.thesis.orchestrator.transformers;

public interface Transformer<T, U> {
    T transform(U data);
}
