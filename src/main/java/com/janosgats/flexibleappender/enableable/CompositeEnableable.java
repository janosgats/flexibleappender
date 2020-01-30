package com.janosgats.flexibleappender.enableable;

import java.util.LinkedList;

public abstract class CompositeEnableable extends AbstractEnableable{

    protected LinkedList<AbstractEnableable> abstractEnableables = new LinkedList<>();

    public LinkedList<AbstractEnableable> getAbstractEnableables() {
        return abstractEnableables;
    }

    public void setAbstractEnableables(LinkedList<AbstractEnableable> abstractEnableables) {
        this.abstractEnableables = abstractEnableables;
    }
}
