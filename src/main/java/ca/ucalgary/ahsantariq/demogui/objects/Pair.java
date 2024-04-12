package ca.ucalgary.ahsantariq.demogui.objects;

import java.util.Objects;

// Abstract class representing a pair of IMDb links
public abstract class Pair implements Comparable<Pair> {
    private final String imdbLink1, imdbLink2; // IMDb links for the pair

    // Constructor to initialize the IMDb links for the pair
    public Pair(String imdbLink1, String imdbLink2) {
        this.imdbLink1 = imdbLink1;
        this.imdbLink2 = imdbLink2;
    }

    // Method to retrieve the first IMDb link in the pair
    public String getImdbLink1() {
        return imdbLink1; // Return the first IMDb link
    }


    // Method to retrieve the second IMDb link in the pair
    public String getImdbLink2() {
        return imdbLink2; // Return the second IMDb link
    }


    // Method to generate a string representation of the Pair object
    @Override
    public String toString() {
        return "core.objects.Pair{" +  // Start building the string with the class name
                "imdbLink1='" + imdbLink1 + '\'' + // Add the first IMDb link
                ", imdbLink2='" + imdbLink2 + '\'' + // Add the second IMDb link
                '}'; // End the string representation
    }


    // Method to compare two Pair objects based on their IMDb links
    @Override
    public int compareTo(Pair pair) {
        // Use CASE_INSENSITIVE_ORDER to compare IMDb links ignoring case sensitivity
        return String.CASE_INSENSITIVE_ORDER.compare(pair.imdbLink1, pair.imdbLink2);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair pair = (Pair) o;
        if (getImdbLink1() == pair.getImdbLink1() && imdbLink2 == pair.imdbLink2) {
            return true;
        } else if (getImdbLink1() == pair.imdbLink2 && imdbLink2 == pair.getImdbLink1()) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(imdbLink1, imdbLink2);
    }
}

