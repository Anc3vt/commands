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
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

@Deprecated
public class Command<T> {

    private final String commandWord;
    private final String description;
    private final Function<Args, T> function;
    private T lastResult;

    public Command(String commandWord, Function<Args, T> function) {
        this.commandWord = commandWord;
        this.description = "";
        this.function = function;
    }

    public Command(String commandWord, String description, Function<Args, T> function) {
        this.commandWord = commandWord;
        this.description = description;
        this.function = function;
    }

    public T execute(String commandLine) {
        Args args = Args.of(commandLine);
        args.skip();
        lastResult = function.apply(args);
        return lastResult;
    }

    public String getDescription() {
        return description;
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
                ", description='" + description + '\'' +
                ", function=" + function +
                '}';
    }
}
