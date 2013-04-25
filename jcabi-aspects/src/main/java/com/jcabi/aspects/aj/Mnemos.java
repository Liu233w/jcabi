/**
 * Copyright (c) 2012-2013, JCabi.com
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met: 1) Redistributions of source code must retain the above
 * copyright notice, this list of conditions and the following
 * disclaimer. 2) Redistributions in binary form must reproduce the above
 * copyright notice, this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided
 * with the distribution. 3) Neither the name of the jcabi.com nor
 * the names of its contributors may be used to endorse or promote
 * products derived from this software without specific prior written
 * permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT
 * NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
 * THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.jcabi.aspects.aj;

import com.jcabi.aspects.Immutable;
import com.jcabi.log.Logger;
import java.lang.reflect.Method;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * Utility class with text functions for making mnemos.
 *
 * @author Yegor Bugayenko (yegor@tpc2.com)
 * @version $Id$
 */
@Immutable
final class Mnemos {

    /**
     * Comma between elements.
     */
    private static final String COMMA = ", ";

    /**
     * Private ctor, it's a utility class.
     */
    private Mnemos() {
        // intentionally empty
    }

    /**
     * Make a string out of point.
     * @param point The point
     * @param trim Shall we trim long texts?
     * @return Text representation of it
     */
    public static String toText(final ProceedingJoinPoint point,
        final boolean trim) {
        return Mnemos.toText(
            MethodSignature.class.cast(point.getSignature()).getMethod(),
            point.getArgs(),
            trim
        );
    }

    /**
     * Make a string out of point.
     * @param point The point
     * @param trim Shall we trim long texts?
     * @return Text representation of it
     * @deprecated Use toText() instead
     */
    @Deprecated
    public static String toString(final ProceedingJoinPoint point,
        final boolean trim) {
        return Mnemos.toText(point, trim);
    }

    /**
     * Make a string out of method.
     * @param method The method
     * @param args Actual arguments of the method
     * @param trim Shall we trim long texts?
     * @return Text representation of it
     */
    public static String toText(final Method method, final Object[] args,
        final boolean trim) {
        final StringBuilder log = new StringBuilder();
        log.append('#').append(method.getName()).append('(');
        for (int pos = 0; pos < args.length; ++pos) {
            if (pos > 0) {
                log.append(Mnemos.COMMA);
            }
            log.append(Mnemos.toText(args[pos], trim));
        }
        log.append(')');
        return log.toString();
    }

    /**
     * Make a string out of method.
     * @param method The method
     * @param args Actual arguments of the method
     * @param trim Shall we trim long texts?
     * @return Text representation of it
     * @deprecated Use toText() instead
     */
    @Deprecated
    public static String toString(final Method method, final Object[] args,
        final boolean trim) {
        return Mnemos.toText(method, args, trim);
    }

    /**
     * Make a string out of an object.
     * @param arg The argument
     * @param trim Shall we trim long texts?
     * @return Text representation of it
     */
    @SuppressWarnings("PMD.AvoidCatchingThrowable")
    public static String toText(final Object arg, final boolean trim) {
        final StringBuilder text = new StringBuilder();
        if (arg == null) {
            text.append("NULL");
        } else {
            try {
                final String mnemo = Mnemos.toText(arg);
                if (trim) {
                    text.append(Logger.format("%[text]s", mnemo));
                } else {
                    text.append(mnemo);
                }
            // @checkstyle IllegalCatch (1 line)
            } catch (Throwable ex) {
                text.append(
                    String.format(
                        "[%s thrown %s]",
                        arg.getClass().getName(),
                        ex
                    )
                );
            }
        }
        return text.toString();
    }

    /**
     * Make a string out of an object.
     * @param arg The argument
     * @param trim Shall we trim long texts?
     * @return Text representation of it
     * @deprecated Use toText() instead
     */
    @Deprecated
    public static String toString(final Object arg, final boolean trim) {
        return Mnemos.toText(arg, trim);
    }

    /**
     * Make a string out of an object.
     * @param arg The argument
     * @return Text representation of it
     */
    private static String toText(final Object arg) {
        String text;
        if (arg.getClass().isArray()) {
            final StringBuilder bldr = new StringBuilder();
            bldr.append('[');
            for (Object item : (Object[]) arg) {
                if (bldr.length() > 1) {
                    bldr.append(Mnemos.COMMA);
                }
                bldr.append(Mnemos.toText(item, false));
            }
            text = bldr.append(']').toString();
        } else {
            final String origin = arg.toString();
            if (arg instanceof String || origin.contains(" ")
                || origin.isEmpty()) {
                text = String.format("'%s'", origin);
            } else {
                text = origin;
            }
        }
        return text;
    }

}
