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
package org.eclipse.che.ide.ui.multisplitpanel.actions;

import org.eclipse.che.ide.api.action.ActionEvent;
import org.eclipse.che.ide.api.action.BaseAction;
import org.eclipse.che.ide.ui.multisplitpanel.SubPanel;

/**
 * Action for horizontally splitting the given {@link SubPanel} on the two sub-panels.
 *
 * @author Artem Zatsarynnyi
 */
public class SplitHorizontallyAction extends BaseAction {

  private final SubPanel subPanel;

  public SplitHorizontallyAction(SubPanel subPanel) {
    super("Split Pane In Two Rows", "Split Pane In Two Rows");
    this.subPanel = subPanel;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    subPanel.splitHorizontally();
  }
}
