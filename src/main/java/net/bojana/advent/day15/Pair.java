package net.bojana.advent.day15;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Pair {
    Point sensor;
    Point beacon;

    Pair(String line) {
        Pattern pattern = Pattern.compile("Sensor at x=(.*), y=(.*): closest beacon is at x=(.*), y=(.*)");
        Matcher matcher = pattern.matcher(line);

        if (matcher.find()) {
            this.sensor = new Point(Long.parseLong(matcher.group(1)), Long.parseLong(matcher.group(2)));
            this.beacon = new Point(Long.parseLong(matcher.group(3)), Long.parseLong(matcher.group(4)));
        }
    }

    public long getManhattan() {
        return Math.abs(sensor.x - beacon.x) + Math.abs(sensor.y - beacon.y);
    }
}
