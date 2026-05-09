import java.util.ArrayList;

public class Station {
    String name;
    ArrayList<BusLine>buses=new ArrayList<BusLine>();
    Point nearP;

    public Station(String name) {
        this.name = name;
    }

    public Point getNearP() {
        return nearP;
    }

    public void setNearP(Point nearP) {
        this.nearP = nearP;
    }
    public int timeToStation(){
       return  nearP.getTimeToStation();
    }
    public void SetBuss(BusLine bl)
    {
        buses.add(bl);
    }
    public String getName() {
        return name;
    }

    public ArrayList<BusLine> getBuses() {
        return buses;
    }

    public void setBuses(ArrayList<BusLine> buses) {
        this.buses = buses;
    }

    public void setName(String name) {
        this.name = name;
    }
}
