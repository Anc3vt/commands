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

public class NoSuchCommandException extends Exception {

    private String commandWord;
    private String commandLine;
    private CommandSet<?> commandSet;

    public NoSuchCommandException(String message, String commandWord, String commandLine, CommandSet<?> commandSet) {
        super(message);
        this.commandWord = commandWord;
        this.commandLine = commandLine;
        this.commandSet = commandSet;
    }

    public NoSuchCommandException(String message) {
        super(message);
    }

    public NoSuchCommandException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommandSet<?> getCommandSet() {
        return commandSet;
    }

    public String getCommandWord() {
        return commandWord;
    }

    public String getCommandLine() {
        return commandLine;
    }
}
