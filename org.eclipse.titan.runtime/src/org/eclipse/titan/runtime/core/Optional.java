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
 * TTCN-3 boolean
 * @author Kristof Szabados
 */
public class Optional<TYPE extends Base_Type> extends Base_Type {
	public enum optional_sel { OPTIONAL_UNBOUND, OPTIONAL_OMIT, OPTIONAL_PRESENT };
	
	private TYPE optionalValue;
	
	private optional_sel optionalSelection;
	
	private Class<TYPE> clazz;
	
	public Optional(Class<TYPE> clazz) {
		optionalValue = null;
		optionalSelection = optional_sel.OPTIONAL_UNBOUND;
		this.clazz = clazz;
	}
	
	public Optional(final Optional<TYPE> otherValue) {
		//super(otherValue);
		optionalValue = null;
		optionalSelection = otherValue.optionalSelection;
		clazz = otherValue.clazz;
		if(optional_sel.OPTIONAL_PRESENT.equals(otherValue.optionalSelection)) {
			try {
				optionalValue = clazz.newInstance();
			} catch (Exception e) {
				throw new TtcnError(MessageFormat.format("Internal Error: exception `{0}'' thrown while instantiating class of `{1}'' type", e.getMessage(), clazz.getName()));
			}

			optionalValue.assign(otherValue.optionalValue);
		}
	}
	
	//originally clean_up
	public void cleanUp() {
		if (optional_sel.OPTIONAL_PRESENT.equals(optionalSelection)) {
			optionalValue = null;
		}
		optionalSelection = optional_sel.OPTIONAL_UNBOUND;
	}
	
	//originally operator=
	public Optional<TYPE> assign(final optional_sel otherValue) {
		if (!optional_sel.OPTIONAL_OMIT.equals(otherValue)) {
			throw new TtcnError("Internal error: Setting an optional field to an invalid value.");
		}
		setToOmit();
		return this;
	}
	
	//originally operator=
	public Optional<TYPE> assign(final Optional<TYPE> otherValue) {
		switch(otherValue.optionalSelection) {
		case OPTIONAL_PRESENT:
			if(!optional_sel.OPTIONAL_PRESENT.equals(optionalSelection)) {
				try {
					optionalValue = clazz.newInstance();
				} catch (Exception e) {
					throw new TtcnError(MessageFormat.format("Internal Error: exception `{0}'' thrown while instantiating class of `{1}'' type", e.getMessage(), clazz.getName()));
				}
				optionalSelection = optional_sel.OPTIONAL_PRESENT;
			} else {
				optionalValue.assign(otherValue.optionalValue);
			}
			break;
		case OPTIONAL_OMIT:
			if (otherValue != this) {
				setToOmit();
			}
			break;
		default:
			cleanUp();
			break;
		}
		
		return this;
	}
	
	@Override
	public Optional<TYPE> assign(final Base_Type otherValue) {
		if (!(otherValue instanceof Optional<?>)) {
			throw new TtcnError(MessageFormat.format("Internal Error: value `{0}'' can not be cast to optional", otherValue));
		}
		
		Optional<?> optionalOther = (Optional<?>)otherValue;
		
		switch(optionalOther.optionalSelection) {
		case OPTIONAL_PRESENT:
			if(!optional_sel.OPTIONAL_PRESENT.equals(optionalSelection)) {
				try {
					optionalValue = clazz.newInstance();
				} catch (Exception e) {
					throw new TtcnError(MessageFormat.format("Internal Error: exception `{0}'' thrown while instantiating class of `{1}'' type", e.getMessage(), clazz.getName()));
				}
				optionalSelection = optional_sel.OPTIONAL_PRESENT;
			} else {
				optionalValue.assign(optionalOther.optionalValue);
			}
			break;
		case OPTIONAL_OMIT:
			if (optionalOther != this) {
				setToOmit();
			}
			break;
		default:
			cleanUp();
			break;
		}
		
		return this;
	}
	
	public void setToPresent() {
		if (!optional_sel.OPTIONAL_PRESENT.equals(optionalSelection)) {
			optionalSelection = optional_sel.OPTIONAL_PRESENT;
			try {
				optionalValue = clazz.newInstance();
			} catch (Exception e) {
				throw new TtcnError(MessageFormat.format("Internal Error: exception `{0}'' thrown while instantiating class of `{1}'' type", e.getMessage(), clazz.getName()));
			}
		}
	}
	
	public void setToOmit() {
		if (optional_sel.OPTIONAL_PRESENT.equals(optionalSelection)) {
			optionalValue = null;
		}
		optionalSelection = optional_sel.OPTIONAL_OMIT;
	}
	
	public optional_sel getSelection () {
		return optionalSelection;
	}
	
	public boolean isBound() {
		switch (optionalSelection) {
		case OPTIONAL_PRESENT:
		case OPTIONAL_OMIT:
			return true;
		default:
			if (null != optionalValue) {
				return optionalValue.isBound();
			}
			return false;
		}
	}
	
	//originally is_present
	public boolean isPresent() {
		return optional_sel.OPTIONAL_PRESENT.equals(optionalSelection);
	}
	
	/**
	 * Note: this is not the TTCN-3 ispresent(), kept for backward compatibility
	 *       with the runtime and existing testports which use this version where 
	 *       unbound errors are caught before causing more trouble
	 * 
	 * originally ispresent
	 * */
	public boolean isPresentOld() {
		switch (optionalSelection) {
		case OPTIONAL_PRESENT:
			return true;
		case OPTIONAL_OMIT:
			return false;
		default:
			throw new TtcnError("Using an unbound optional field.");
		}
	}

	public boolean isValue() {
		return optional_sel.OPTIONAL_PRESENT.equals(optionalSelection)
				&& optionalValue.isValue();
	}
	
	public boolean isOptional() {
		return true;
	}
	
	//originally operator()
	public TYPE get() {
		setToPresent();
		return optionalValue;
	}
	
	// originally const operator()
	public TYPE constGet() {
		if (!optional_sel.OPTIONAL_PRESENT.equals(optionalSelection)) {
			throw new TtcnError("Using the value of an optional field containing omit.");
		}
		
		return optionalValue;
	}
	
	//originally operator==
	public boolean operatorEquals( final Optional<TYPE> otherValue ) {
		if(optional_sel.OPTIONAL_UNBOUND.equals(optionalSelection)) {
			if(optional_sel.OPTIONAL_UNBOUND.equals(otherValue.optionalSelection)) {
				return true;
			} else {
				throw new TtcnError("The left operand of comparison is an unbound optional value.");
			}
		} else {
			if (optional_sel.OPTIONAL_UNBOUND.equals(otherValue.optionalSelection)) {
				throw new TtcnError("The right operand of comparison is an unbound optional value.");
			} else {
				if(optionalSelection == otherValue.optionalSelection) {
					return false;
				} else if (optional_sel.OPTIONAL_PRESENT.equals(optionalSelection)) {
					return optionalValue.operatorEquals(otherValue.optionalValue);
				} else {
					return true;
				}
			}
		}
	}

	@Override
	public boolean operatorEquals(final Base_Type otherValue) {
		if (otherValue instanceof Optional<?>) {
			return operatorEquals((Optional<?>)otherValue);
		}

		throw new TtcnError(MessageFormat.format("Internal Error: value `{0}'' can not be cast to boolean", otherValue));
	}
	
	
}