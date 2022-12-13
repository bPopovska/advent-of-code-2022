package net.bojana.advent.day13;

import java.util.LinkedList;
import java.util.List;

public class Element implements Comparable<Element>{
    Integer element = null;
    List<Element> listOfElements = null;
    boolean isOpenBracket;

    Element() {
        isOpenBracket = true;
    }

    Element (Integer element) {
        this.element = element;
    }

    Element (List<Element> listOfElements) {
        this.listOfElements = listOfElements;
    }

    boolean isBracket () {
        return isOpenBracket;
    }

    boolean isNumber() {
        return element != null;
    }

    boolean isList() {
        return listOfElements != null;
    }

    @Override
    public int compareTo(Element element) {
        // number vs number
        if (this.isNumber() && element.isNumber()) {
            return this.element - element.element;
        } else if (this.isList() && element.isList()) {
            if (this.listOfElements.size() == 0 && element.listOfElements.size() != 0) {
                return -1;
            }
            for (int i = 0; i < this.listOfElements.size(); i++) {
                if (i >= element.listOfElements.size()) {
                    return 1;
                }
                int elementCompare = this.listOfElements.get(i).compareTo(element.listOfElements.get(i));
                if (elementCompare != 0) {
                    return elementCompare;
                }
            }
        } else if (this.isList() &&  element.isNumber()) {
            List<Element> listOfElement = new LinkedList<>();
            listOfElement.add(element);
            return this.compareTo(new Element(listOfElement));
        } else {
            List<Element> listOfElement = new LinkedList<>();
            listOfElement.add(this);
            return new Element(listOfElement).compareTo(element);
        }
        return 0;
    }
}
