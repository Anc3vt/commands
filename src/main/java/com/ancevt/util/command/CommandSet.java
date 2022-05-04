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

import java.util.HashSet;
import java.util.StringTokenizer;

import static java.lang.String.format;

public class CommandSet<T> extends HashSet<Command<? extends T>> {

    private static final String DELIMITER = " ";

    public T execute(String commandLine) throws CommandException {
        StringTokenizer stringTokenizer = new StringTokenizer(commandLine, DELIMITER);
        String commandWord = stringTokenizer.nextToken();

        for (Command<? extends T> command : this) {
            if (commandWord.equals(command.getCommandWord())) {
                return command.execute(commandLine);
            }
        }

        throw new CommandException(format("No such command %s", commandWord), this);
    }
}
