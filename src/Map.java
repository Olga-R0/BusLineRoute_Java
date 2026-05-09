import java.util.ArrayList;
import java.util.HashMap;

public class Map implements Calculations {
    ArrayList<BusLine>busLine= new ArrayList<BusLine>();
    ArrayList<Point>points=new ArrayList<Point>();
    ArrayList<Station>stations=new ArrayList<Station>();
    HashMap<String,Integer > path = new HashMap<String,Integer >();
    public void ADDBusLine(BusLine b)
    {
        busLine.add(b);
    }
    public void ADDPoint(Point p)
    { int c=0;
        for(Point pp: points)
        {
            if(pp.getName()==p.getName())
                c++;
        }
        if(c==0)
            points.add(p);
            //if(!points.contains(p))
               // points.add(p);
    }
    public  Station getStationByName(String name_)
    {
        for(int i=0; i< stations.size();i++)
            if(stations.get(i).getName().equals(name_))
                return stations.get(i);
        return null;
    }
    public void ADDStations(Station s)
    {
        if(!stations.contains(s))
            stations.add(s);
    }
    public void setBusLine(ArrayList<BusLine> busLine) {
        this.busLine = busLine;
    }

    public void setPoints(ArrayList<Point> points) {
        this.points = points;
    }

    public ArrayList<BusLine> getBusLine() {
        return busLine;
    }

    public ArrayList<Point> getPoints() {
        return points;
    }

    @Override
    public int fastestTime(Station f, Station s) {
        int time=1000;
        //Station f= A.getNearStation();
       // Station s= B.getNearStation();
        for(BusLine b: busLine) {
            int t = b.calculateTime(f, s);
            if (t!=-1)
            {
                t+=f.timeToStation();
                t+=s.timeToStation();
            }
            if (t < time && t!=-1)
                time = t;
        }

       return time;
        //return 0;
    }

    @Override
    public ArrayList<Station> fastestPath(Point A, Point B) {
        return null;
    }
    public HashMap<String, Integer>  Floyid(Point pFrom,Point pTo)
    {
        HashMap<String, Integer> pathAndTime = new HashMap<>();
        Station sFrom= pFrom.getNearStation();
        Station sTo= pTo.getNearStation();
        if(sFrom.equals(sTo))
            return pathAndTime;

        int n = stations.size(); // размер матрицы
        int INF = 99999;  // имитация бесконечности

        int[][] matr = new int[n][n];
        String[][] path = new String[n][n];// матрица пути
            for(int i=0;i<n;i++)
                for (int j = 0; j <n; j++)
                {
                    matr[i][j] = INF;
                    path[i][j]="";
                    if(i==j)
                        matr[i][i]=0;
                }

            for(int i=0;i<busLine.size();i++) {

                var st=busLine.get(i).getStations();
                for (int j = 0; j < st.size()-1; j++)
                {
                    int indLine =stations.indexOf(st.get(j));
                    int indColumn =stations.indexOf(st.get(j + 1));
                    var value=matr[indLine][indColumn];

                    matr[indLine][indColumn]=Math.min(value, busLine.get(i).getTimeBetweenStations().get(j));
                    matr[indColumn][indLine]=matr[indLine][indColumn]; //для двустороннего движения
                    if(value!=matr[indLine][indColumn]) {
                        path[indLine][indColumn] = "|По маршруту: "
                                + busLine.get(i).getName() +
                                " " + st.get(j).getName() + "->" + st.get(j + 1).getName() + "|\n"; //откуда->куда
                        path[indColumn][indLine] = "|По маршруту: "
                                + busLine.get(i).getName() +
                                " " + st.get(j+1).getName() + "->" + st.get(j ).getName() + "|\n"; //откуда->куда

                    }
                }
            }
            for (int i = 0; i < n; i++) //сам алгоритм Флойда
            {
                for (int j = 0; j < n; j++)
                {
                    for (int k = 0; k <n; k++)
                    {
                        if (matr[j][k] > (matr[j][i]+matr[i][k]) )
                        {
                            matr[j][k] = (matr[j][i]+matr[i][k]);
                            path[j][k] = path[j][i]+ path[i][k] ;
                        }
                    }
                }
           }

            int Time=matr[stations.indexOf(sFrom)][stations.indexOf(sTo)];

            String anv = "От\n"+pFrom.getName()+"\n"+path[stations.indexOf(sFrom)][stations.indexOf(sTo)]
                    +" "+"до\n "+pTo.getName()+"\n Чистое время "+ String.valueOf(Time)
                    +"\n Время с учетом точек "+ String.valueOf(Time + pFrom.timeToStation +  pTo.timeToStation);
            if (Time==INF)
                anv= "Нет маршрута между точками";

            pathAndTime.put(anv,Time);
            return pathAndTime;
    }
    public void calcTimeAndP(Point pFrom,Point pTo)
    {
        Station sFrom= pFrom.getNearStation();
        Station sTo= pTo.getNearStation();
        if(sFrom.equals(sTo))
            return;
        int time=0;
        String s="";
        for(int i=0;i<busLine.size();i++)
        {
           BusLine bl= busLine.get(i);
           if(!bl.getStations().contains(sFrom))
               continue;//переходим к другой линии

            if(bl.getStations().contains(sTo)) //на одной линии есть и начальная и конечная станция
            {
                for(int j=bl.getStations().indexOf(sFrom);j<(bl.getStations().size()-1);j++)
                {

                    s+= bl.getStations().get(j).getName();

                    if(bl.getStations().get(j).equals(sTo))
                    {
                        path.put(s, time);
                        s="";
                        time=0;
                        break;
                    }
                    if(j<bl.getTimeBetweenStations().size() && j>0)
                        time+=bl.getTimeBetweenStations().get((j-1));
                }
            }
            outer:
            for(int ii=bl.getStations().indexOf(sFrom);ii<bl.getStations().size();ii++) // начальная есть, но конечной нет, делаем перебор по всем линиям для кажд ст.
            {
                var st=bl.getStations().get(ii);

                for (int iii=1; iii< st.getBuses().size();iii++)
                {
                    var bL=st.getBuses().get(iii);
                    if(bL.getStations().contains(sTo))
                    {
                        for(int y= bL.getStations().indexOf(st); y<(bL.getStations().size()); y++)
                        {
                            s+= bL.getStations().get(y).getName();
                            if((y-1)>=0)
                            time+=bL.getTimeBetweenStations().get((y-1));
                            if(bL.getStations().get(y).equals(sTo))
                            {
                                path.put(s, time);
                                s=sFrom.getName();
                                time=0;
                                continue outer;
                            }

                        }
                    }
                }
            }


        }

    }
}
