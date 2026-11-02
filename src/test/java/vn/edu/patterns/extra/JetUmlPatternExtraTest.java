package vn.edu.patterns.extra;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.jetuml.diagram.builder.CompoundOperation;
import org.jetuml.diagram.builder.DiagramOperation;
import org.jetuml.diagram.builder.SimpleOperation;
import vn.edu.patterns.extra.decorator.jetuml.LoggingDiagramOperation;

class JetUmlPatternExtraTest {
    private SimpleOperation recorded(char id, StringBuilder trace) {
        return new SimpleOperation(() -> trace.append(id),
            () -> trace.append(Character.toLowerCase(id)));
    }

    @Test void originalCompoundTracksEmptyState() {
        CompoundOperation root = new CompoundOperation();
        assertTrue(root.isEmpty());
        root.add(new SimpleOperation(() -> {}, () -> {}));
        assertFalse(root.isEmpty());
    }

    @Test void nestedCompoundExecutesAndUndoesInCorrectOrder() {
        StringBuilder history = new StringBuilder();
        CompoundOperation root = new CompoundOperation();
        CompoundOperation child = new CompoundOperation();
        root.add(recorded('A', history));
        child.add(recorded('B', history)); child.add(recorded('C', history));
        root.add(child);
        root.execute(); root.undo();
        assertEquals("ABCcba", history.toString());
    }

    @Test void emptyCompoundExecutesSafely() {
        CompoundOperation empty = new CompoundOperation();
        assertDoesNotThrow(() -> { empty.execute(); empty.undo(); });
    }

    @Test void decoratorKeepsOriginalBehaviorAndLogs() {
        StringBuilder history = new StringBuilder();
        List<String> logs = new ArrayList<>();
        CompoundOperation root = new CompoundOperation();
        root.add(recorded('A', history)); root.add(recorded('B', history));
        DiagramOperation decorated = new LoggingDiagramOperation(root, logs::add);
        decorated.execute(); decorated.undo();
        assertEquals("ABba", history.toString());
        assertEquals(4, logs.size());
        assertTrue(logs.get(1).contains("Hoan thanh execute"));
    }

    @Test void decoratorsCanBeStacked() {
        StringBuilder history = new StringBuilder();
        List<String> logs = new ArrayList<>();
        DiagramOperation root = recorded('A', history);
        DiagramOperation twice = new LoggingDiagramOperation(
                new LoggingDiagramOperation(root, logs::add), logs::add);
        twice.execute(); twice.undo();
        assertEquals("Aa", history.toString());
        assertEquals(8, logs.size());
    }
}
