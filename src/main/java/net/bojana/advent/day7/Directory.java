package net.bojana.advent.day7;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Directory {
    String name;
    boolean isFile;
    BigInteger sizeInBytes = BigInteger.ZERO;

    Directory parent;
    Map<String, Directory> children = new HashMap<>();

    Directory (String s) {
        if (s.startsWith("dir")) {
            // directory
            isFile = false;
            name = s.split(" ")[1];
        } else {
            String[] parts = s.split(" ");
            sizeInBytes = new BigInteger(parts[0]);
            name = parts[1];
            isFile = true;
        }
    }

    void setParent(Directory parent) {
        this.parent = parent;
    }

    void addChild(Directory child) {
        children.put(child.name, child);
    }

    void addSize(BigInteger bigInteger) {
        sizeInBytes = sizeInBytes.add(bigInteger);
    }
}
