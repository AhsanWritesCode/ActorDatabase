package ca.ucalgary.ahsantariq.demogui.comparators;

import ca.ucalgary.ahsantariq.demogui.objects.Actor;

import java.util.Comparator;

public class ActorAgeComparator implements Comparator<Actor> {

    @Override
    public int compare(Actor o1, Actor o2) {
        return Integer.compare(o1.getAge(), o2.getAge());
    }
}
