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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class Repl {

    private final InputStream in;
    private final OutputStream out;
    private OutputStream err;

    private boolean running;

    private List<Command> commands;

    public Repl() {
        this(System.in, System.out, System.err);
    }

    public Repl(InputStream in, OutputStream out, OutputStream err) {
        this.in = in;
        this.out = out;
        this.err = err;
        commands = new ArrayList<>();
    }

    public Repl addCommand(Command command) {
        commands.add(command);
        return this;
    }

    public Repl addCommand(String[] words, Consumer<Args> function, String help) {
        return addCommand(new Command(words, function, help));
    }

    public Repl addCommand(String word, Consumer<Args> function, String help) {
        return addCommand(new Command(word, function, help));
    }

    public Repl addCommand(String[] words, Consumer<Args> function) {
        return addCommand(new Command(words, function));
    }

    public Repl addCommand(String word, Consumer<Args> function) {
        return addCommand(new Command(word, function));
    }

    public void start() {
        running = true;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        String line;
        while (true) {
            try {
                if (!(running && (line = bufferedReader.readLine()) != null)) break;
            } catch (IOException e) {
                throw new ReplException(e);
            }

            if (line.trim().isEmpty()) continue;

            Args args = Args.of(line);
            String word = args.get(String.class, 0);

            Optional<Command> command = commands.stream()
                .filter(c -> c.checkWord(word))
                .findAny();

            if (command.isPresent()) {
                try {
                    command.get().exec(args);
                } catch (Exception e) {
                    e.printStackTrace(new PrintStream(err));
                }
            } else {
                try {
                    err.write(String.format("No such command '%s'", word).getBytes(StandardCharsets.UTF_8));
                    err.write(System.lineSeparator().getBytes(StandardCharsets.UTF_8));
                } catch (IOException e) {
                    throw new ReplException(e);
                }
            }
        }
    }

    public void startAsync() {
        new Thread(this::start, "repl-async-thread-" + this.hashCode()).start();
    }

    public static void main(String[] args) {
        new Repl()
            .addCommand(new String[]{"exit", "e"}, a -> System.exit(0))
            .addCommand("test", a -> System.out.println(a.getSource()))
            .addCommand("add", a -> {

                try {
                    a.skip();
                    int x = a.next(int.class);
                    int y = a.next(int.class);
                    System.out.println(x + y);
                } catch (Exception e) {
                    System.out.println("Exception");
                    e.printStackTrace();
                }

            })
            .startAsync();
    }


}
