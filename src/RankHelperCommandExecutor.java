import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * User: Jigsaw
 * Date: 7/4/13
 * Time: 9:47 AM
 */

public class RankHelperCommandExecutor implements CommandExecutor{

    private RankHelper plugin;

    public RankHelperCommandExecutor(RankHelper p){
        plugin = p;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
