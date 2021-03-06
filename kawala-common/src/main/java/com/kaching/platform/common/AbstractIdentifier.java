/**
 * Copyright 2010 Wealthfront Inc. Licensed under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package com.kaching.platform.common;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.Serializable;

import com.google.common.base.Objects;

/**
 * Abstract class providing the skeleton to create type-safe identifiers. This
 * class implements {@link #hashCode()}, {@link #equals(Object)},
 * {@link #toString()} and {@link Comparable#compareTo(Object)}.
 *
 * A typical use of this class is to introduce a marker type for a specific
 * identifier. In the following example, we are introducing a {@code PersonId}
 * which wraps a long.
 * <pre>
 * class PersonId extends AbstractIdentifier&lt;Long&gt; {
 *   public PersonId(long id) {
 *     super(id);
 *   }
 * }</pre>
 * Using such identifiers makes it possible to have the compiler ensure these
 * identifiers are used consistently.
 *
 * @param <I> the type of the wrapped identifier
 */
public abstract class AbstractIdentifier<I extends Comparable<I>> implements
    Comparable<AbstractIdentifier<I>>, Serializable {

  private static final long serialVersionUID = 8147623822365809694L;

  private final I id;

  /**
   * Creates an identifier.
   * @param id the wrapped identifier
   */
  protected AbstractIdentifier(I id) {
    this.id = checkNotNull(id);
  }

  /**
   * Gets the wrapped identifier.
   * @return the wrapped identifier
   */
  public I getId() {
    return id;
  }

  @Override
  @SuppressWarnings("unchecked")
  public boolean equals(Object that) {
    return
        this == that ||
        (that != null &&
         this.getClass().equals(that.getClass())) &&
         id.equals(((AbstractIdentifier) that).id);
  }

  @Override
  public int compareTo(AbstractIdentifier<I> that) {
    if (that == null) {
      return 1;
    }
    int result = this.getId().compareTo(that.getId());
    return result != 0 ? result :
      this.getClass().toString().compareTo(
      that.getClass().toString());
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }

  @Override
  public String toString() {
    return String.valueOf(id);
  }

}
