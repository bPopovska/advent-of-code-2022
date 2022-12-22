package net.bojana.advent.day21;

import net.bojana.advent.MultiLineInput;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day21 extends MultiLineInput {

    Map<String, Node> nodes = new HashMap<>();

    public static void main(String[] args) throws IOException {
        Day21 day21 = new Day21();

        List<String> input = day21.getInput(21, false);

        day21.part1(input);
        day21.part2(input);
    }

    @Override
    public void part1(List<String> input) {
        initTree(input);
        BigInteger result = dfsEvaluate(nodes.get("root"));
        System.out.println(result);
    }

    @Override
    public void part2(List<String> input) {
        initTree(input);
        Node root = nodes.get("root");
        Node left = nodes.get(root.left);
        Node right = nodes.get(root.right);

        boolean isInLeft = dfsFindBName(left, "humn");
        boolean isInRight = dfsFindBName(right, "humn");

        Node searchIn = null;
        BigInteger targetValue = BigInteger.ZERO;
        BigInteger otherValue = BigInteger.ZERO;

        if (isInLeft) {
            targetValue = dfsEvaluate(right);
            otherValue = dfsEvaluate(left);
            searchIn = left;
        } else if (isInRight) {
            targetValue = dfsEvaluate(left);
            otherValue = dfsEvaluate(right);
            searchIn = right;
        }

        print(searchIn);
        System.out.println("=" + targetValue);

        BigInteger start = BigInteger.valueOf(Long.MIN_VALUE);
        BigInteger end = new BigInteger(String.valueOf(Long.MAX_VALUE));
        while (start.compareTo(end) < 0) {
            BigInteger mid = start.add(end).divide(new BigInteger("2"));

            BigInteger val = dfsEvaluateWithSubstitute(searchIn, "humn", mid);

            System.out.println(mid.longValue() + " " + val + "  " + targetValue);
            if (val.equals(targetValue)) {
                System.out.println(mid);
                break;
            } else if (val.compareTo(targetValue) > 0) {
                start = mid.add(BigInteger.ONE);
            } else {
                end = mid.subtract(BigInteger.ONE);
            }
        }


    }

    private void initTree(List<String> input) {
        for (String line : input) {
            Node n = new Node(line);
            nodes.put(n.getName(), n);
        }
    }

    private void print(Node node) {
        if (node.left == null && node.right == null) {
            if (node.name.equals("humn")) {
                System.out.print("x");
            } else {
                System.out.print(node.number);
            }
        } else {
            System.out.print("(");
            print(nodes.get(node.left));
            System.out.print(node.operation.val);
            print(nodes.get(node.right));
            System.out.print(")");
        }
    }

    private BigInteger dfsEvaluate(Node node) {
        if (node.left == null && node.right == null) {
            return BigInteger.valueOf(node.number);
        }
        switch (node.operation) {
            case MUL:
                return dfsEvaluate(nodes.get(node.left)).multiply(dfsEvaluate(nodes.get(node.right)));
            case ADD:
                return dfsEvaluate(nodes.get(node.left)).add(dfsEvaluate(nodes.get(node.right)));
            case SUB:
                return dfsEvaluate(nodes.get(node.left)).subtract(dfsEvaluate(nodes.get(node.right)));
            case DIV:
                return dfsEvaluate(nodes.get(node.left)).divide(dfsEvaluate(nodes.get(node.right)));
            default:
                return BigInteger.ZERO;
        }
    }

    private BigInteger dfsEvaluateWithSubstitute(Node node, String name, BigInteger value) {
        if (node.left == null && node.right == null) {
            if (name.equals(node.name)) {
                return value;
            } else {
                return BigInteger.valueOf(node.number);
            }
        }
        switch (node.operation) {
            case MUL:
                return dfsEvaluateWithSubstitute(nodes.get(node.left), name, value).multiply(dfsEvaluateWithSubstitute(nodes.get(node.right), name, value));
            case ADD:
                return dfsEvaluateWithSubstitute(nodes.get(node.left), name, value).add(dfsEvaluateWithSubstitute(nodes.get(node.right), name, value));
            case SUB:
                return dfsEvaluateWithSubstitute(nodes.get(node.left), name, value).subtract(dfsEvaluateWithSubstitute(nodes.get(node.right), name, value));
            case DIV:
                return dfsEvaluateWithSubstitute(nodes.get(node.left), name, value).divide(dfsEvaluateWithSubstitute(nodes.get(node.right), name, value));
            default:
                return BigInteger.ZERO;
        }
    }

    private boolean dfsFindBName(Node node, String name) {
        if (node.left == null && node.right == null) {
            if (node.name.equals(name)) {
                return true;
            }
        } else {
            boolean left = dfsFindBName(nodes.get(node.left), name);
            boolean right = dfsFindBName(nodes.get(node.right), name);
            return left || right;
        }
        return false;
    }
}
