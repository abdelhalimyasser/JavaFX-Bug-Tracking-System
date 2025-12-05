import java.time.LocalDateTime;

public abstract class Entity {
    protected int id;
    protected LocalDateTime createdAt;

    public Entity(int id) {
        this.id = id;
        this.createdAt = LocalDateTime.now();
    }

    public int getId() { return id; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
