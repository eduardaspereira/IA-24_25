import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// Define the Ilayout interface with the required methods
interface Ilayout {
    /**
     * @return the children of the receiver.
     */
    List<Ilayout> children();

    /**
     * @return true if the receiver equals the argument l; return false otherwise.
     */
    boolean isGoal(Ilayout l);

    /**
     * @return the cost from the receiver to a successor.
     */
    double getK();
}