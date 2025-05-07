public interface GameObserver {
    //for event updates
    void update(EventType type, Object data);
}