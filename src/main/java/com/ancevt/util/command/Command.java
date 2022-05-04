/*
 *   Ancevt Command
 *   Copyright (C) 2020 Ancevt (me@ancevt.com)
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.ancevt.util.command;

import com.ancevt.util.args.Args;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public class Command<T> {

    private final String commandWord;
    private final Function<Args, T> function;
    private T lastResult;

    public Command(String commandWord, Function<Args, T> function) {
        this.commandWord = commandWord;
        this.function = function;
    }

    public T execute(String commandLine) {
        lastResult = function.apply(Args.of(commandLine));
        return lastResult;
    }

    public String getCommandWord() {
        return commandWord;
    }

    public T getLastResult() {
        return lastResult;
    }

    @Contract(value = "_, _ -> new", pure = true)
    public static <T> @NotNull Command<T> of(String commandWord, Function<Args, T> function) {
        return new Command<>(commandWord, function);
    }

    @Override
    public String toString() {
        return "Command{" +
                "commandWord='" + commandWord + '\'' +
                ", function=" + function +
                '}';
    }
}
