package Other;


import java.util.HashMap;
import java.util.Map;

public class MessageManager
{
    private  Map<String,Event64> stationsAlert = new HashMap<>();
    private Map<Integer,String[]> StationInLine = new HashMap<>(); //what station in the line x


    public MessageManager()
    {
        StationInLine.put(5,new String[] {"A","B","C"});
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
        if(!stationsAlert.containsKey(station))
            return;

        stationsAlert.get(station).sendEvent(line);
    }

}
