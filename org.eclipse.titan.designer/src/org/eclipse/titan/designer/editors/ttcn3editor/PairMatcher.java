/******************************************************************************
 * Copyright (c) 2000-2017 Ericsson Telecom AB
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package org.eclipse.titan.designer.editors.ttcn3editor;

import org.eclipse.titan.designer.editors.GeneralPairMatcher;
import org.eclipse.titan.designer.editors.Pair;

/**
 * @author Kristof Szabados
 * */
public final class PairMatcher extends GeneralPairMatcher {
	public PairMatcher() {
		this.pairs = new Pair[] { new Pair('{', '}'), new Pair('(', ')'), new Pair('[', ']') };
	}

	@Override
	protected String getPartitioning() {
		return PartitionScanner.TTCN3_PARTITIONING;
	}
}
