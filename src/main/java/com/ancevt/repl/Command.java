package com.ancevt.repl;

import com.ancevt.util.args.Args;

import java.util.function.Function;

public class Command<T> {

    private String commandWord;
    private Function<Args, T> function;

    public Command(String commandWord, Function<Args, T> function) {
        this.commandWord = commandWord;
        this.function = function;
    }

    public T execute(String commandLine) {
        return function.apply(Args.of(commandLine));
    }
}
