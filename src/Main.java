import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Map newMap= new Map();
        File devFile = new File("src/map1.txt");
        Scanner devScanner = new Scanner(devFile);
        ArrayList<String> nameStations= new ArrayList<String>();
        while (devScanner.hasNext())
        {
            String nextLine = devScanner.nextLine();
            String[] devData = nextLine.split(",");
            BusLine bl=new BusLine(devData[0]);

            String[] St= devData[1].split(" ");
            String[] timeToSt= devData[2].split(" ");
            ArrayList<Integer> timeBSt= new ArrayList<Integer>();

            for(int i=0; i< St.length;i++)
            {
                if(nameStations.contains(St[i]))
                {
                    var st= newMap.getStationByName(St[i]);
                    st.SetBuss(bl);
                    bl.addStation(st);
                }
                else {
                    Station s = new Station(St[i]);
                    s.SetBuss(bl);
                    newMap.ADDStations(s);
                    bl.addStation(s);
                    nameStations.add(s.getName());
                }
            }
            for(int i=0;i<timeToSt.length; i++)
            {
                int time=Integer.parseInt(timeToSt[i]);
                timeBSt.add(time);
            }
            bl.setTimeBetweenStations(timeBSt);
            newMap.ADDBusLine(bl);
        }

        System.out.println("Введите от какой точки Вы хотите двигаться. Введите сначала название точки, название ближайшей станции, время до станции через пробел");
        Scanner in = new Scanner(System.in);
        String stFrom = in.nextLine();
        String[] dD = stFrom.split(" ");
        Point pFrom=new Point();
        pFrom.setName(dD[0]);
        int time=Integer.parseInt(dD[2]);
        pFrom.setTimeToStation(time);
        var stationFrom= newMap.getStationByName(dD[1]);
        pFrom.setNearStation(stationFrom);

        System.out.println("Введите до какой точки Вы хотите двигаться. Введите сначала название точки, название ближайшей станции, время до станции через пробел");
        Scanner inn = new Scanner(System.in);
        String stTo = inn.nextLine();
        //String stTo = inn.next();
        String[] dT = stTo.split(" ");
        Point pTo= new Point();
        pTo.setName(dT[0]);
        int time2=Integer.parseInt(dT[2]);
        pTo.setTimeToStation(time2);
        var stationTo= newMap.getStationByName(dT[1]);

        pTo.setNearStation(stationTo);
        var answer= newMap.Floyid(pFrom,pTo);
        System.out.println(answer.keySet());

    }
}
