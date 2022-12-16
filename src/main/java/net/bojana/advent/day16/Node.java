package net.bojana.advent.day16;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Node {
    final static Pattern patternSingular = Pattern.compile("Valve (.*) has flow rate=(.*); tunnel leads to valve (.*)");
    final static Pattern patternPlural = Pattern.compile("Valve (.*) has flow rate=(.*); tunnels lead to valves (.*)");

    public String name;
    public int index;
    public int valveFlow;
    public int valveIndex;
    public List<String> connections;

    public Node(String node, int count, int valveCount) {

        this.index = count;
        this.connections = new ArrayList<>();

        Matcher matcherSingular = patternSingular.matcher(node);
        Matcher matcherPlural = patternPlural.matcher(node);

        if (matcherSingular.find()) {
            this.name = matcherSingular.group(1);
            this.valveFlow = Integer.parseInt(matcherSingular.group(2));
            this.connections.add(matcherSingular.group(3).trim());
        } else if (matcherPlural.find()) {
            this.name = matcherPlural.group(1);
            this.valveFlow = Integer.parseInt(matcherPlural.group(2));
            String[] allConnections = matcherPlural.group(3).split(",");
            for (String connection : allConnections) {
                this.connections.add(connection.trim());
            }
        }

        if (this.valveFlow != 0) {
            this.valveIndex = valveCount;
        } else {
            this.valveIndex = -1;
        }
    }

}
