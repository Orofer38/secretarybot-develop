package io.onhigh.bot.secretary.provision.constraint;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Marat Kadzhaev
 * @since 16 июль 2021 г.
 */
@Getter
@RequiredArgsConstructor
public class ConstraintCheckResult {

    public static final ConstraintCheckResult DEFAULT_SUCCESS = ConstraintCheckResult.success();

    private final String errorCode;
    private final Object[] args;
    private final String details;
    private final ConstraintCheckResultStatus status;

    public boolean isFailed() {
        return ConstraintCheckResultStatus.FAILED == status;
    }

    public static ConstraintCheckResult success() {
        return new ConstraintCheckResult(null, null, null, ConstraintCheckResultStatus.SUCCESS);
    }

    public static ConstraintCheckResult failed(String errorCode, String details) {
        return new ConstraintCheckResult(errorCode, null, details, ConstraintCheckResultStatus.FAILED);
    }
}
