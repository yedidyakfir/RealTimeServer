package Other;


import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MessageManager
{
     Map<String,Event64> stationsAlert = new HashMap<String, Event64>();
    private Map<Integer,String[]> StationInLine = new HashMap<>(); //what station in the line x



    public MessageManager()
    {
        StationInLine.put(5,new String[] {"A","B","C"});
        StationInLine.put(6,new String[] {"D","E","F"});
        StationInLine.put(7,new String[] {"G","H","I"});
    }

    public void AddStation(String station, Event64 ev)
    {
        stationsAlert.put(station,ev);
        System.out.println("Messagemanager, Added station " + station);
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
//        if(!stationsAlert.containsKey(station))
//            return;

        int index = Arrays.asList(StationInLine.get(line)).indexOf(station);//get the index of the first station
        for(int i = index; i < StationInLine.get(line).length; i++){
            String tempStation = StationInLine.get(line)[i];
            if(stationsAlert.containsKey(tempStation)){
                stationsAlert.get(tempStation).sendEvent(line + " " + String.valueOf(((i - index)*5)));
                System.out.println("Event to station: " + tempStation  + " has been sent" );
            }
        }
    }

}
