package io.github.k3ssdev.stacompanion.data;

// TODO: implementar CharacterSheet con los campos necesarios
public class CharacterSheet {
    private String id;
    private String name;
    // otros campos...

    // constructor, getters y setters...

    public CharacterSheet() {
        // Default constructor required for calls to DataSnapshot.getValue(CharacterSheet.class)
    }

    public CharacterSheet(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }


}