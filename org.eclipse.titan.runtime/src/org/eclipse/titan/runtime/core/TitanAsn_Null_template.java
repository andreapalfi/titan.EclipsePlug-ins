/******************************************************************************
 * Copyright (c) 2000-2017 Ericsson Telecom AB
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package org.eclipse.titan.runtime.core;

import java.text.MessageFormat;
import java.util.ArrayList;

import org.eclipse.titan.runtime.core.TitanAsn_Null.Asn_Null_Type;

/**
 * ASN.1 NULL type template
 *
 * @author Kristof Szabados
 * @author Andrea P�lfi
 */
public class TitanAsn_Null_template extends Base_Template {
	private ArrayList<TitanAsn_Null_template> value_list;

	public TitanAsn_Null_template() {
		// intentionally empty
	}

	public TitanAsn_Null_template(final template_sel otherValue) {
		super(otherValue);

		checkSingleSelection(otherValue);
	}

	public TitanAsn_Null_template(final Asn_Null_Type otherValue) {
		super(template_sel.SPECIFIC_VALUE);
	}

	public TitanAsn_Null_template(final TitanAsn_Null otherValue) {
		super(template_sel.SPECIFIC_VALUE);

		if (!otherValue.isBound()) {
			throw new TtcnError("Creating a template from an unbound ASN.1 NULL value.");
		}
	}

	public TitanAsn_Null_template(final TitanAsn_Null_template otherValue) {
		super();

		copyTemplate(otherValue);
	}

	private void copyTemplate(final TitanAsn_Null_template otherValue) {
		switch (otherValue.templateSelection) {
		case SPECIFIC_VALUE:
		case OMIT_VALUE:
		case ANY_VALUE:
		case ANY_OR_OMIT:
			break;
		case VALUE_LIST:
		case COMPLEMENTED_LIST:
			value_list = new ArrayList<TitanAsn_Null_template>(otherValue.value_list.size());
			for(int i = 0; i < otherValue.value_list.size(); i++) {
				final TitanAsn_Null_template temp = new TitanAsn_Null_template(otherValue.value_list.get(i));
				value_list.add(temp);
			}
			break;
		default:
			throw new TtcnError("Copying an uninitialized/unsupported template of ASN.1 NULL type.");
		}

		setSelection(otherValue);
	}

	//originally clean_up
	public void cleanUp() {
		switch (templateSelection) {
		case VALUE_LIST:
		case COMPLEMENTED_LIST:
			value_list.clear();
			value_list = null;
		default:
			break;
		}
		templateSelection = template_sel.UNINITIALIZED_TEMPLATE;
	}

	//originally operator=
	public TitanAsn_Null_template assign( final template_sel otherValue ) {
		checkSingleSelection(otherValue);
		cleanUp();
		setSelection(otherValue);

		return this;
	}

	//originally operator=
	public TitanAsn_Null_template assign( final TitanAsn_Null otherValue ) {
		if (!otherValue.isBound()) {
			throw new TtcnError("Assignment of an unbound ASN.1 NULL value to a template.");
		}

		cleanUp();
		setSelection(template_sel.SPECIFIC_VALUE);

		return this;
	}

	@Override
	public TitanAsn_Null_template assign(final Base_Type otherValue) {
		if (otherValue instanceof TitanAsn_Null) {
			return assign((TitanAsn_Null) otherValue);
		}

		throw new TtcnError(MessageFormat.format("Internal Error: value `{0}'' can not be cast to ASN.1 NULL type", otherValue));
	}

	@Override
	public void log_match(final Base_Type match_value, final boolean legacy) {
		if (match_value instanceof TitanAsn_Null) {
			log_match((TitanAsn_Null) match_value, legacy);
			return;
		}

		throw new TtcnError(MessageFormat.format("Internal Error: value `{0}'' can not be cast to ASN.1 NULL type", match_value));
	}

	//originally operator=
	public TitanAsn_Null_template assign( final TitanAsn_Null_template otherValue ) {
		if (otherValue != this) {
			cleanUp();
			copyTemplate(otherValue);
		}

		return this;
	}

	@Override
	public TitanAsn_Null_template assign(final Base_Template otherValue) {
		if (otherValue instanceof TitanAsn_Null_template) {
			return assign((TitanAsn_Null_template) otherValue);
		}

		throw new TtcnError(MessageFormat.format("Internal Error: value `{0}'' can not be cast to ASN.1 NULL type", otherValue));
	}

	@Override
	public boolean match(final Base_Type otherValue, final boolean legacy) {
		if (otherValue instanceof TitanAsn_Null) {
			return match((TitanAsn_Null) otherValue, legacy);
		}

		throw new TtcnError(MessageFormat.format("Internal Error: value `{0}'' can not be cast to ASN.1 NULL type", otherValue));
	}

	// originally match
	public boolean match(final TitanAsn_Null.Asn_Null_Type otherValue) {
		return match(otherValue, false);
	}

	// originally match
	public boolean match(final TitanAsn_Null otherValue) {
		return match(otherValue, false);
	}

	// originally match
	public boolean match(final TitanAsn_Null.Asn_Null_Type otherValue, final boolean legacy) {
		switch (templateSelection) {
		case OMIT_VALUE:
			return false;
		case SPECIFIC_VALUE:
		case ANY_VALUE:
		case ANY_OR_OMIT:
			return true;
		case VALUE_LIST:
		case COMPLEMENTED_LIST:
			for(int i = 0 ; i < value_list.size(); i++) {
				if(value_list.get(i).match(otherValue, legacy)) {
					return templateSelection == template_sel.VALUE_LIST;
				}
			}
			return templateSelection == template_sel.COMPLEMENTED_LIST;
		case STRING_PATTERN:{
			//TODO: implement
		}
		default:
			throw new TtcnError("Matching with an uninitialized/unsupported template of ASN.1 NULL type.");
		}
	}

	// originally match
	public boolean match(final TitanAsn_Null otherValue, final boolean legacy) {
		if (!otherValue.isBound()) {
			return false;
		}

		return match(Asn_Null_Type.ASN_NULL_VALUE, legacy);
	}

	// originally valueof
	public TitanAsn_Null valueOf() {
		if (templateSelection != template_sel.SPECIFIC_VALUE || is_ifPresent) {
			throw new TtcnError("Performing a valueof or send operation on a non-specific template of ASN.1 NULL type.");
		}

		return new TitanAsn_Null(Asn_Null_Type.ASN_NULL_VALUE);
	}

	//originally set_type
	public void setType(final template_sel templateType, final int listLength){
		if(templateType != template_sel.VALUE_LIST && templateType != template_sel.COMPLEMENTED_LIST){
			throw new TtcnError("Setting an invalid list type for a template of ASN.1 NULL type.");
		}

		cleanUp();
		setSelection(templateType);
		value_list = new ArrayList<TitanAsn_Null_template>(listLength);
		for (int i = 0; i < listLength; i++) {
			value_list.add(new TitanAsn_Null_template());
		}
	}

	public TitanAsn_Null_template listItem(final int listIndex) {
		if (templateSelection != template_sel.VALUE_LIST && templateSelection != template_sel.COMPLEMENTED_LIST) {
			throw new TtcnError("Accessing a list element of a non-list template for ASN.1 NULL type.");
		}
		if (listIndex < 0) {
			throw new TtcnError("Accessing an ASN.1 NULL value list template using a negative index (" + listIndex + ").");
		}
		if (listIndex >= value_list.size()) {
			throw new TtcnError("Index overflow in a list template of ASN.1 NULL type.");
		}

		return value_list.get(listIndex);
	}

	public void log() {
		switch (templateSelection) {
		case SPECIFIC_VALUE:
			TtcnLogger.log_event_str("NULL");
			break;
		case COMPLEMENTED_LIST:
			TtcnLogger.log_event_str("complement");
		case VALUE_LIST:
			TtcnLogger.log_char('(');
			for (int i = 0; i < value_list.size(); i++) { // nbits
				if (i > 0) {
					TtcnLogger.log_event_str(", ");
				}
				value_list.get(i).log();
			}
			TtcnLogger.log_char(')');
			break;
		default:
			log_generic();
			break;
		}
		log_ifpresent();
	}

	public void log_match(final TitanAsn_Null match_value, final boolean legacy) {
		if (TtcnLogger.matching_verbosity_t.VERBOSITY_COMPACT == TtcnLogger.get_matching_verbosity()
				&& TtcnLogger.get_logmatch_buffer_len() != 0) {
			TtcnLogger.print_logmatch_buffer();
			TtcnLogger.log_event_str(" := ");
		}
		match_value.log();
		TtcnLogger.log_event_str(" with ");
		log();
		if (match(match_value)) {
			TtcnLogger.log_event_str(" matched");
		} else {
			TtcnLogger.log_event_str(" unmatched");
		}
	}

	// originally is_present (with default parameter)
	public boolean isPresent() {
		return isPresent(false);
	}

	public boolean isPresent(final boolean legacy) {
		if (templateSelection == template_sel.UNINITIALIZED_TEMPLATE) {
			return false;
		}

		return !match_omit(legacy);
	}

	public boolean match_omit() {
		return match_omit(false);
	}

	public boolean match_omit(final boolean legacy) {
		if (is_ifPresent) {
			return true;
		}

		switch (templateSelection) {
		case OMIT_VALUE:
		case ANY_OR_OMIT:
			return true;
		case VALUE_LIST:
		case COMPLEMENTED_LIST:
			if (legacy) {
				for (int i = 0; i < value_list.size(); i++) {
					if (value_list.get(i).match_omit()) {
						return templateSelection == template_sel.VALUE_LIST;
					}
				}
				return templateSelection == template_sel.COMPLEMENTED_LIST;
			}
			return false;
		default:
			return false;
		}
	}

	@Override
	/** {@inheritDoc} */
	public void encode_text(final Text_Buf text_buf) {
		encode_text_base(text_buf);

		switch (templateSelection) {
		case SPECIFIC_VALUE:
		case OMIT_VALUE:
		case ANY_VALUE:
		case ANY_OR_OMIT:
			break;
		case VALUE_LIST:
		case COMPLEMENTED_LIST:
			text_buf.push_int(value_list.size());
			for (int i = 0; i < value_list.size(); i++) {
				value_list.get(i).encode_text(text_buf);
			}
			break;
		default:
			throw new TtcnError("Text encoder: Encoding an uninitialized/unsupported template of ASN.1 NULL type.");
		}
	}

	@Override
	/** {@inheritDoc} */
	public void decode_text(final Text_Buf text_buf) {
		cleanUp();
		decode_text_base(text_buf);

		switch (templateSelection) {
		case SPECIFIC_VALUE:
		case OMIT_VALUE:
		case ANY_VALUE:
		case ANY_OR_OMIT:
			break;
		case VALUE_LIST:
		case COMPLEMENTED_LIST:
			value_list = new ArrayList<TitanAsn_Null_template>(text_buf.pull_int().getInt());
			for (int i = 0; i < value_list.size(); i++) {
				final TitanAsn_Null_template temp = new TitanAsn_Null_template();
				temp.decode_text(text_buf);
				value_list.add(temp);
			}
			break;
		default:
			throw new TtcnError("Text decoder: An unknown/unsupported selection was received in a template for ASN.1 NULL type.");
		}
	}
}
