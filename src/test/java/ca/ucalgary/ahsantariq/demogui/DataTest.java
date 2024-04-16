package ca.ucalgary.ahsantariq.demogui;

import ca.ucalgary.ahsantariq.demogui.objects.Actor;
import ca.ucalgary.ahsantariq.demogui.objects.Pair;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DataTest {
    static final String name1 = "Tom Hanks";
    static final int age1 = 67;
    static final int height1 = 178;
    static final String country1 = "USA";
    static final int awards1 = 5;
    static final String imdbLink1 = "https://www.imdb.com/name/nm0000158/";
    static final String name2 = "Jacky Chan";
    static final int age2 = 23;
    static final int height2 = 170;
    static final String country2 = "Canada";
    static final int awards2 = 2;
    static final String imdbLink2 = "https://www.imdb.com/name/nm0000953/";

    private Data data;

    @org.junit.jupiter.api.BeforeEach
    void BeforeEach() {
        data = new Data();
    }

    @org.junit.jupiter.api.Test
    void storeNewActor() {
        assertEquals(0, data.getAllActors().size());
        boolean success = data.storeNewActor(name1, age1, height1, country1, awards1, imdbLink1);
        assertEquals(1, data.getAllActors().size());
        assertEquals(name1, data.getAllActors().get(0).getName());
        assertEquals(age1, data.getAllActors().get(0).getAge());
        assertEquals(height1, data.getAllActors().get(0).getHeight());
        assertEquals(country1, data.getAllActors().get(0).getCountry());
        assertEquals(awards1, data.getAllActors().get(0).getNumberOfAwards());
        assertEquals(imdbLink1, data.getAllActors().get(0).getImdbLink());
        assertTrue(success, "Add was successful");
    }

    // Test Cases for storeNewActor

    // Test case for storing a new actor successfully
    @Test
    void testStoreNewActor_Success() {
        String name = "Actor1";
        int age = 30;
        int height = 180;
        String country = "USA";
        int awards = 2;
        String imdbLink = "Actor1Link";

        assertEquals(0, data.getAllActors().size());
        boolean success = data.storeNewActor(name, age, height, country, awards, imdbLink);
        assertEquals(1, data.getAllActors().size());
        assertEquals(name, data.getAllActors().get(0).getName());
        assertEquals(age, data.getAllActors().get(0).getAge());
        assertEquals(height, data.getAllActors().get(0).getHeight());
        assertEquals(country, data.getAllActors().get(0).getCountry());
        assertEquals(awards, data.getAllActors().get(0).getNumberOfAwards());
        assertEquals(imdbLink, data.getAllActors().get(0).getImdbLink());
        assertTrue(success, "Add was successful");
    }

    // Test case for storing a new actor with existing IMDb link
    @Test
    void testStoreNewActor_ExistingIMDbLink() {
        String name1 = "Actor1";
        int age1 = 30;
        int height1 = 180;
        String country1 = "USA";
        int awards1 = 2;
        String imdbLink1 = "Actor1Link";
        data.storeNewActor(name1, age1, height1, country1, awards1, imdbLink1);

        assertEquals(1, data.getAllActors().size());
        boolean success = data.storeNewActor("Actor2", 35, 175, "UK", 1, imdbLink1);
        assertEquals(1, data.getAllActors().size(), "No new actor should be added when using an existing IMDb link");
        assertFalse(success, "Add should not be successful with an existing IMDb link");
    }

    // Test case for storing multiple new actors
    @Test
    void testStoreNewActor_MultipleActors() {
        // Reset data to ensure a clean test environment
        assertEquals(0, data.getAllActors().size());

        data.storeNewActor("Actor1", 30, 180, "USA", 2, "Actor1Link");
        data.storeNewActor("Actor2", 35, 175, "UK", 1, "Actor2Link");

        assertEquals(2, data.getAllActors().size(), "Multiple actors should be stored successfully");
    }

    @org.junit.jupiter.api.Test
    void storeTwoActors() {
        assertEquals(0, data.getAllActors().size());
        boolean success = data.storeNewActor(name1, age1, height1, country1, awards1, imdbLink1);
        assertEquals(1, data.getAllActors().size());
        assertEquals(name1, data.getAllActors().get(0).getName());
        assertEquals(age1, data.getAllActors().get(0).getAge());
        assertEquals(height1, data.getAllActors().get(0).getHeight());
        assertEquals(country1, data.getAllActors().get(0).getCountry());
        assertEquals(awards1, data.getAllActors().get(0).getNumberOfAwards());
        assertEquals(imdbLink1, data.getAllActors().get(0).getImdbLink());
        assertTrue(success, "Add was successful");
        success = data.storeNewActor(name2, age2, height2, country2, awards2, imdbLink2);
        assertEquals(2, data.getAllActors().size());
        assertEquals(name2, data.getAllActors().get(1).getName());
        assertEquals(age2, data.getAllActors().get(1).getAge());
        assertEquals(height2, data.getAllActors().get(1).getHeight());
        assertEquals(country2, data.getAllActors().get(1).getCountry());
        assertEquals(awards2, data.getAllActors().get(1).getNumberOfAwards());
        assertEquals(imdbLink2, data.getAllActors().get(1).getImdbLink());
        assertTrue(success, "Add was successful");
    }

    @org.junit.jupiter.api.Test
    void storeTwoActorsSameImdbLink() {
        assertEquals(0, data.getAllActors().size());
        boolean success = data.storeNewActor(name1, age1, height1, country1, awards1, imdbLink1);
        assertEquals(1, data.getAllActors().size());
        assertEquals(name1, data.getAllActors().get(0).getName());
        assertEquals(age1, data.getAllActors().get(0).getAge());
        assertEquals(height1, data.getAllActors().get(0).getHeight());
        assertEquals(country1, data.getAllActors().get(0).getCountry());
        assertEquals(awards1, data.getAllActors().get(0).getNumberOfAwards());
        assertEquals(imdbLink1, data.getAllActors().get(0).getImdbLink());
        assertTrue(success);
        success = data.storeNewActor(name2, age2, height2, country2, awards2, imdbLink1);
        assertFalse(success);
        assertEquals(1, data.getAllActors().size());
    }

    // Tests For getAllActors Method
    @org.junit.jupiter.api.Test
    void getAllActors() {
        data.storeNewActor("Tom Hanks", 67, 178, "USA", 5, "https://www.imdb.com/name/nm0000158/");
        data.storeNewActor("Meryl Streep", 72, 168, "USA", 3, "https://www.imdb.com/name/nm0000658/");

        // Retrieve all actors from the database
        List<Actor> allActors = data.getAllActors();

        // Flag variables to track if actors are found
        boolean tomHanksFound = false;
        boolean merylStreepFound = false;

        // Iterate over each actor in the returned list
        for (Actor actor : allActors) {
            String name = (String) actor.getName();
            String imdbLink = (String) actor.getImdbLink();

            // Check if the actor is Tom Hanks
            if (name.equals("Tom Hanks") && imdbLink.equals("https://www.imdb.com/name/nm0000158/")) {
                tomHanksFound = true;
            }

            // Check if the actor is Meryl Streep
            if (name.equals("Meryl Streep") && imdbLink.equals("https://www.imdb.com/name/nm0000658/")) {
                merylStreepFound = true;
            }
        }

        // Verify that both actors are present in the returned list
        assertTrue(tomHanksFound);
        assertTrue(merylStreepFound);
    }

    @org.junit.jupiter.api.Test
    void getActor() {
        // Reset data to ensure a clean test environment
        String imdbLink = "Actor1Link";
        String name = "Actor1";
        int age = 30;
        int height = 180;
        String country = "USA";
        int numberOfAwards = 3;

        // Store a new actor
        data.storeNewActor(name, age, height, country, numberOfAwards, imdbLink);

        // Call the method under test
        Actor actor = data.getActor(imdbLink);

        // Verify that the correct actor is retrieved
        assertNotNull(actor, "The actor should not be null.");
        assertEquals(name, actor.getName(), "The actor's name should match.");
        assertEquals(age, actor.getAge(), "The actor's age should match.");
        assertEquals(height, actor.getHeight(), "The actor's height should match.");
        assertEquals(country, actor.getCountry(), "The actor's country should match.");
        assertEquals(numberOfAwards, actor.getNumberOfAwards(), "The actor's number of awards should match.");
    }

    // Test For core.objects.Actor Exists Method
    @org.junit.jupiter.api.Test
    void actorExists() {
        data.storeNewActor("Tom Hanks", 67, 178, "USA", 5, "https://www.imdb.com/name/nm0000158/");

        // Test case: Check if an existing actor is correctly identified as present in the database
        assertTrue(data.actorExists("https://www.imdb.com/name/nm0000158/"));

        // Test case: Verify that a non-existing actor is correctly identified as not present in the database
        assertFalse(data.actorExists("https://www.imdb.com/name/nm0000000/"));

    }

    // Test For retire Method
    @org.junit.jupiter.api.Test
    void retire() {
        // Reset data to ensure a clean test environment
        String imdbLink = "Actor1Link";
        data.storeNewActor("Actor1", 30, 180, "USA", 2, imdbLink);

        // Call the method under test
        data.retire(imdbLink);

        // Verify that the actor's retired status is correctly updated
        Actor actor = data.getActor(imdbLink);
        assertNotNull(actor, "The actor should exist in the data.");
        assertTrue(actor.isRetired(), "The actor should be retired after calling the retire method.");

    }

    // Test for storeNewCoStar core.objects.Pair method
    @org.junit.jupiter.api.Test
    void storeNewCoStarPair() {
        // Reset data to ensure a clean test environment
        String imdbLink1 = "Actor1Link";
        String imdbLink2 = "Actor2Link";

        // Call the method under test
        boolean result = data.storeNewCoStarPair(imdbLink1, imdbLink2);

        // Verify that the pair is stored correctly
        assertTrue(result, "The pair should be stored successfully.");
        assertTrue(data.checkInPair(imdbLink1), "The first actor should be in a pair.");
        assertTrue(data.checkInPair(imdbLink2), "The second actor should be in a pair.");
    }

    @org.junit.jupiter.api.Test
    void testStoreNewCoStarPair_BothActorsInPair() {
        // Reset data to ensure a clean test environment

        // Store co-star pairs for both actors
        data.storeNewCoStarPair("Actor1Link", "Actor2Link");
        data.storeNewCoStarPair("Actor3Link", "Actor4Link");
        boolean result = data.storeNewCoStarPair("Actor2Link", "Actor3Link");

        // Verify that the method returns false indicating failure to store due to both actors already in pairs
        assertFalse(result, "Storing a co-star pair when both actors are already in pairs should return false.");
    }

    // Test for getPair method
    @org.junit.jupiter.api.Test
    void getPair() {
        String imdbLink1 = "https://www.imdb.com/link1";
        String imdbLink2 = "https://www.imdb.com/link2";
        // Set up test environment
        data.storeNewCoStarPair(imdbLink1, imdbLink2);
        // Call the method under test
        Pair actualPair = data.getPair(imdbLink1);
        // Verify the result
        assertEquals(imdbLink1, ((Pair) actualPair).getImdbLink1());
        assertEquals(imdbLink2,actualPair.getImdbLink2());
        //"The returned pair should match the expected pair."

    }

    /**
     * Test for getAllPairs method
     */

    @org.junit.jupiter.api.Test
    void getAllPairs() {
        String imdbLink1 = "https://www.imdb.com/link1";
        String imdbLink2 = "https://www.imdb.com/link2";
        data.storeNewCoStarPair(imdbLink1, imdbLink2);

        // Call the method under test
        ArrayList<Pair> allPairs = data.getAllPairs();

        // Verify that the returned array contains the expected pair
        boolean pairFound = false;
        for (Pair pair : allPairs) {
            // Checks first and second index for pair
            String pairImdbLink1 = pair.getImdbLink1();
            String pairImdbLink2 = pair.getImdbLink2();
            if ((pairImdbLink1.equals(imdbLink1) && pairImdbLink2.equals(imdbLink2)) ||
                    (pairImdbLink1.equals(imdbLink2) && pairImdbLink2.equals(imdbLink1))) {
                pairFound = true;
                break;
            }
        }
        assertTrue(pairFound, "The stored pair should be present in the returned array.");
    }

    @org.junit.jupiter.api.Test
    void getActorsNotInPair() {
        //Set up test environment
        // Store actors with one actor in a pair and one actor not in any pair
        String imdbLink1 = "https://www.imdb.com/link1";
        String imdbLink2 = "https://www.imdb.com/link2";
        data.storeNewActor("Actor1", 30, 180, "USA", 3, imdbLink1);
        data.storeNewActor("Actor2", 35, 175, "UK", 2, imdbLink2);
        data.storeNewCoStarPair(imdbLink1, imdbLink2);

        // Call the method under test
        ArrayList<Actor> actorsNotInPair = data.getActorsNotInPair();

        // Verify that the returned array contains the expected actor not in a pair
        boolean actorNotInPairFound = false;
        for (Actor actor : actorsNotInPair) {
            String actorImdbLink = actor.getImdbLink();
            if (actorImdbLink.equals(imdbLink1) || actorImdbLink.equals(imdbLink2)) {
                actorNotInPairFound = true;
                break;
            }
        }
        assertFalse(actorNotInPairFound, "The actor in a pair should not be present in the returned array.");
    }

    @org.junit.jupiter.api.Test
    void getActorCount() {
        data.storeNewActor("Actor1", 30, 180, "USA", 3, "https://www.imdb.com/link1");
        data.storeNewActor("Actor2", 35, 175, "UK", 2, "https://www.imdb.com/link2");
        data.storeNewActor("Actor3", 40, 170, "Australia", 4, "https://www.imdb.com/link3");

        // Call the method under test
        int actorCount = data.getActorCount();

        // Verify that the returned actor count matches the expected count
        assertEquals(3, actorCount, "The returned actor count should match the number of stored actors.");
    }

    @org.junit.jupiter.api.Test
    void getPairCount() {
        data.storeNewCoStarPair("Actor1Link", "Actor2Link");
        data.storeNewCoStarPair("Actor3Link", "Actor4Link");
        data.storeNewCoStarPair("Actor5Link", "Actor6Link");

        // Call the method under test
        int pairCount = data.getPairCount();

        // Verify that the returned pair count matches the expected count
        assertEquals(3, pairCount, "The returned pair count should match the number of stored co-star pairs.");
    }


    @org.junit.jupiter.api.Test
    void getLoneActorsCount() {
        // Reset data to ensure a clean test environment
        data.storeNewActor("Actor1", 30, 180, "USA", 2, "Actor1Link");
        data.storeNewActor("Actor2", 35, 175, "UK", 1, "Actor2Link");
        data.storeNewActor("Actor3", 40, 170, "Canada", 3, "Actor3Link");

        // Call the method under test
        int loneActorsCount = data.getLoneActorsCount();

        // Verify that the returned count of lone actors matches the expected count
        assertEquals(3, loneActorsCount, "The returned count of lone actors should match the number of actors not in any co-star pair.");
    }

    // Test case for checking an actor not in any pair
    @org.junit.jupiter.api.Test
    void testCheckInPair_NotInPair() {
        // Reset data to ensure a clean test environment
        boolean result = data.checkInPair("Actor1Link");
        assertFalse(result, "An actor not in any pair should return false when checked for being in a pair.");
    }

    // Test case for checking an actor in a pair
    @org.junit.jupiter.api.Test
    void testCheckInPair_InPair() {
        // Reset data to ensure a clean test environment
        data.storeNewCoStarPair("Actor1Link", "Actor2Link");
        boolean result = data.checkInPair("Actor1Link");
        assertTrue(result, "An actor in a pair should return true when checked for being in a pair.");
    }

    // Test case for checking an actor in a different pair
    @org.junit.jupiter.api.Test
    void testCheckInPair_InDifferentPair() {
        // Reset data to ensure a clean test environment
        data.storeNewCoStarPair("Actor1Link", "Actor2Link");
        boolean result = data.checkInPair("Actor2Link");
        assertTrue(result, "An actor in a different pair should return true when checked for being in a pair.");
    }

    // Test case for retrieving all pairs when there are no pairs
    @Test
    void testGetAllPairs_NoPairs() {
        // Reset data to ensure a clean test environment
        ArrayList<Pair> pairs = data.getAllPairs();
        assertNotNull(pairs, "List of pairs should not be null");
        assertTrue(pairs.isEmpty(), "List of pairs should be empty when no pairs are stored");
    }

    // Test case for counting lone actors when there are no actors
    @Test
    void testGetLoneActorsCount_NoActors() {
        // Reset data to ensure a clean test environment
        int loneActorsCount = data.getLoneActorsCount();
        assertEquals(0, loneActorsCount, "Lone actors count should be zero when there are no actors");
    }

    // Test case for counting lone actors when there is one lone actor
    @Test
    void testGetLoneActorsCount_OneLoneActor() {
        // Reset data to ensure a clean test environment
        data.storeNewActor("Actor1", 30, 180, "USA", 2, "Actor1Link");
        int loneActorsCount = data.getLoneActorsCount();
        assertEquals(1, loneActorsCount, "Lone actors count should be one when there is one lone actor");
    }

    // Test case for counting lone actors when there are multiple lone actors
    @Test
    void testGetLoneActorsCount_MultipleLoneActors() {
        // Reset data to ensure a clean test environment
        data.storeNewActor("Actor1", 30, 180, "USA", 2, "Actor1Link");
        data.storeNewActor("Actor2", 35, 175, "UK", 1, "Actor2Link");
        data.storeNewActor("Actor3", 40, 170, "Canada", 3, "Actor3Link");
        int loneActorsCount = data.getLoneActorsCount();
        assertEquals(3, loneActorsCount, "Lone actors count should match the number of actors not in any co-star pair");
    }

    // Test case for retiring an actor successfully
    @Test
    void testRetire_Success() {
        // Reset data to ensure a clean test environment
        data.storeNewActor("Actor1", 30, 180, "USA", 2, "Actor1Link");
        data.retire("Actor1Link");
        assertTrue((boolean) data.getActor("Actor1Link").isRetired(), "The actor should be retired after calling retire()");
    }

    // Test case for retiring an already retired actor
    @Test
    void testRetire_AlreadyRetiredActor() {
        // Reset data to ensure a clean test environment
        data.storeNewActor("Actor1", 30, 180, "USA", 2, "Actor1Link");
        data.retire("Actor1Link");
        data.retire("Actor1Link");
        assertTrue((boolean) data.getActor("Actor1Link").isRetired(), "The actor should remain retired after multiple retire() calls");
    }

    // Test case for getting the count of actors when there are no actors
    @Test
    void testGetActorCount_NoActors() {
        // Reset data to ensure a clean test environment
        assertEquals(0, data.getActorCount(), "The count of actors should be 0 when there are no actors");
    }

    // Test case for getting the count of actors when there is one actor
    @Test
    void testGetActorCount_OneActor() {
        // Reset data to ensure a clean test environment
        data.storeNewActor("Actor1", 30, 180, "USA", 2, "Actor1Link");
        assertEquals(1, data.getActorCount(), "The count of actors should be 1 when there is one actor");
    }

    // Test case for getting the count of actors when there are multiple actors
    @Test
    void testGetActorCount_MultipleActors() {
        // Reset data to ensure a clean test environment
        data.storeNewActor("Actor1", 30, 180, "USA", 2, "Actor1Link");
        data.storeNewActor("Actor2", 35, 175, "UK", 1, "Actor2Link");
        data.storeNewActor("Actor3", 40, 170, "Canada", 3, "Actor3Link");
        assertEquals(3, data.getActorCount(), "The count of actors should match the number of actors added");
    }

    @Test
    void testAscendingOrderActors() {
        // Reset data to ensure a clean test environment
        data.storeNewActor("Actor1", 30, 180, "USA", 2, "Actor1Link");
        data.storeNewActor("Actor2", 35, 175, "UK", 1, "Actor2Link");
        data.storeNewActor("Actor3", 35, 175, "UK", 1, "Actor3Link");
        data.getActorsInCountryAscendingList();
        assertEquals(data.getActorsPerCountryAscending().get("UK"), 2);
    }
}