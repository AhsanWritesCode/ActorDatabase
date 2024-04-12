package ca.ucalgary.ahsantariq.demogui.objects;

// Class representing a pair of actors, inheriting from the Pair class
public class ActorPair extends Pair {
    // Constructor to initialize the ActorPair with IMDb links of actors
    public ActorPair(String imdbLink1, String imdbLink2) {
        super(imdbLink1, imdbLink2);
    }

    // Method to generate a string representation of the ActorPair object
    @Override
    public String toString() {
        return "core.objects.ActorPair{" +
                "imdbLink1='" + this.getImdbLink1() + '\'' + // Add the first actor's IMDb link
                ", imdbLink2='" + this.getImdbLink2() + '\'' + // Add the second actor's IMDb link
                '}';
    }
}
