package evolvability.thesis.orchestrator.exceptions;

import evolvability.thesis.common.metadata.Type;

public class ResultTypeNotSupported extends Exception {
    public ResultTypeNotSupported(final Type resultType) {
            super(String.format("Result type %s not supported", resultType.getValue()));
    }
}
