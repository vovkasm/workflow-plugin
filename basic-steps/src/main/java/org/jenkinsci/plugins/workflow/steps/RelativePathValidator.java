/*
 * The MIT License
 *
 * Copyright 2014 Jesse Glick.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package org.jenkinsci.plugins.workflow.steps;

import hudson.LauncherDecorator;

class RelativePathValidator {

    /**
     * Pointless normally, since shell steps can anyway read and write files anywhere on the slave.
     * Could be necessary in case a plugin installs a {@link LauncherDecorator} which keeps commands inside some kind of jail.
     * In that case we would need some API to determine that such a jail is in effect and this validation must be enforced.
     */
    private static final boolean ENABLED = Boolean.getBoolean(RelativePathValidator.class.getName() + ".ENABLED");

    public static String validate(String path) throws IllegalArgumentException {
        if (!ENABLED) {
            return path;
        }
        if (path.startsWith("/")) {
            throw new IllegalArgumentException("only relative paths are accepted");
        }
        if (path.contains("\\")) {
            throw new IllegalArgumentException("only / is accepted as a path separator");
        }
        if (path.contains("..")) {
            throw new IllegalArgumentException("only relative paths are accepted");
        }
        return path;
    }

    private RelativePathValidator() {}

}
