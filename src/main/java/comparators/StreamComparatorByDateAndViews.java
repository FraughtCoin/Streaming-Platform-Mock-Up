package comparators;

import models.Stream;

import java.util.Comparator;

public class StreamComparatorByDateAndViews implements Comparator<Stream> {

    @Override
    public int compare(Stream o1, Stream o2) {
        if (o1.getDateAdded() > o2.getDateAdded()) {
            return -1;
        }
        if (o1.getDateAdded() < o2.getDateAdded()) {
            return 1;
        }
        return Long.compare(o2.getNoOfStreams(), o1.getNoOfStreams());

    }
}
