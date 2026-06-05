package marks.system;

/**
 * BaseEntity - abstract base class for entities in the marks system.
 */
public abstract class BaseEntity {
    protected String id;

    public BaseEntity(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    // Subclasses must implement a short description
    public abstract String describe();
}
