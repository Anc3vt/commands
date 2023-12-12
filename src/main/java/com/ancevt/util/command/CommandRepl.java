/**
 * Copyright (C) 2022 the original author or authors.
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

@Deprecated
public class CommandRepl<T> {

    private final CommandSet<T> commandSet;
    private boolean running;

    public CommandRepl(CommandSet<T> commandSet) {
        this.commandSet = commandSet;
    }

    public CommandSet<T> getCommandSet() {
        return commandSet;
    }

    public boolean isRunning() {
        return running;
    }

    public void start(InputStream inputStream, OutputStream outputStream) throws IOException {
        running = true;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while (running && (line = bufferedReader.readLine()) != null) {
            try {
                String resultString = String.valueOf(commandSet.execute(line));
                outputStream.write(resultString.getBytes(StandardCharsets.UTF_8));
                outputStream.write("\n".getBytes(StandardCharsets.UTF_8));
            } catch (NoSuchCommandException e) {
                outputStream.write(e.getMessage().getBytes(StandardCharsets.UTF_8));
                outputStream.write("\n".getBytes(StandardCharsets.UTF_8));
            }
        }
    }

    public void stop() {
        running = false;
    }

    public static void main(String[] args) throws IOException {
        CommandSet<Integer> commandSet = CommandSet.create(int.class);
        commandSet.registerCommand("/test", a -> a.get(int.class, 1, 0));
        commandSet.registerCommand("/exit", a -> {System.exit(0); return 0;});
        new CommandRepl<>(commandSet).start(System.in, System.err);
    }
}
