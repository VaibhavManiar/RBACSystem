package org.auth.api;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public final class CustomRole implements Role {

    private final Set<ActionType> actionTypes;
    private final String name;

    public CustomRole(String name) {
        this.actionTypes = new HashSet<>(1);
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Set<ActionType> getActionType() {
        return actionTypes;
    }

    public CustomRole addActionType(ActionType actionType) {
        if (Objects.isNull(actionType))
            throw new IllegalArgumentException("Action type must not be null.");
        this.actionTypes.add(actionType);
        return this;
    }

    @Override
    public int hashCode() {
        class HashCode {
            int hashCode = 0;
            void add(int hashCode) {
                this.hashCode += hashCode;
            }
            int getHashCode() {
                return this.hashCode;
            }
        }
        HashCode hashCode = new HashCode();
        this.getActionType().forEach(actionType -> hashCode.add(actionType.hashCode()));
        return hashCode.getHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Role) {
            Role that = (Role) obj;
            return this.getActionType().containsAll(that.getActionType()) && that.getActionType().containsAll(this.getActionType());
        }
        return Boolean.FALSE;
    }
}