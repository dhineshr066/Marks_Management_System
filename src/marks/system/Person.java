package marks.system;

/**
 * Person extends BaseEntity to demonstrate inheritance.
 */
public class Person extends BaseEntity {
    protected String name;

    public Person(String id, String name) {
        super(id);
        this.name = name;
    }

    @Override
    public String describe() {
        return "Person[id=" + id + ", name=" + name + "]";
    }

    public String getName() {
        return name;
    }
}
