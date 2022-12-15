package net.bojana.advent.day15;

public class Interval implements Comparable<Interval> {
    long start;
    long end;

    Interval(long start, long end) {
        this.start = start;
        this.end = end;
    }

    public long getLength() {
        return this.end - this.start;
    }

    public void normalize(int start, int end) {
        this.start = Math.max(start, this.start);
        this.end = Math.min(end, this.end);
    }

    public boolean contains(Long l) {
        return this.start <= l && this.end >= l;
    }

    public boolean overlap(Interval i) {
        return i.start >= this.start && i.start <= this.end;
    }

    public Interval merge(Interval i) {
        return new Interval(Math.min(i.start, this.start), Math.max(i.end, this.end));
    }

    @Override
    public int compareTo(Interval o) {
        return (int)(o.start - this.start);
    }

    @Override
    public String toString() {
        return "Interval{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }
}
