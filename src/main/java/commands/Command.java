package commands;

import java.util.Arrays;

public class Command implements CommandInterface{

    protected int id;
    protected String[] args;

    public Command(String line) {
        String[] split = line.split(" ");
        id = Integer.parseInt(split[0]);
        if (split.length <= 2) {
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

    @Override
    public void run() {
        //base method
    }
}
