/******************************************************************************
 * Copyright (c) 2000-2017 Ericsson Telecom AB
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package org.eclipse.titan.designer.AST.ASN1;

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.IPreferencesService;
import org.eclipse.titan.designer.GeneralConstants;
import org.eclipse.titan.designer.AST.ASTVisitor;
import org.eclipse.titan.designer.AST.Constraint;
import org.eclipse.titan.designer.AST.INamedNode;
import org.eclipse.titan.designer.parsers.CompilationTimeStamp;
import org.eclipse.titan.designer.preferences.PreferenceConstants;
import org.eclipse.titan.designer.productUtilities.ProductConstants;

/**
 * Represents a permitted alphabet constraint
 *
 * @author Kristof Szabados
 */
public class PermittedAlphabetConstraint extends Constraint {
	private static final String FULLNAMEPART = ".<permitted alphabet constraint>";

	private Constraint constraint;

	public PermittedAlphabetConstraint(final Constraint constraint) {
		super(Constraint_type.CT_PERMITTEDALPHABET);
		this.constraint = constraint;
	}

	@Override
	/** {@inheritDoc} */
	public PermittedAlphabetConstraint newInstance() {
		return new PermittedAlphabetConstraint(constraint);
	}

	@Override
	/** {@inheritDoc} */
	public StringBuilder getFullName(final INamedNode child) {
		final StringBuilder builder = super.getFullName(child);

		if (constraint == child) {
			return builder.append(FULLNAMEPART);
		}

		return builder;
	}

	@Override
	/** {@inheritDoc} */
	public void check(CompilationTimeStamp timestamp) {
		//Not Yet supported
		final IPreferencesService preferenceService = Platform.getPreferencesService();
		final String option = preferenceService.getString(ProductConstants.PRODUCT_ID_DESIGNER, PreferenceConstants.REPORTUNSUPPORTEDCONSTRUCTS, GeneralConstants.WARNING, null);
		myType.getLocation().reportConfigurableSemanticProblem(option, "Permitted alphabet constraint not yet supported");
	}

	@Override
	/** {@inheritDoc} */
	protected boolean memberAccept(ASTVisitor v) {
		// TODO
		return true;
	}

}
