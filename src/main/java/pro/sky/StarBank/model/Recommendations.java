package pro.sky.StarBank.model;

import java.util.Objects;
import java.util.UUID;

public class Recommendations {

    private String name;
    private UUID id;
    private String text;

    public Recommendations() {
    }

    public Recommendations(String name, UUID id, String text) {
        this.name = name;
        this.id = id;
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public UUID getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recommendations that = (Recommendations) o;
        return Objects.equals(name, that.name) && Objects.equals(id, that.id) && Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id, text);
    }

    @Override
    public String toString() {
        return "Recommendations{" +
                "name=" + name +
                ", id='" + id + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
