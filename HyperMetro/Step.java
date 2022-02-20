package metro;

public class Step {

    private final Station currentStation;
    private final Step lastStep;

    public Step(Station currentStation, Step lastStep) {
        this.currentStation = currentStation;
        this.lastStep = lastStep;
    }

    public Station getCurrentStation() {
        return currentStation;
    }

    public Step getLastStep() {
        return lastStep;
    }

}
