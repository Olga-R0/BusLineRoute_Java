public class Point {
    Station nearStation;
    int timeToStation;

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public Point() {
    }

    public String getName() {
        return name;
    }

    public Point(String name) {
        this.name = name;
    }

    public int getTimeToStation() {
        return timeToStation;
    }

    public Station getNearStation() {
        return nearStation;
    }

    public void setTimeToStation(int timeToStation) {
        this.timeToStation = timeToStation;
    }

    public void setNearStation(Station nearStation) {
        this.nearStation = nearStation;
    }
}
