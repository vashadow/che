/*
 * Copyright (c) 2012-2018 Red Hat, Inc.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Red Hat, Inc. - initial API and implementation
 */
package org.eclipse.che.ide.ui.list;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;

/**
 * Called each time we render an item in the list. Provides an opportunity for implementors/clients
 * to customize the DOM structure of each list item.
 *
 * @author Evgen Vidolob
 */
public abstract class CategoryRenderer<T> {

  public abstract void renderElement(Element element, T data);

  public abstract Element renderCategory(Category<T> category);

  /**
   * A factory method for the outermost element used by a list item.
   *
   * <p>The default implementation returns a div element.
   *
   * @return the element
   */
  public Element createElement() {
    return DOM.createDiv();
  }

  /**
   * A factory method for the category element used by a list item.
   *
   * <p>The default implementation returns a span element.
   *
   * @return the element
   */
  public Element createCategoryElement() {
    return DOM.createSpan();
  }
}
