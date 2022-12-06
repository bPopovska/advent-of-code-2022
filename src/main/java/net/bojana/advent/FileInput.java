package net.bojana.advent;

import java.util.Objects;

abstract public class FileInput {
    protected String getFileName(int day, boolean isTest) {
        return Objects.requireNonNull(MultiLineInput.class.getClassLoader()
                        .getResource(isTest ? "day" + day + "_test.txt" : "day" + day + ".txt"))
                .getFile();
    }

}
