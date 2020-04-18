package org.auth.api;

import java.util.HashSet;
import java.util.Set;

public final class ReadRole implements Role {

    private static final String name = "READER";

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Set<ActionType> getActionType() {
        Set<ActionType> actionTypes = new HashSet<>(1);
        actionTypes.add(ActionType.READ);
        return actionTypes;
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