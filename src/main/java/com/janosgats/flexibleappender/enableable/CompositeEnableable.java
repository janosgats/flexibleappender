package com.janosgats.flexibleappender.enableable;

import java.util.LinkedList;

/**
 * Execute multiple enablers by using this enabler!
 * <br>
 * Feel free to override one of these Enableables to create your own!
 */
public abstract class CompositeEnableable extends AbstractEnableable {

    protected LinkedList<AbstractEnableable> abstractEnableables = new LinkedList<>();

    public LinkedList<AbstractEnableable> getAbstractEnableables() {
        return abstractEnableables;
    }

    public void setAbstractEnableables(LinkedList<AbstractEnableable> abstractEnableables) {
        this.abstractEnableables = abstractEnableables;
    }
}
