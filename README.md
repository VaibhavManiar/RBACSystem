===(RBAC) Role Based Access Control System===

- Focus is on developing RBAC System.
- RBAC system is designed as it can be injectable into the resource management system and extendable.
- Not focused on developing resource management system.
- Not focused on providing features enriched client.
- Focusing on SOLID principles, providing proper abstraction, dependency injection and Dependency inversion.
- Focused of code to interface.
- Here 4 modules are defined.
    - Core: core of RBAC System
    - API: API's which can be shared to access system.
    - Client: Client to Resource.
    - Resource: File resource system.
- Here Resource module is dependent on core and api module.
- Core is not exposed to client.
- Client has dependency of API and Resource modules.
- Action Type is enum.
- Role is defined as interface, as user can create few custom roles as per it's requirement.
- Not providing user group access (Group of users having same role).

- Separate interfaces are defined for service, DAO and DB are defined for User and Auth service.
- Bootstrap is defined to start the resource management.