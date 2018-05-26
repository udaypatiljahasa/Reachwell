/**
 * Jahasa Technology all rights reserved
 *
 * @author Uday
 * @since 01.02.2018
 *
 * Custom Application annotation interface.
 *
 */

package com.am.reachwell.Global.Depedencies;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface ApplicationContext {
}
