package io.onhigh.bot.secretary.provision.constraint;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Marat Kadzhaev
 * @since 16 июль 2021 г.
 */
@Getter
public class BizConstraintException extends RuntimeException {

    private List<ConstraintCheckResult> checkResults;

    public BizConstraintException(ConstraintCheckResult checkResult) {
        checkResults = new ArrayList<>();
        checkResults.add(checkResult);
    }

    public BizConstraintException(List<ConstraintCheckResult> checkResults) {
        this.checkResults = checkResults;
    }

    public boolean isMoreThanOne() {
        return checkResults.size() > 1;
    }

    @Override
    public String getMessage() {
        return checkResults
                .stream()
                .findFirst()
                .map(ConstraintCheckResult::getDetails)
                .orElse(null);
    }
}
