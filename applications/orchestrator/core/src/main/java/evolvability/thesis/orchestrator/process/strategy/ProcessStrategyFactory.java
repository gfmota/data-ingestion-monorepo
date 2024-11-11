package evolvability.thesis.orchestrator.process.strategy;

import evolvability.thesis.common.metadata.Type;

public class ProcessStrategyFactory {
    public static ProcessStrategy getProcessStrategy(final Type resultType) {
        switch (resultType) {
            case ARRAY:
                return new ArrayResultProcessStrategy();
            case MAP:
                return new MapResultProcessStrategy();
            default:
                throw new IllegalArgumentException("Invalid strategy: " + resultType);
        }
    }
}
