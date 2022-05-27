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

import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.junit.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class CommandTest {

    @Test
    void testCommand() throws NoSuchCommandException {
        CommandSet<Boolean> commandSet = new CommandSet<>();
        commandSet.add(new Command<>("/test", args -> true));
        boolean result = commandSet.execute("/test");
        assertTrue(result);
    }

    @Test
    void testCommandArgs() throws NoSuchCommandException {
        CommandSet<Integer> commandSet = new CommandSet<>();
        commandSet.add(new Command<>("/add", args -> {
            return args.next(int.class) + args.next(int.class);
        }));
        int result = commandSet.execute("/add 1 2");
        assertThat(result, is(3));
    }
}
