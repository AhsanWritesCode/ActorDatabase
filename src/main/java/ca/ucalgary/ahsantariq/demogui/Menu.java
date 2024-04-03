package ca.ucalgary.ahsantariq.demogui;

import ca.ucalgary.ahsantariq.demogui.comparators.ActorNameImdbLinkComparator;
import ca.ucalgary.ahsantariq.demogui.objects.Actor;
import ca.ucalgary.ahsantariq.demogui.objects.ActorPair;
import ca.ucalgary.ahsantariq.demogui.objects.Pair;
import ca.ucalgary.ahsantariq.demogui.util.FileSaver;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import java.util.*;
import java.io.File;
/**
 * This class represents a menu with various options related to managing actors and co-stars in a movie database.
 */

public class Menu {

    private static Data data = new Data();
    //Scanner object for user input
    private static final Scanner scanner = new Scanner(System.in);
    // List to store menu options
    private static final String[] options = new String[16];

    // Static initializer block to populate the menu options


    static {
        options[0] = ("Exit");
        options[1] = ("Enter a new actor into the database"); // Enter cast members for a film
        options[2] = ("Record 2 actors as co-stars"); // core.objects.Pair 2 actors as co-stars in a film
        options[3] = ("Separate a pair of co-stars"); // Separate 2 actors as co-stars (useful in the event one gets fired)
        options[4] = ("Record an actor's retirement"); // Option to record when an actor is fired or resigns from a movie set
        options[5] = ("Print all actors"); // Option to print information about all actors in the database
        options[6] = ("Print all co-stars"); // Option to print information about all co-stars in the database
        options[7] = ("Print the info of an actor"); // Option to print detailed information about a specific actor
        options[8] = ("How many actors/co-stars are in the database?"); // Option to get the total count of actors and co-stars in the database
        options[9] = ("Print the country with the most actors");
        options[10] = ("Print the country with the fewest actors");
        options[11] = ("Which actors are not in a co-star pair?"); // Option to identify actors who haven't worked together with any co-star
        options[12] = ("What is the average age of the actors in the database?"); // Option to calculate the average age of actors in the database
        options[13] = ("Who is the tallest actor in the database?"); // Option to locate and output the actor with the tallest height in the database
        options[14] = ("Load."); // Option to locate and output the actor with the tallest height in the database
        options[15] = ("Save."); // Option to locate and output the actor with the tallest height in the database

    }

    // Initial message describing the purpose of the program
    private static String optMessage = """
            Store and access information about the actors in the Star Wars films.
            Select an option by typing the associated number and hitting the enter key.
            \tMenu Options
            """;

    // Static block to initialize the menu options
    static {
        // StringBuilder to construct the menu message
        StringBuilder sb = new StringBuilder();
        // Append the initial message
        sb.append(optMessage);
        // Loop through the options list to add each option to the message
        for (int i = 0; i < options.length; i++) {
            // Format the option number and text and append to the StringBuilder
            sb.append(String.format("\t%d. %s\n", i, options[i]));
        }
        // Update optMessage with the constructed menu message
        optMessage = sb.toString();
    }

    // Format string for displaying actor information
    private static final String ACTOR_FORMAT = "%-20s\t%-3s\t%-6s\t%-30s\t%-11s\t%-36s\t%-7s%n";
    // Header for displaying actor information
    private static final String ACTOR_HEADER = String.format(ACTOR_FORMAT, "NAME", "AGE", "HEIGHT", "COUNTRY", "# OF AWARDS", "IMDB LINK", "RETIRED");
    // Separator string for formatting purposes
    private static String ACTOR_SEPARATOR = "";

    // Static block to initialize the separator string
    static {
        // Loop through each character in the actor header and concatenate '-' to form a separator string
        for (int i = 0; i < ACTOR_HEADER.length(); i++) {
            ACTOR_SEPARATOR += "-";
        }
    }

    // Format string for displaying actor pairs
    private static final String PAIR_FORMAT = "%-32s %-33s%n";
    // Header for displaying actor pairs
    private static final String PAIR_HEADER = String.format(PAIR_FORMAT, "IMDBLINK1", "IMDBLINK2");
    // Separator string for formatting purposes

    private static String PAIR_SEP = "";

    // Static block to initialize the separator string
    static {
        // Loop through each character in the pair header and concatenate '-' to form a separator string
        for (int i = 0; i < PAIR_HEADER.length(); i++) {
            PAIR_SEP += "-";
        }
    }

    /**
     * Method to get a valid menu option from the user
     *
     * @return The validated non-empty option provided by the user.
     */
    private static int getOption() {
        String option;
        Integer choice = null;
        // Repeat the input prompt until a non-empty option is provided
        do {
            do {
                System.out.println("Enter an integer menu option: ");
                // Read the user input from the scanner and trim leading/trailing whitespace
                option = scanner.nextLine().trim();
            } while (option.isEmpty());
            try {
                choice = Integer.parseInt(option);
            }catch (NumberFormatException nfe) {
                choice = null;
            }
        } while (choice == null);
        // Return the validated option
        return choice;
    }

    /**
     * Continuously displays the menu options, prompts the user for input,
     * and processes the chosen option until the user chooses to exit.
     */
    public static void menuLoop(File file) {
        if (file != null){
            load(file);
        }
        // Display the menu options
        System.out.println(optMessage);
        // Get the user's choice
        int choice = getOption();
        // Continue looping until the user chooses to exit (option 0)
        while (choice != 0) {
            // Check if the option is within the valid range
            if (choice > 0 && choice < options.length) {
                // Display the selected option
                System.out.printf("Selected option %d. %s%n", choice, options[choice]);
                // Prompt the user to press Enter to continue
                System.out.println("Press the Enter key to continue...");
                // Wait for the user to press Enter before proceeding
                scanner.nextLine();
            }
            /**
             * Executes different actions based on the selected option using a switch statement.
             *
             * @param option The selected option to determine the action to execute.
             */
            switch (choice) {
                // If option 1 is selected, call menuEnterNewActor() method
                case 1 -> menuEnterNewActor();
                // If option 2 is selected, call menuCreateCoStarPair() method
                case 2 -> menuCreateCoStarPair();
                // If option 3 is selected, call menuSplitCoStars() method
                case 3 -> menuSplitCoStars();
                // If option 4 is selected, call menuRecordRetirement() method
                case 4 -> menuRecordRetirement();
                // If option 5 is selected, call menuPrintAllActors() method
                case 5 -> menuPrintAllActors();
                // If option 6 is selected, call menuPrintAllCoStarPairs() method
                case 6 -> menuPrintAllCoStarPairs();
                // If option 7 is selected, call menuPrintActorInfo() method
                case 7 -> menuPrintActorInfo();
                // If option 8 is selected, call menuActorStatistics() method
                case 8 -> menuActorStatistics();
                // If option 9 is selected, call menuCountryMaxStatistics() method
                case 9 -> menuCountryMaxStatistics();
                // If option 10 is selected, call menuCountryMinStatistics() method
                case 10 -> menuCountryMinStatistics();
                // If option 11 is selected, call menuActorsNotInPair() method
                case 11 -> menuActorsNotInPair();
                // If option 12 is selected, call menuAverageAge() method
                case 12 -> menuAverageAge();
                // If option 13 is selected, call menuTallestActor() method
                case 13 -> menuTallestActor();
                case 14 -> load();
                case 15 -> save();
                // If none of the above options match, display an error message
                default -> System.out.printf("Option %d not recognized!%n", choice);
            }
            // Prompt the user to press Enter to continue
            System.out.println("Press the Enter key to continue...");
            // Wait for the user to press Enter before proceeding
            scanner.nextLine();
            // Display the menu options again
            System.out.println(optMessage);
            // Get the next choice from the user
            choice = getOption();
        }
        System.out.println("Thank you for using our actor data base. Bye!");
    }


    private static void load() {
            String filename;
            File file;
            do {
                do {
                    System.out.println("Enter a filename:");
                    filename = scanner.nextLine().trim();
                } while (filename.isEmpty());
                file = new File(filename);
            } while (!file.exists() || !file.canRead());
            load(file);
        }
        private static void load(File file){
            if (data == null){
                System.err.printf("Failed to load from file %s%n", file);
            } else{
                System.out.printf("Loaded data from file %s%n", file);
                Menu.data = data;
            }
        }

    private static void save() {
        String filename;
        File file;
        do {
            do {
                System.out.println("Enter a filename:");
                filename = scanner.nextLine().trim();
            } while (filename.isEmpty());
            file = new File(filename);
        } while (file.exists() && !file.canWrite());
        if (FileSaver.save(file, data)){
            System.out.printf("Saved to file %s%n", filename);
        } else {
            System.err.printf("Failed to save to file %s%n", filename);
        }
    }

    /**
     * Allows the user to enter information about a new actor and stores it in the system.
     * This method prompts the user to input the actor's name, age, height, country, number of awards, and IMDb link.
     * It repeatedly prompts the user until the actor's information is successfully stored in the system.
     * If the actor already exists in the system, it displays a message and prompts the user to try again.
     * Upon successful storage of the actor's information, it displays a success message.
     */
    private static void menuEnterNewActor() {
        boolean success;
        // Repeat the process until the actor information is successfully stored
        do {
            // Prompt the user to enter information about a new actor
            System.out.println("Enter information about a new actor.");
            // Get the name of the actor
            String actorName = getActorName();
            // Get the age of the actor
            int age = getAge();
            // Get the height of the actor
            int actorHeight = getActorHeight();
            // Get the country of the actor
            String country = getCountry();
            // Get the number of awards received by the actor
            int numberOfAwards = getNumberOfAwards();
            // Get the IMDb link of the actor
            String imdbLink = getImdbLink();
            // Store the new actor's information and check if the operation was successful
            success = data.storeNewActor(actorName, age, actorHeight, country, numberOfAwards, imdbLink);
            // If the actor already exists, display a message and prompt the user to try again
            if (!success) {
                System.out.println("Actor already exists in the system!\nTry again!\n");
            }
        } while (!success); // Continue looping until a new actor is successfully stored
        // Once a new actor is successfully stored, display a success message
        System.out.println("Stored a new actor!");
    }

    /**
     * Prompts the user to input the name of the actor and validates the input.
     * This method repeats the prompt until a non-empty name is provided.
     * It reads the user input from the scanner and trims leading/trailing whitespace.
     *
     * @return The name of the actor.
     */
    private static String getActorName() {
        String actorName;
        // Repeat the prompt until a non-empty name is provided
        do {
            System.out.println("Please enter the actor's name: ");
            // Read the user input from the scanner and trim leading/trailing whitespace
            actorName = scanner.nextLine().trim();
        } while (actorName.isEmpty()); // Continue looping if the name is empty
        // Return the validated actor name
        return actorName;
    }

    /**
     * Prompts the user to input the age of the actor and validates the input.
     * This method repeats the prompt until a non-negative age is provided.
     * It reads the user input from the scanner and trims leading/trailing whitespace.
     *
     * @return The age of the actor.
     */
    private static int getAge() {
        String age;
        // Repeat the prompt until a non-negative age is provided
        do {
            System.out.println("Please enter an age: ");
            // Read the user input from the scanner and trim leading/trailing whitespace
            age = scanner.nextLine().trim();
            if (Integer.parseInt(age) < 0){
                System.out.println("Age cannot be less than 0! Please try again.");
            }
            else if (Integer.parseInt(age) > 122){
                System.out.println("Age cannot be greater than 122! Please try again.");
            }
        } while ((Integer.parseInt(age) < 0 || Integer.parseInt(age) > 122)); // Continue looping if the age is invalid
        // Return the validated age
        return Integer.parseInt(age);
    }

    /**
     * Prompts the user to input the height of the actor in centimeters and validates the input.
     * This method repeats the prompt until a non-negative height is provided.
     * It reads the user input from the scanner and trims leading/trailing whitespace.
     *
     * @return The height of the actor in centimeters.
     */
    private static int getActorHeight() {
        String actorHeight;
        // Repeat the prompt until a non-negative height is provided
        do {
            System.out.println("Please enter the actor's height in cm (rounded): ");
            // Read the user input from the scanner and trim leading/trailing whitespace
            actorHeight = scanner.nextLine().trim();
            if (Integer.parseInt(actorHeight) < 0) {
                System.out.println("Height cannot be less than 0! Please try again.");
            }
            else if (Integer.parseInt(actorHeight) >= 273){
                System.out.println("Age cannot be greater than 272 cm! Please try again.");
            }
        } while (Integer.parseInt(actorHeight) < 0 || Integer.parseInt(actorHeight) > 273); // Continue looping if the height is invalid
        // Return the validated height
        return Integer.parseInt(actorHeight);
    }

    /**
     * Prompts the user to input the country the actor is from and validates the input.
     * This method repeats the prompt until a non-empty country is provided.
     * It reads the user input from the scanner and trims leading/trailing whitespace.
     *
     * @return The country the actor is from.
     */
    private static String getCountry() {
        String country;
        // Repeat the prompt until a non-empty country is provided
        do {
            System.out.println("Enter the country the actor is from: ");
            // Read the user input from the scanner and trim leading/trailing whitespace
            country = scanner.nextLine().trim();
            if (country.isEmpty()){
                System.out.println("Country field cannot be empty! Try again.");
            }
        } while (country.isEmpty()); // Continue looping if the country is empty
        // Return the validated country
        return country;
    }

    /**
     * Method to prompt the user to enter the number of major awards won by the actor
     * Prompts the user to input the number of major awards won by the actor and validates the input.
     * This method repeats the prompt until a non-negative number of awards is provided.
     * It reads the user input from the scanner and trims leading/trailing whitespace.
     *
     * @return The number of major awards won by the actor.
     */
    private static int getNumberOfAwards() {
        String numberOfAwards;
        // Repeat the prompt until a non-negative number of awards is provided
        do {
            System.out.println("Please enter the number of major awards the actor won: ");
            // Read the user input from the scanner and trim leading/trailing whitespace
            numberOfAwards = scanner.nextLine().trim();
        } while (Integer.parseInt(numberOfAwards) < 0); // Continue looping if the number of awards is negative
        // Return the validated number of awards
        return Integer.parseInt(numberOfAwards);
    }

    /**
     * Prompts the user to input the IMDb link of the actor and validates the input.
     * This method repeats the prompt until a valid IMDb link, starting with "https://www.imdb.com/", is provided.
     * It reads the user input from the scanner and trims leading/trailing whitespace.
     *
     * @return The IMDb link of the actor.
     */
    private static String getImdbLink() {
        String imdbLink;
        // Repeat the prompt until a valid IMDb link is provided
        do {
            System.out.println("Please enter the actor's IMDb (website) link: ");
            // Read the user input from the scanner and trim leading/trailing whitespace
            imdbLink = scanner.nextLine().trim();
        } while (!imdbLink.startsWith("https://www.imdb.com/")); // Continue looping if the link doesn't start with the expected format
        // Return the validated IMDb link
        return imdbLink;
    }

    // Method to create a co-star pair
    // Actors can only be in 1 co-star pair

    /**
     * Allows the user to create a co-star pair by providing IMDb links of two actors.
     * This method prompts the user to enter the IMDb link of the first and second actors.
     * It checks if both actors exist in the database and if they are already part of another pair.
     * If both actors exist and are not part of any pair, it stores the new co-star pair.
     * It provides feedback based on the success of storing the pair or displays an error message if actors do not exist.
     */
    private static void menuCreateCoStarPair() {
        // Prompt the user to enter the IMDb link of the first actor
        System.out.println("Enter the first actor's IMDb link: ");
        // Get the IMDb link of the first actor
        String imdbLink1 = getImdbLink();

        // Prompt the user to enter the IMDb link of the second actor
        System.out.println("Enter the second actor's IMDb link: ");
        // Get the IMDb link of the second actor
        String imdbLink2 = getImdbLink();

        // Check if both actors exist in the database
        if (data.actorExists(imdbLink1) && data.actorExists(imdbLink2)) {
            // Check if either actor is already in a pair
            if (data.checkInPair(imdbLink1)) {
                System.out.printf("Actor associated with link %s is already in a pair!%n", imdbLink1);
            } else if (data.checkInPair(imdbLink2)) {
                System.out.printf("Actor associated with link %s is already in a pair!%n", imdbLink2);
            } else {
                // If both actors are not in a pair, store the new co-star pair
                boolean success = data.storeNewCoStarPair(imdbLink1, imdbLink2);
                // Provide feedback based on the success of storing the pair
                if (success) {
                    System.out.println("Success");
                } else {
                    System.out.println("Failed");
                }
            }
        } else {
            // If either actor does not exist, display an error message
            System.out.printf("Actor associated with the link %s and the actor associated with the link %s both do not exist!%n", imdbLink1, imdbLink2);
        }
    }

    /**
     * Allows the user to split a co-star pair by providing IMDb links of two actors.
     * This method prompts the user to enter the IMDb links of the first and second actors.
     * It checks if both actors exist in the database and if they are currently paired together.
     * If the provided actors are paired together, it splits the pair.
     * It displays appropriate error messages if actors do not exist or are not paired together.
     */
    private static void menuSplitCoStars() {
        // Prompt the user to enter the IMDb link of the first actor
        System.out.println("Enter the first actor's IMDb link: ");
        String imdbLink1 = getImdbLink();

        // Prompt the user to enter the IMDb link of the second actor
        System.out.println("Enter the second actor's IMDb link: ");
        String imdbLink2 = getImdbLink();

        // Check if both actors exist in the database
        if (data.actorExists(imdbLink1) && data.actorExists(imdbLink2)) {
            // Get the pair associated with the first actor's IMDb link
            Pair pair = data.getPair(imdbLink1);
            if (pair instanceof ActorPair actorPair) {
                String p1 = actorPair.getImdbLink1();
                String p2 = actorPair.getImdbLink1();

                // Check if the provided actors are paired together
                if (Objects.equals(imdbLink1, p1) && !Objects.equals(imdbLink2, p2)) {
                    System.out.printf("Actor %s and actor %s are not paired together!%n", imdbLink1, imdbLink2);
                } else if (Objects.equals(imdbLink1, p2) && !Objects.equals(imdbLink2, p1)) {
                    System.out.printf("Actor %s and actor %s are not paired together!%n", imdbLink1, imdbLink2);
                } else {
                    // If the actors are paired together, split the pair
                    data.splitPair(imdbLink1, imdbLink2);
                    System.out.println("Co-star pair successfully split!");
                }
            }
        } else {
            // If either actor does not exist, display an error message
            System.out.printf("Actor associated with the link %s and the actor associated with the link %s both do not exist!%n", imdbLink1, imdbLink2);
        }
    }

    /**
     * Allows the user to record the retirement of an actor by providing the IMDb link of the actor.
     * This method prompts the user to enter the IMDb link of the actor to retire.
     * It checks if the actor exists in the database and then retires the actor if found.
     * It displays an appropriate error message if the actor does not exist.
     */
    private static void menuRecordRetirement() {
        // Prompt the user to enter the IMDb link of the actor to retire
        String imdbLink = getImdbLink();
        // Check if the actor exists in the database
        if (data.actorExists(imdbLink)) {
            // If the actor exists, retire the actor
            data.retire(imdbLink);
        } else {
            // If the actor does not exist, display an error message
            System.out.printf("Actor with IMDB link %s does not exist!%n", imdbLink);
        }
    }

    /**
     * Prints information about all actors stored in the system.
     * This method displays the name, age, height, number of awards, IMDb link, and retirement status of each actor.
     * It retrieves the actor information from the {@link Data} class and prints it in a formatted manner.
     */
    private static void menuPrintAllActors() {
        // Display header for actor information
        System.out.println(ACTOR_HEADER);
        // Display separator line for formatting
        System.out.println(ACTOR_SEPARATOR);
        // Retrieve and print information about all actors from the data
        ArrayList<Actor> actors = data.getAllActors();
        actors.sort(new ActorNameImdbLinkComparator());
        printActors(actors);
    }

    private static void printActors(ArrayList<Actor> actors) {
        for (Actor actor : actors) {
            // Convert boolean value for retired status to "T" or "F"
            String retired = "F";
            if (actor.isRetired()) {
                retired = "T";
            }
            // Display actor information using formatted string
            System.out.printf(ACTOR_FORMAT, actor.getName(), actor.getAge(), actor.getHeight(), actor.getCountry(), actor.getNumberOfAwards(), actor.getImdbLink(), actor.isRetired());
        }
    }

    /**
     * Prints information about all co-star pairs stored in the system.
     * This method displays the IMDb links of both actors in each co-star pair.
     * It retrieves the pair information from the {@link Data} class and prints it in a formatted manner.
     */
    private static void menuPrintAllCoStarPairs() {
        // Display header for co-star pair information
        System.out.println(PAIR_HEADER);
        // Display separator line for formatting
        System.out.println(PAIR_SEP);
        // Retrieve and print information about all co-star pairs from the data
        ArrayList<Pair> pairs = data.getAllPairs();
        for (Pair pair : pairs) {
            if (pair instanceof ActorPair actorPair) {
                // Display co-star pair information using formatted string
                System.out.printf(PAIR_FORMAT, actorPair.getImdbLink1(), actorPair.getImdbLink2());
            }
            // Display co-star pair information using formatted string

        }
    }
    /**
     * Prints information about a specific actor based on their IMDb link.
     * This method prompts the user to enter the IMDb link of the actor.
     * It retrieves information about the actor from the {@link Data} class and prints it in a formatted manner.
     * If the actor does not exist, it displays an error message.
     */
    private static void menuPrintActorInfo() {
        // Prompt the user to enter the IMDb link of the actor
        String imdbLink = getImdbLink();
        // Retrieve information about the actor from the data
        Actor actor = data.getActor(imdbLink);
        // Check if the actor exists
        if (actor != null) {
            ArrayList<Actor> actors = new ArrayList<>();
            actors.add(actor);
            printActors(actors);
        } else {
            // If the actor does not exist, display an error message
            System.out.printf("Actor with IMDB Link %s does not exist!%n", imdbLink);
        }
    }

    /**
     * Displays statistics about the actors and co-star pairs stored in the system.
     * This method prints the total number of actors, co-star pairs, and actors not in a co-star pair.
     * It retrieves these statistics from the {@link Data} class and displays them.
     */
    private static void menuActorStatistics() {
        System.out.printf("There are %d actors.%n", data.getActorCount());
        System.out.printf("There are %d co-star pairs.%n", data.getPairCount());
        System.out.printf("There are %d actors not in a co-star pair.%n", data.getLoneActorsCount());
    }

    /**
     * Displays statistics about the distribution of actors across different countries.
     * This method counts the number of actors from each country and displays the top countries with the most actors.
     * It iterates through all actors in the data, counts the actors per country, sorts the countries by actor count in descending order,
     * and then displays the top countries with their respective actor counts.
     */
    private static void menuCountryMaxStatistics() {
        // Iterate through each actor in data.java
        ArrayList<Actor> ActorsInCountryDescendingList = data.getActorsInCountryDescendingList();

        // Prints the countries in order of most to least actors
        for (Map.Entry<String, Integer> entry : data.getActorsPerCountryDescending().entrySet()) {
        System.out.println(entry.getKey() + ": " + entry.getValue() + " actors");
        }
    }

    /**
     * Displays statistics about the distribution of actors across different countries.
     * This method counts the number of actors from each country and displays the top countries with the most actors.
     * It iterates through all actors in the data, counts the actors per country, sorts the countries by actor count in descending order,
     * and then displays the top countries with their respective actor counts.
     */
    private static void menuCountryMinStatistics() {
        // Iterate through each actor in data.java
        ArrayList<Actor> ActorsInCountryAscendingList = data.getActorsInCountryAscendingList();

        // Prints the countries in order of most to least actors
        for (Map.Entry<String, Integer> entry : data.getActorsPerCountryAscending().entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " actors");
        }
    }

    /**
     * Displays information about actors who are not part of a co-star pair.
     * The information includes the name, age, height, awards, IMDb link, and retired status of each actor.
     * This method retrieves the relevant data from the {@link Data} class.
     * The actor information is displayed in a formatted manner.
     */
    private static void menuActorsNotInPair() {
        // Display header for actor information
        System.out.println(ACTOR_HEADER);
        // Display separator line for formatting
        System.out.println(ACTOR_SEPARATOR);
        // Retrieve actors who are not in a co-star pair from the data
        for (Actor actor : data.getActorsNotInPair()) {
            // Convert boolean value for retired status to "T" or "F"
            String retired = "F";
            if (actor.isRetired()) {
                retired = "T";
            }
            // Display actor information using formatted string
            System.out.printf(ACTOR_FORMAT, actor.getName(), actor.getAge(), actor.getHeight(), actor.getCountry(), actor.getNumberOfAwards(), actor.getImdbLink(), actor.isRetired());
        }
    }
    /**
     * Calculates the average age of all actors in the database.
     * This method retrieves the age of each actor from the {@link Data} class and calculates the average.
     * It then displays the average age.
     */
    private static void menuAverageAge() {
        // Get all actors from the data
        ArrayList<Actor> actors = data.getAllActors();
        int totalAge = 0;
        // Calculate total age
        for (Actor actor : actors) {
            totalAge += actor.getAge();
        }
        // Calculate average age
        double averageAge = (double) totalAge / actors.size();
        // Display the average age
        System.out.printf("The average age of actors in the database is %.2f%n", averageAge);
    }

    /**
     * Identifies the tallest actor in the database.
     * This method retrieves the height of each actor from the {@link Data} class and identifies the tallest actor.
     * It then displays the name and height of the tallest actor.
     */
    private static void menuTallestActor() {
        // Get all actors from the data
        ArrayList<Actor> actors = data.getAllActors();
        int maxHeight = Integer.MIN_VALUE;
        String tallestActorName = "";
        // Iterate through all actors to find the tallest actor
        for (Actor actor : actors) {
            int height = actor.getHeight();
            if (height > maxHeight) {
                maxHeight = height;
                tallestActorName = actor.getName();
            }
        }
        // Display the tallest actor
        System.out.printf("The tallest actor in the database is %s with a height of %d cm%n", tallestActorName, maxHeight);
    }
}