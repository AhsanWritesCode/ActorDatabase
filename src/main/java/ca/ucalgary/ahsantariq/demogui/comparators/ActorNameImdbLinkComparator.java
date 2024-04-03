package ca.ucalgary.ahsantariq.demogui.comparators;

import ca.ucalgary.ahsantariq.demogui.objects.Actor;

import java.util.Comparator;

public class ActorNameImdbLinkComparator implements Comparator<Actor>{

    @Override
    public int compare(Actor o1, Actor o2) {
        int comp = o1.getName().compareTo(o2.getName());
        if (comp != 0) {
            return comp;
        }
        return o1.compareTo(o2);
    }
}
