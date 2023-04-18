package io.onhigh.bot.secretary.provision.constraint;

/**
 * @author Marat Kadzhaev
 * @since 16 июль 2021 г.
 */
public interface BizConstraint {

     ConstraintCheckResult check() throws BizConstraintException;

}
