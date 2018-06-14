import java.util.Map;

public class MessageManager
{
    private Map<Integer,Integer[]> StationInLine; //what station in the line x

    public void AddStation(int station)
    {
        StationInLine.put(station,new Integer[0]);
    }

    public void BusStratDrive()
    {

    }

}
