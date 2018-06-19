package Other;

import org.w3c.dom.events.Event;

import java.util.Map;

public class MessageManager
{
    private  Map<String,Event64> stationsAlert;
    private Map<Integer,String[]> StationInLine; //what station in the line x

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
