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
