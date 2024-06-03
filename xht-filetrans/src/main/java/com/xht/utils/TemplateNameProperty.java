package com.xht.utils;

import java.beans.PropertyDescriptor;

public class TemplateNameProperty {
    /**
     *
     */
    private static final long serialVersionUID = -5907127777449933308L;

    private String _templateName;

    private PropertyDescriptor _property;

    public String getTemplateName() {
        return _templateName;
    }

    public void setTemplateName(String templateName) {
        this._templateName = templateName;
    }

    public PropertyDescriptor getProperty() {
        return _property;
    }

    public void setProperty(PropertyDescriptor property) {
        this._property = property;
    }

    public TemplateNameProperty() {
    }

    public TemplateNameProperty(String templateName, PropertyDescriptor property) {
        _templateName = templateName;
        _property = property;
    }
}
