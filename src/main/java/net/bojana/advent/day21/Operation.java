package net.bojana.advent.day21;

public enum Operation {
    ADD("+"), SUB("-"), MUL("*"), DIV("/");

    String val;
    Operation(String val) {
        this.val = val;
    }

    public static Operation fromVal(String val) {
        switch (val) {
            case "+":
                return ADD;
            case "-":
                return SUB;
            case "*":
                return MUL;
            case "/":
                return DIV;
        }
        return null;
    }
}
