package ca.ucalgary.ahsantariq.demogui;

import ca.ucalgary.ahsantariq.demogui.objects.Actor;
import ca.ucalgary.ahsantariq.demogui.objects.ActorPair;
import ca.ucalgary.ahsantariq.demogui.objects.Pair;

import java.util.*;

public class Data {
    private ArrayList<Actor> actors;
    // Define an ArrayList to store pairs of objects
    private ArrayList<Pair> pairs;

    // Define a HashMap for quick lookup based on string keys
    private HashMap<String, Pair> pair_lookup;

    // Define a HashMap to store IMDb links associated with string keys
    private HashMap<String, Actor> actor_lookup;

    // Creates a TreeMap that uses a comparator to be used to sort the number of actors (value) per country (key) in descending order.
    // This hashmap sorting method was learned from https://www.baeldung.com/java-sort-map-descending, however we implemented some changes to ensure duplicate entries were not counted
    private ArrayList<Actor> actorsInCountryAscendingList;
    private ArrayList<Actor> actorsInCountryDescendingList;
    private SortedMap<String, Integer> actorsPerCountryAscending;
    private SortedMap<String, Integer> actorsPerCountryDescending;

    public Data() {
        actors = new ArrayList<>();
        // Define an ArrayList to store pairs of objects
        pairs = new ArrayList<>();

        // Define a HashMap for quick lookup based on string keys
        pair_lookup = new HashMap<>();

        // Define a HashMap to store IMDb links associated with string keys
        actor_lookup = new HashMap<>();

        actorsInCountryAscendingList = new ArrayList<>();

        actorsInCountryDescendingList = new ArrayList<>();

        actorsPerCountryAscending = new TreeMap<>(Comparator.naturalOrder());

        actorsPerCountryDescending = new TreeMap<>(Comparator.reverseOrder());
    }

    // Retrieve all actors stored in the actors list
    public ArrayList<Actor> getAllActors() {
        return actors;
    }

    public ArrayList<Pair> getAllPairs() {
        return pairs;
    }

    /**
     * Stores information about a new actor if they don't already exist.
     *
     * @param name           The name of the actor.
     * @param age            The age of the actor.
     * @param height         The height of the actor.
     * @param country        The country of origin of the actor.
     * @param numberOfAwards The number of awards received by the actor.
     * @param imdbLink       The IMDb link associated with the actor.
     * @return true if the actor is successfully stored, false if the actor already exists.
     */
    public boolean storeNewActor(String name, int age, int height, String country, int numberOfAwards, String imdbLink) {
        // Check if the actor already exists using IMDb link
        if (!actorExists(imdbLink)) {
            // If actor doesn't exist, create an array to store actor's information
            Actor actor = new Actor(name, age, height, country, numberOfAwards, imdbLink);
            // Add the actor's information to the list of actors and IMDb links HashMap
            actors.add(actor);
            actor_lookup.put(imdbLink, actor);
            return true; // core.objects.Actor successfully stored
        } else {
            return false; // core.objects.Actor already exists
        }
    }


    // Store information about a new co-star pair if both actors in the pair don't already have a pair associated with them
    public boolean storeNewCoStarPair(String imdbLink1, String imdbLink2) {
        // Check if both actors in the pair are not already in a pair
        if (!checkInPair(imdbLink1) && !checkInPair(imdbLink2)) {
            // Create an array to store information about the co-star pair
            Pair pair = new ActorPair(imdbLink1, imdbLink2);
            pairs.add(pair);
            pair_lookup.put(imdbLink1, pair);
            pair_lookup.put(imdbLink2, pair);
            return true; // core.objects.Pair successfully stored
        } else {
            return false; // core.objects.Pair already exists
        }
    }

    /**
     * Checks imdbLinks hashmap for if it contains the actors imdbLink
     *
     * @param imdbLink
     * @return boolean if the actor exists
     */

    // Check if an actor exists based on their IMDb link
    public boolean actorExists(String imdbLink) {
        return actor_lookup.containsKey(imdbLink);
    }


    // Check if actor is in a co-star pair
    public boolean checkInPair(String imdbLink) {
        return pair_lookup.containsKey(imdbLink);
    }

    // Retrieve information about a specific actor based on their IMDb link
    public Actor getActor(String imdbLink) {
        return actor_lookup.get(imdbLink);
    }


    // Retrieve the co-star pair associated with the given IMDb link
    public Pair getPair(String imdbLink) {
        return pair_lookup.get(imdbLink);
    }

    // Set the retirement status of an actor identified by their IMDb link
    public void retire(String imdbLink) {
        actor_lookup.get(imdbLink).retire();
    }

    // Remove a co-star pair given the IMDb links of the two actors in the pair using equals()
    public void splitPair(String imdbLink1, String imdbLink2) {
        ActorPair dummy = new ActorPair(imdbLink1, imdbLink2);
        pairs.remove(dummy);
        // Remove IMDb links from the pair lookup HashMap
        pair_lookup.remove(imdbLink1);
        pair_lookup.remove(imdbLink2);
    }

    // Retrieve a list of actors who are not part of any co-star pair
    public ArrayList<Actor> getActorsNotInPair() {
        // Create a temporary list to store actors not in a pair
        ArrayList<Actor> temp = new ArrayList<>();
        // Iterate through the list of actors
        for (Actor actor : actors) {
            // Check if the actor's IMDb link is not present in the pair lookup HashMap
            if (!pair_lookup.containsKey(actor.getImdbLink())) {
                // If not present, add the actor to the temporary list
                temp.add(actor);
            }
        }
        return temp; // Return the list of actors not in a pair
    }

    // Get the total number of actors stored in the system
    public int getActorCount() {
        return actors.size();
    }

    // Get the total number of co-star pairs stored in the system
    public int getPairCount() {
        return pairs.size();
    }

    // Get the count of actors who are not part of any co-star pair
    public int getLoneActorsCount() {
        return getActorsNotInPair().size(); // Return the total count of lone actors
    }

    public ArrayList<Actor> getActorsInCountryAscendingList() {
        // Iterate through each actor in data.java
        for (Actor actor : actors) {
            // Get the actor's country
            String country = actor.getCountry();
            // Add 1 to the country's actor count in the hashmap if it already exists, or add it to the hashmap and set it at a default value of 0 if it doesn't.
            if (!actorsInCountryAscendingList.contains(actor)) {
                actorsInCountryAscendingList.add(actor);
                actorsPerCountryAscending.put(country, actorsPerCountryAscending.getOrDefault(country, 0) + 1);
            }
        }
    return actorsInCountryAscendingList;
    }

    public SortedMap<String, Integer> getActorsPerCountryAscending() {
        return actorsPerCountryAscending;
    }

    public ArrayList<Actor> getActorsInCountryDescendingList() {
        // Iterate through each actor in data.java
        for (Actor actor : actors) {
            // Get the actor's country
            String country = actor.getCountry();
            // Add 1 to the country's actor count in the hashmap if it already exists, or add it to the hashmap and set it at a default value of 0 if it doesn't.
            if (!actorsInCountryDescendingList.contains(actor)) {
                actorsInCountryDescendingList.add(actor);
                actorsPerCountryDescending.put(country, actorsPerCountryDescending.getOrDefault(country, 0) + 1);
            }
        }
        return actorsInCountryDescendingList;
    }

    public SortedMap<String, Integer> getActorsPerCountryDescending() {
        return actorsPerCountryDescending;
    }
}
