package com.ancevt.command;

import com.ancevt.util.args.Args;

import java.util.Objects;
import java.util.function.Consumer;

public class Command {

    private final String[] words;
    private final Consumer<Args> function;
    private final String help;

    public Command(String[] words, Consumer<Args> function, String help) {
        this.words = words;
        this.function = function;
        this.help = help;
    }

    public Command(String word, Consumer<Args> function, String help) {
        this(new String[]{word}, function, help);
    }

    public Command(String[] words, Consumer<Args> function) {
        this(words, function, null);
    }

    public Command(String word, Consumer<Args> function) {
        this(word, function, null);
    }

    public String getHelp() {
        return help;
    }

    public boolean checkWord(String word) {
        for (String s : words) {
            if (Objects.equals(s, word)) return true;
        }
        return false;
    }

    public void exec(Args args) {
        getFunction().accept(args);
    }

    public void exec(String args) {
        getFunction().accept(Args.of(args));
    }

    public Consumer<Args> getFunction() {
        return function;
    }
}
