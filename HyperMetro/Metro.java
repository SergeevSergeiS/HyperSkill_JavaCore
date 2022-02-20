package metro;

public class Metro {

    private final String name;
    private final DoubleLinkedList<Station> stations;

    public Metro(String name) {
        this.name = name;
        stations = new DoubleLinkedList<>();
    }

    public String getName() {
        return name;
    }

    public DoubleLinkedList<Station> getStations() {
        return stations;
    }

}
