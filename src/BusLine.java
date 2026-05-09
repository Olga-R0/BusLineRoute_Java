import java.util.ArrayList;

public class BusLine {
    private ArrayList<Station> stations =new ArrayList<Station>();
    private String name;
    private ArrayList<Integer> timeBetweenStations= new ArrayList<Integer>();
    public BusLine( String name_)
    {
        name=name_;
    }
    public ArrayList<Integer> getTimeBetweenStations() {
        return timeBetweenStations;
    }

    public void setStations(ArrayList<Station> stations) {
        this.stations = stations;
    }

    public void setTimeBetweenStations(ArrayList<Integer> timeBetweenStations) {
        this.timeBetweenStations = timeBetweenStations;
    }
    //private ModuleHashesBuilder.Graph<Station> station;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public java.util.ArrayList<Station> getStations() {
        return stations;
    }
    public void addStation(Station station)
    {
        if(!stations.contains(station))
            stations.add(station);
    }
    public int calculateTime(Station start, Station end) {
        if (!stations.contains(start) || !stations.contains(end))
            return -1;

        int startPosition = stations.indexOf(start);
        int endPosition=stations.indexOf(end);
        int time=0;
        if(startPosition<endPosition){

            for(int i=startPosition; i<endPosition; i++)
            {
                time += timeBetweenStations.get(startPosition);
            }
        }
        else {
            for (int i = endPosition; i < startPosition; i++) {
                time += timeBetweenStations.get(startPosition);
            }
        }
        return  time;
        }

}
