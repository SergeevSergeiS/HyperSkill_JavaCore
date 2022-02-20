package metro;

import java.util.ArrayList;

public class Station {

    private final String name;
    private final String line;
    private int time;
    private final ArrayList<Transfer> transfers;
    private final ArrayList<Station> nextStations;
    private final ArrayList<Station> prevStations;

    public Station(String name, String line, int time) {
        this.name = name;
        this.line = line;
        this.time = time;
        transfers = new ArrayList<>();
        nextStations = new ArrayList<>();
        prevStations = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addTransfer(Transfer transfer) {
        transfers.add(transfer);
    }

    public ArrayList<Transfer> getTransfers() {
        return transfers;
    }

    public String getLine() {
        return line;
    }

    public int getTime() {
        return time;
    }

    public ArrayList<Station> getNextStations() {
        return nextStations;
    }

    public ArrayList<Station> getPrevStations() {
        return prevStations;
    }

    public void addNextStation(Station station) {
        nextStations.add(station);
    }

    public void addPrevStation(Station station) {
        prevStations.add(station);
    }

    public void setTime(int time) {
        this.time = time;
    }

    @Override
    public int hashCode() {
        return name.hashCode() + line.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj instanceof Station) {
            return this.name.equals(((Station) obj).name) && this.line.equals(((Station) obj).line);
        } else {
            return false;
        }
    }

}
