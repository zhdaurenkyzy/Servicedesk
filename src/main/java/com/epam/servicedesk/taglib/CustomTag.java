package com.epam.servicedesk.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class CustomTag extends TagSupport{
    private static final long serialVersionUID = 1L;

    private boolean displayBody = false;

    public void setDisplayBody(boolean displayBody) {
        this.displayBody = displayBody;
    }

    @Override
    public int doStartTag() throws JspException {
        if( displayBody ){
            return EVAL_BODY_INCLUDE;
        }
        return SKIP_BODY;
    }
}
