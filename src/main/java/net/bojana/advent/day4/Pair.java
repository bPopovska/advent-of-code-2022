package net.bojana.advent.day4;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Pair {

    Interval first;
    Interval second;

    Pair(String input) {
        Pattern pattern = Pattern.compile("([0-9]*)-([0-9]*),([0-9]*)-([0-9]*)");

        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            first = new Interval(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
            second = new Interval(Integer.parseInt(matcher.group(3)), Integer.parseInt(matcher.group(4)));
        }

    }

    boolean fullOverlap() {
        if ((first.start <= second.start && first.end >= second.end) || (second.start <= first.start && second.end >= first.end)) {
            return true;
        }
        return false;
    }

    boolean overlap() {
        if (first.start >= second.start) {
            Interval tmp = first;
            first = second;
            second = tmp;
        }

        if (first.end >= second.start) {
            return true;
        }
        return false;
    }
}
