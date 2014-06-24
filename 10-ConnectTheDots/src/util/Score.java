package util;

import players.*;

public interface Score {
    public int getPlayer1();
    public int getPlayer2();
    public String toString();
    public void add(Player player, int n);
    public void add(Score other);
}