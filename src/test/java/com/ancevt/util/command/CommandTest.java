package com.ancevt.util.command;

import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.junit.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class CommandTest {

    @Test
    void testCommand() throws CommandException {
        CommandSet<Boolean> commandSet = new CommandSet<>();
        commandSet.add(new Command<>("/test", args -> true));
        boolean result = commandSet.execute("/test");
        assertTrue(result);
    }

    @Test
    void testCommandArgs() throws CommandException {
        CommandSet<Integer> commandSet = new CommandSet<>();
        commandSet.add(new Command<>("/add", args -> {
            args.next();
            return args.next(int.class) + args.next(int.class);
        }));
        int result = commandSet.execute("/add 1 2");
        assertThat(result, is(3));
    }
}
