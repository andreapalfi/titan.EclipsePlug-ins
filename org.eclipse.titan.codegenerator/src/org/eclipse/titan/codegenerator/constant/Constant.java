/******************************************************************************
 * Copyright (c) 2000-2017 Ericsson Telecom AB
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *
 *   Meszaros, Mate Robert
 *
 ******************************************************************************/

package org.eclipse.titan.codegenerator.constant;

import org.eclipse.titan.codegenerator.template.Value;

public class Constant {
	public final String type;
	public final String name;
	public final Value value;

	public Constant(String type, String name, Value value) {
		this.type = type;
		this.name = name;
		this.value = value;
	}
}
