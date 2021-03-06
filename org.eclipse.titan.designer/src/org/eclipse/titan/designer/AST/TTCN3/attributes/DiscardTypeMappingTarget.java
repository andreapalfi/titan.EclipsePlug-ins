/******************************************************************************
 * Copyright (c) 2000-2017 Ericsson Telecom AB
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package org.eclipse.titan.designer.AST.TTCN3.attributes;

import org.eclipse.titan.designer.AST.ASTVisitor;
import org.eclipse.titan.designer.AST.Type;
import org.eclipse.titan.designer.parsers.CompilationTimeStamp;

/**
 * Represents a discard mapping target.
 *
 * @author Kristof Szabados
 * */
public final class DiscardTypeMappingTarget extends TypeMappingTarget {

	@Override
	/** {@inheritDoc} */
	public TypeMapping_type getTypeMappingType() {
		return TypeMapping_type.DISCARD;
	}

	@Override
	/** {@inheritDoc} */
	public String getMappingName() {
		return "discard";
	}

	@Override
	/** {@inheritDoc} */
	public Type getTargetType() {
		return null;
	}

	@Override
	/** {@inheritDoc} */
	public void check(final CompilationTimeStamp timestamp, final Type source) {
		if (lastTimeChecked != null && !lastTimeChecked.isLess(timestamp)) {
			return;
		}

		lastTimeChecked = timestamp;
	}

	@Override
	/** {@inheritDoc} */
	protected boolean memberAccept(final ASTVisitor v) {
		// no members
		return true;
	}
}
