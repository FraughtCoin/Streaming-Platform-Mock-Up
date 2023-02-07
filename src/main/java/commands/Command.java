package commands;

import java.util.Arrays;

public class Command implements CommandInterface{

    protected int id;
    protected String commandName;
    protected String[] args;

    public Command(String line) {
        String[] split = line.split(" ");
        id = Integer.parseInt(split[0]);
        commandName = split[1];
        if (split.length <=2) {
            args = null;
        } else {
            args = Arrays.copyOfRange(split, 2, split.length);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCommandName() {
        return commandName;
    }

    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    @Override
    public void run() {

    }
}
