package org.auth.api;

import java.util.Set;

public interface Role {
    String getName();
    Set<ActionType> getActionType();
}
