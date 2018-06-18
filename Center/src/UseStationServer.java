import BusServerPkg.BusServer;
import Other.MessageManager;
import StationServerPkg.StationServer;

public class UseStationServer
{
    public static void main(String[] args)
    {
        MessageManager messageManager = new MessageManager();
        StationServer stationServer = new StationServer(messageManager);
        BusServer busServer = new BusServer(messageManager);
    }
}
