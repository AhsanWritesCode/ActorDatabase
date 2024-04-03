package ca.ucalgary.ahsantariq.demogui.objects;


public class Actor implements Comparable<Actor>{
    // Declare private instance variables
    private String name, country, imdbLink;// Actor's name, country, and IMDb link
    private int age, height, numberOfAwards;// Actor's age, height, and number of awards
    private boolean retired; // Indicates if the actor is retired

    public Actor(String name, int age, int height, String country, int numberOfAwards, String imdbLink){
        this.name = name;
        this.age = age;
        this.height = height;
        this.country = country;
        this.numberOfAwards = numberOfAwards;
        this.retired = false;
        this.imdbLink = imdbLink;
    }
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int getHeight() {
        return height;
    }

    public String getCountry() {
        return country;
    }

    public int getNumberOfAwards() {
        return numberOfAwards;
    }

    public String getImdbLink() {
        return imdbLink;
    }

    public boolean isRetired() {
        return retired;
    }

    @Override
    public String toString() {
        return "core.objects.Actor{" +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", height=" + height +
                ", country=" + country +
                ", numberOfAwards=" + numberOfAwards +
                ", imdbLink=" + imdbLink +
                ", retired=" + retired +
                '}';
    }

    public void retire() {
        this.retired = true;
    }

    @Override
    public int compareTo(Actor o) {
        return String.CASE_INSENSITIVE_ORDER.compare(imdbLink, o.getImdbLink());
    }
}
