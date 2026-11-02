package vn.edu.patterns.extra;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import ca.mcgill.solitaire.model.CompositeMove;
import ca.mcgill.solitaire.model.Move;
import vn.edu.patterns.extra.decorator.solitaire.LoggingMoveDecorator;

class SolitairePatternExtraTest {
    private Move recorded(char id, StringBuilder log) {
        return new Move() {
            @Override public void perform() { log.append(id); }
            @Override public void undo() { log.append(Character.toLowerCase(id)); }
        };
    }

    @Test void compositePerformsForwardUndoesBackward() {
        StringBuilder history = new StringBuilder();
        Move c = new CompositeMove(recorded('A', history), recorded('B', history));
        c.perform(); c.undo();
        assertEquals("ABba", history.toString());
    }

    @Test void nestedCompositeWorks() {
        StringBuilder history = new StringBuilder();
        Move nested = new CompositeMove(recorded('A', history),
            new CompositeMove(recorded('B', history), recorded('C', history)));
        nested.perform(); nested.undo();
        assertEquals("ABCcba", history.toString());
    }

    @Test void emptyCompositeDoesNotThrow() {
        Move empty = new CompositeMove();
        assertDoesNotThrow(() -> { empty.perform(); empty.undo(); });
    }

    @Test void decoratorKeepsSameResultsAndAddsLogs() {
        StringBuilder history = new StringBuilder();
        List<String> messages = new ArrayList<>();
        Move base = new CompositeMove(recorded('A', history), recorded('B', history));
        Move decorated = new LoggingMoveDecorator(base, messages::add);
        decorated.perform(); decorated.undo();
        assertEquals("ABba", history.toString());
        assertEquals(4, messages.size());
        assertTrue(messages.get(0).contains("Bat dau"));
    }

    @Test void decoratorDelegatesIsNullAndCanBeNested() {
        Move nullMove = new Move() {
            @Override public void perform() {}
            @Override public void undo() {}
            @Override public boolean isNull() { return true; }
        };
        List<String> logs = new ArrayList<>();
        Move twice = new LoggingMoveDecorator(new LoggingMoveDecorator(nullMove, logs::add), logs::add);
        assertTrue(twice.isNull());
        twice.perform();
        assertEquals(4, logs.size());
    }
}
