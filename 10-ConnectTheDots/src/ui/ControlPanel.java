package ui;

import util.Score;
import util.Clock;
import javax.swing.*;

public interface ControlPanel {

    public void setButtonPanel(JPanel buttonPanel);
    public void setScorePanel(JPanel scorePanel);

    public void setGameScore(Score squares);
    public void setMatchScore(Score games);
    public void setProgress(Clock clock);
    public int getGames();
}
