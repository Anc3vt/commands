/*
 *   Ancevt Commands
 *   Copyright (C) 2022 Ancevt (me@ancevt.com)
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

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
        new CommandRepl<>(commandSet).start(System.in, System.err);
    }
}
