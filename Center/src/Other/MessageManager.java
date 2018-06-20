package Other;


import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MessageManager
{
    private  Map<String,Event64> stationsAlert = new HashMap<>();
    private Map<Integer,String[]> StationInLine = new HashMap<>(); //what station in the line x
    private Random random = new Random();


    public MessageManager()
    {
        StationInLine.put(5,new String[] {"A","B","C"});
        StationInLine.put(6,new String[] {"D","E","F"});
        StationInLine.put(7,new String[] {"G","H","I"});
    }

    public void AddStation(String station, Event64 ev)
    {
        stationsAlert.put(station,ev);
    }

    public String BusStartDrive(int lineNumber)
    {
        String ret = "";
        if(!StationInLine.containsKey(lineNumber))
            System.out.println("Line does not exist");
        else
        {
            String[] stations = StationInLine.get(lineNumber);
            for(String station: stations)
            {
                ret += station + " ";
            }
        }
        return ret;
    }

    public void BusArrivedAt(int line,String station)
    {
        String[] path;
        path = StationInLine.get(line); //get the bus stations

        int i=0;
        while(i < path.length && path[i]!=station )
        {
            i++;
            System.out.println("MassageManager arrivedStation: " + path[i]);
        }

        if(i == path.length) //end route of bus
            stationsAlert.get(path[i]).sendEvent("end");
        else
            for(int k=i; k<path.length; k++) {
                stationsAlert.get(path[k]).sendEvent(line + " " + k * 2); //notice stationd
            }
        //if(!stationsAlert.containsKey(station))
          //  return;

        //stationsAlert.get(station).sendEvent(line);
    }

}
