/* This file is part of SableCC ( http://sablecc.org ).
 *
 * See the NOTICE file distributed with this work for copyright information.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.sablecc.sablecc.exception;

/**
 * An invalid argument runtime exception wraps an invalid argument exception
 * into a runtime exception.
 */
@SuppressWarnings("serial")
public class InvalidArgumentRuntimeException
        extends RuntimeException {

    /** The wrapped invalid argument exception. */
    private InvalidArgumentException invalidArgumentException;

    /**
     * Constructs an exception wraper.
     */
    public InvalidArgumentRuntimeException(
            InvalidArgumentException invalidArgumentException) {

        super(invalidArgumentException);

        if (invalidArgumentException == null) {
            throw new InternalException(
                    "invalidArgumentException may not be null");
        }

        this.invalidArgumentException = invalidArgumentException;
    }

    /** Returns the wrapped exception. */
    public InvalidArgumentException getInvalidArgumentException() {

        return this.invalidArgumentException;
    }
}
