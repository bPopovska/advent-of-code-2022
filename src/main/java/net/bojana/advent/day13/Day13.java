package net.bojana.advent.day13;

import net.bojana.advent.MultiLineInput;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class Day13 extends MultiLineInput {

    public static void main(String[] args) throws IOException {
        Day13 day13 = new Day13();
        List<String> input = day13.getInput(13, false);
        day13.part1(input);
        day13.part2(input);
    }

    @Override
    public void part1(List<String> input) {
        List<Element> allElements = parseInput(input);

        int pairIndex = 1;
        int result = 0;

        for (int i = 0; i < allElements.size(); i += 2) {
            Element e1 = allElements.get(i);
            Element e2 = allElements.get(i + 1);
            int res = e1.compareTo(e2);
            if (res <= 0) {
                result += pairIndex;
                System.out.println(pairIndex);
            }
            pairIndex++;
        }

        System.out.println(result);
    }

    @Override
    public void part2(List<String> input) {
        List<Element> allElements = parseInput(input);
        Element divider1 = new Element(List.of(new Element(2)));
        Element divider2 = new Element(List.of(new Element(6)));
        allElements.add(divider1);
        allElements.add(divider2);

        Collections.sort(allElements);

        System.out.println((allElements.indexOf(divider1) + 1) * (allElements.indexOf(divider2) + 1));
    }

    private List<Element> parseInput(List<String> input) {
        List<Element> allElements = new LinkedList<>();

        for (String line : input) {
            if (line.isBlank()) {
                continue;
            }
            Stack<Element> elementStack = new Stack<>();
            for (int i = 0; i < line.length(); i++) {
                Character c = line.charAt(i);
                if (c == '[') {
                    elementStack.add(new Element());
                } else if (c == ']') {
                    // pop
                    List<Element> list = new LinkedList<>();
                    while (true) {
                        Element element = elementStack.pop();
                        if (element.isBracket()) {
                            Collections.reverse(list);
                            Element listElement = new Element(list);
                            elementStack.add(listElement);
                            break;
                        }
                        list.add(element);
                    }
                } else if (Character.isDigit(c)){
                    Integer number = 0;
                    char next = c;
                    while (Character.isDigit(next)) {
                        number *= 10;
                        number += next - '0';
                        next = line.charAt(i + 1);
                        if (next == ',') {
                            i++;
                            break;
                        } else if (next == ']') {
                            break;
                        } else {
                            i++;
                        }
                    }
                    elementStack.add(new Element(number));
                }
            }
            Element element = elementStack.pop();
            allElements.add(element);
        }
        return allElements;
    }

}
