package io.onhigh.bot.secretary.provision.constraint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

/**
 * @author Marat Kadzhaev
 * @since 16 июль 2021 г.
 */
public class BizConstraintProcessor {

    private final List<BizConstraint> constraints = new ArrayList<>();

    public static BizConstraintProcessor create() {
        return new BizConstraintProcessor();
    }

    public BizConstraintProcessor addAll(List<BizConstraint> bizConstraints) {
        constraints.addAll(bizConstraints);
        return this;
    }

    public BizConstraintProcessor add(BizConstraint constraint) {
        constraints.add(constraint);
        return this;
    }

    public BizConstraintProcessor add(BizConstraint... constraints) {
        Collections.addAll(this.constraints, constraints);
        return this;
    }

    public BizConstraintProcessor add(List<? extends BizConstraint> constraints) {
        this.constraints.addAll(constraints);
        return this;
    }

    public BizConstraintProcessor add(BizConstraint constraint, Supplier<Boolean> condition) {
        if (condition.get()) {
            return add(constraint);
        }

        return this;
    }

    public void check() {
        check(true);
    }

    public void check(boolean failFast) {
        List<ConstraintCheckResult> checkResults = new ArrayList<>();

        for (BizConstraint constraint : constraints) {
            ConstraintCheckResult result = constraint.check();
            if (result.isFailed()) {
                if (failFast) {
                    throw new BizConstraintException(result);
                }

                checkResults.add(result);
            }
        }

        if (!checkResults.isEmpty()) {
            throw new BizConstraintException(checkResults);
        }
    }

}
