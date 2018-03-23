package com.jld.base.core.taglib;

import javax.servlet.jsp.JspException;
import org.springframework.web.servlet.tags.Param;
import org.springframework.web.servlet.tags.ParamAware;
import org.springframework.web.servlet.tags.ParamTag;

import com.jld.base.core.utils.AESencrypter;

public class EncryptedParamTag extends ParamTag {

	/**
	 * 
	 */
	private static final long serialVersionUID = 98498795681L;

	private String name;

	private String value;

	private Param param;


	@Override
	public int doEndTag() throws JspException {
		param = new Param();
		param.setName(name);
		if (value != null) {
			try {
				param.setValue(AESencrypter.encrypt(value));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new JspException("Error encrypting parameter value", e);
			}
		}
		else if (getBodyContent() != null) {
			// get the value from the tag body
			param.setValue(getBodyContent().getString().trim());
		}

		// find a param aware ancestor
		ParamAware paramAwareTag = (ParamAware) findAncestorWithClass(this, ParamAware.class);
		if (paramAwareTag == null) {
			throw new JspException("The param tag must be a descendant of a tag that supports parameters");
		}

		paramAwareTag.addParam(param);

		return EVAL_PAGE;
	}

	// tag attribute accessors

	/**
	 * Sets the name of the parameter
	 * 
	 * <p>
	 * Required
	 * 
	 * @param name the parameter name
	 */
	public void setName(String name) {
		
//		StringBuilder newName = new StringBuilder("_encrypted_");
//		newName.append(name);
		
//		this.name = newName.toString();
		this.name = name;
	}

	/**
	 * Sets the value of the parameter
	 * 
	 * <p>
	 * Optional. If not set, the tag's body content is evaluated
	 * 
	 * @param value the parameter value
	 */
	public void setValue(String value) {
		this.value = value;
	}

}
