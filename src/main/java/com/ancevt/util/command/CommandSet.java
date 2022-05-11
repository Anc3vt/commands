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
import com.ancevt.util.texttable.TextTable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.StringTokenizer;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.lang.String.format;

public class CommandSet<T> extends HashSet<Command<? extends T>> {

    private static final String DELIMITER = " ";

    private final List<Command<? extends T>> commandList = new ArrayList<>();

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

    public CommandSet<T> registerCommand(String commandLine, Function<Args, T> function) {
        add(new Command<>(commandLine, function));
        return this;
    }

    public CommandSet<T> registerCommand(String commandLine, String description, Function<Args, T> function) {
        add(new Command<>(commandLine, description, function));
        return this;
    }

    public List<Command<? extends T>> toList() {
        return new ArrayList<>(commandList);
    }

    @Override
    public void clear() {
        commandList.clear();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        commandList.retainAll(c);
        return super.retainAll(c);
    }

    @Override
    public boolean add(Command<? extends T> command) {
        commandList.add(command);
        return super.add(command);
    }

    @Override
    public boolean addAll(@NotNull Collection<? extends Command<? extends T>> collection) {
        commandList.addAll(collection);
        return super.addAll(collection);
    }

    @Override
    public boolean remove(Object o) {
        commandList.remove(o);
        return super.remove(o);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        commandList.removeAll(c);
        return super.removeAll(c);
    }

    @Override
    public boolean removeIf(Predicate<? super Command<? extends T>> filter) {
        commandList.removeIf(filter);
        return super.removeIf(filter);
    }

    public String getFormattedCommandList() {
        TextTable textTable = new TextTable(false, "Command", "Description");
        commandList.forEach(command -> textTable.addRow(command.getCommandWord(), command.getDescription()));
        return textTable.render();
    }

    public static <T> @NotNull CommandSet<T> create(Class<T> type) {
        return new CommandSet<>();
    }
}

























