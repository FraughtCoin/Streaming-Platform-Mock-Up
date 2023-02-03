package commands;

public abstract class Command {

    protected int id;
    protected String commandName;
    protected String[] args;

    public abstract void run();
}
