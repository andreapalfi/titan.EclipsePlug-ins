/******************************************************************************
 * Copyright (c) 2000-2017 Ericsson Telecom AB
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package org.eclipse.titan.executor.executors.single;

import org.eclipse.core.resources.IProject;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.titan.executor.tabpages.maincontroller.SingleMainControllerTab;

/**
 * @author Kristof Szabados
 * */
public final class LaunchShortcutConfig extends org.eclipse.titan.executor.executors.LaunchShortcutConfig {
	@Override
	protected String getConfigurationId() {
		return "org.eclipse.titan.executor.executors.single.LaunchConfigurationDelegate";
	}

	@Override
	protected String getDialogTitle() {
		return "Select single mode execution";
	}

	@Override
	public boolean initLaunchConfiguration(final ILaunchConfigurationWorkingCopy configuration, final IProject project, final String configFilePath) {
		return SingleMainControllerTab.initLaunchConfiguration(configuration, project, configFilePath);
	}
}
