package softuni.models.bindingModels;

public class AddTag {

    private String name;

    public AddTag() {
    }

    public AddTag(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }
}
