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

    public String[] BusStratDrive(int line)
    {
        return StationInLine.get(line);
    }

    public void BusArrivedAt(int line,String station)
    {
        stationsAlert.get(station).sendEvent(line);
    }

}
