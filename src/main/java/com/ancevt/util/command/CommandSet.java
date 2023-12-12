/**
 * Copyright (C) 2023 the original author or authors.
 * See the notice.md file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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

@Deprecated
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

























