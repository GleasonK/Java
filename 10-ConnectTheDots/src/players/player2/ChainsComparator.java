//File: ChainsComparator.java
//Name: Kevin Gleason & Andrew Francl
//Date: May 2, 2014
//Use: The comparator used for the PQ in Player1.java

package players.player2;

import java.util.Comparator;

/**
 * Created by Andrew on 5/2/14.
 */
public class ChainsComparator implements Comparator<Chain> {

    @Override
    public int compare(Chain chain, Chain chain2) {
        if (chain.getChainLength() > chain2.getChainLength())
            return 1;
        else if (chain.getChainLength() < chain2.getChainLength())
            return -1;
        else
            return 0;

    }
}
