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

import java.util.HashSet;
import java.util.StringTokenizer;
import java.util.function.Function;

import static java.lang.String.format;

public class CommandSet<T> extends HashSet<Command<? extends T>> {

    private static final String DELIMITER = " ";

    public T execute(@NotNull String commandLine) throws NoSuchCommandException {
        StringTokenizer stringTokenizer = new StringTokenizer(commandLine, DELIMITER);
        String commandWord = stringTokenizer.nextToken();

        for (Command<? extends T> command : this) {
            if (commandWord.equals(command.getCommandWord())) {
                return command.execute(commandLine);
            }
        }

        throw new NoSuchCommandException(format("Unknown command: %s", commandWord), commandWord, commandLine, this);
    }

    public CommandSet registerCommand(String commandLine, Function<Args, T> function) {
        add(new Command<>(commandLine, function));
        return this;
    }

    public static <T> @NotNull CommandSet<T> create(Class<T> type) {
        return new CommandSet<>();
    }
}
