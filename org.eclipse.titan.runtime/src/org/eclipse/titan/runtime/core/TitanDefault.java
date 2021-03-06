/******************************************************************************
 * Copyright (c) 2000-2017 Ericsson Telecom AB
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package org.eclipse.titan.runtime.core;

import java.text.MessageFormat;

/**
 * TTCN-3 default
 *
 * @author Kristof Szabados
 *
 */
public class TitanDefault extends Base_Type {
	// TODO check if we could use null instead of this object
	static final Default_Base UNBOUND_DEFAULT = new Default_Base();

	Default_Base default_ptr;

	public TitanDefault() {
		default_ptr = UNBOUND_DEFAULT;
	}

	//originally has component parameter
	public TitanDefault(final int otherValue) {
		if (otherValue != TitanComponent.NULL_COMPREF) {
			throw new TtcnError("Initialization from an invalid default reference.");
		}

		default_ptr = null;
	}

	public TitanDefault(final Default_Base aOtherValue) {
		default_ptr = aOtherValue;
	}

	public TitanDefault(final TitanDefault aOtherValue) {
		if (aOtherValue.default_ptr == UNBOUND_DEFAULT) {
			throw new TtcnError( "Copying an unbound default reference." );
		}

		default_ptr = aOtherValue.default_ptr;
	}

	//originally operator= with component parameter
	public TitanDefault assign(final int otherValue) {
		if (otherValue != TitanComponent.NULL_COMPREF) {
			throw new TtcnError( "Assignment of an invalid default reference." );
		}

		default_ptr = null;
		return this;
	}

	//originally operator=
	public TitanDefault assign(final Default_Base otherValue) {
		if (otherValue == UNBOUND_DEFAULT) {
			throw new TtcnError( "Assignment of an unbound default reference." );
		}

		default_ptr = otherValue;
		return this;
	}

	//originally operator=
	public TitanDefault assign(final TitanDefault otherValue) {
		if (otherValue.default_ptr == UNBOUND_DEFAULT) {
			throw new TtcnError( "Assignment of an unbound default reference." );
		}

		if (otherValue != this) {
			default_ptr = otherValue.default_ptr;
		}

		return this;
	}

	@Override
	public Base_Type assign(final Base_Type otherValue) {
		if (otherValue instanceof TitanDefault) {
			return assign((TitanDefault)otherValue);
		}

		throw new TtcnError(MessageFormat.format("Internal Error: value `{0}'' can not be cast to default", otherValue));
	}

	//originally operator== with component parameter
	public boolean operatorEquals(final int otherValue) {
		if (default_ptr == UNBOUND_DEFAULT) {
			throw new TtcnError("The left operand of comparison is an unbound default reference.");
		}
		if (otherValue != TitanComponent.NULL_COMPREF) {
			throw new TtcnError("Comparison of an invalid default value.");
		}

		return default_ptr == null;
	}

	//originally operator==
	public boolean operatorEquals(final Default_Base otherValue) {
		if (default_ptr == UNBOUND_DEFAULT) {
			throw new TtcnError( "The left operand of comparison is an unbound default reference." );
		}

		return default_ptr == otherValue;
	}

	//originally operator==
	public boolean operatorEquals(final TitanDefault otherValue) {
		if (default_ptr == UNBOUND_DEFAULT) {
			throw new TtcnError( "The left operand of comparison is an unbound default reference." );
		}
		if (otherValue.default_ptr == UNBOUND_DEFAULT) {
			throw new TtcnError( "The right operand of comparison is an unbound default reference." );
		}

		return default_ptr == otherValue.default_ptr;
	}

	@Override
	public boolean operatorEquals(final Base_Type otherValue) {
		if (otherValue instanceof TitanDefault) {
			return operatorEquals((TitanDefault)otherValue);
		}

		throw new TtcnError(MessageFormat.format("Internal Error: value `{0}'' can not be cast to default", otherValue));

	}

	//originally operator!= with component parameter
	public boolean operatorNotEquals(final int otherValue) {
		return !operatorEquals(otherValue);
	}

	//originally operator!=
	public boolean operatorNotEquals(final Default_Base otherValue) {
		return !operatorEquals(otherValue);
	}

	//originally operator!=
	public boolean operatorNotEquals(final TitanDefault otherValue) {
		return !operatorEquals(otherValue);
	}

	//originally operator Default_Base*
	public Default_Base getDefaultBase() {
		if (default_ptr == UNBOUND_DEFAULT) {
			throw new TtcnError("Using the value of an unbound default reference.");
		}

		return default_ptr;
	}

	@Override
	public boolean isPresent() {
		return isBound();
	}

	@Override
	public boolean isBound() {
		return default_ptr != UNBOUND_DEFAULT;
	}

	@Override
	public boolean isValue() {
		return default_ptr != UNBOUND_DEFAULT;
	}

	public void cleanUp() {
		default_ptr = UNBOUND_DEFAULT;
	}

	public void log() {
		TTCN_Default.log(default_ptr);
	}

	@Override
	/** {@inheritDoc} */
	public void encode_text(final Text_Buf text_buf) {
		throw new TtcnError("Default references cannot be sent to other test components.");
	}

	@Override
	/** {@inheritDoc} */
	public void decode_text(final Text_Buf text_buf) {
		throw new TtcnError("Default references cannot be received from other test components.");
	}

	//originally static operator== with component parameter
	public static boolean operatorEquals(final int defaultValue, final TitanDefault otherValue) {
		if (defaultValue != TitanComponent.NULL_COMPREF) {
			throw new TtcnError("The left operand of comparison is an invalid default reference.");
		}
		if (otherValue.default_ptr == UNBOUND_DEFAULT) {
			throw new TtcnError("The right operand of comparison is an unbound default reference.");
		}

		return otherValue.default_ptr == null;
	}

	//originally static operator== with component parameter
	public static boolean operatorEquals(final Default_Base defaultValue, final TitanDefault otherValue) {
		if (otherValue.default_ptr == UNBOUND_DEFAULT) {
			throw new TtcnError("The right operand of comparison is an unbound default reference.");
		}

		return defaultValue == otherValue.default_ptr;
	}

	//originally static operator!= with component parameter
	public static boolean operatorNotEquals(final int defaultValue, final TitanDefault otherValue) {
		return !operatorEquals(defaultValue, otherValue);
	}

	//originally static operator!= with component parameter
	public static boolean operatorNotEquals(final Default_Base defaultValue, final TitanDefault otherValue) {
		return !operatorEquals(defaultValue, otherValue);
	}
}
