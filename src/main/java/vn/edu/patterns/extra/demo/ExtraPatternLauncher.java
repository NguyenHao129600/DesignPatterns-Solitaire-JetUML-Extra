package vn.edu.patterns.extra.demo;

import ca.mcgill.solitaire.model.CompositeMove;
import ca.mcgill.solitaire.model.Move;
import org.jetuml.diagram.builder.CompoundOperation;
import org.jetuml.diagram.builder.DiagramOperation;
import org.jetuml.diagram.builder.SimpleOperation;
import vn.edu.patterns.extra.decorator.jetuml.LoggingDiagramOperation;
import vn.edu.patterns.extra.decorator.solitaire.LoggingMoveDecorator;

/**
 * Chay demo doc lap: su dung NHUNG LOP COMPOSITE GOC (sao chep nguyen ven)
 * va hai DECORATOR DE XUAT. Day KHONG PHAI giao dien game/app day du.
 */
public final class ExtraPatternLauncher {
    private ExtraPatternLauncher() {}

    public static void main(String[] args) {
        demoSolitaireComposite();
        demoSolitaireDecorator();
        demoJetUmlComposite();
        demoJetUmlDecorator();
        System.out.println("\n=== KET LUAN: 4 DEMO PASS ===");
    }

    private static Move move(char symbol, StringBuilder history) {
        return new Move() {
            @Override public void perform() { history.append(symbol); }
            @Override public void undo() { history.append(Character.toLowerCase(symbol)); }
        };
    }

    private static CompositeMove newSolitaireComposite(StringBuilder history) {
        return new CompositeMove(move('A', history), move('B', history));
    }

    private static CompoundOperation newJetUmlComposite(StringBuilder history) {
        CompoundOperation root = new CompoundOperation();
        root.add(new SimpleOperation(() -> history.append('A'), () -> history.append('a')));
        CompoundOperation inner = new CompoundOperation();
        inner.add(new SimpleOperation(() -> history.append('B'), () -> history.append('b')));
        inner.add(new SimpleOperation(() -> history.append('C'), () -> history.append('c')));
        root.add(inner);
        return root;
    }

    private static void verify(String actual, String expected) {
        if (!expected.equals(actual)) {
            throw new AssertionError("Expected " + expected + ", actual " + actual);
        }
    }

    private static void demoSolitaireComposite() {
        System.out.println("=== 1. SOLITAIRE: COMPOSITE GOC ===");
        StringBuilder history = new StringBuilder();
        CompositeMove root = newSolitaireComposite(history);
        root.perform(); root.undo();
        verify(history.toString(), "ABba");
        System.out.println("Thu tu perform/undo: " + history + " (PASS)\n");
    }

    private static void demoSolitaireDecorator() {
        System.out.println("=== 2. SOLITAIRE: DECORATOR DE XUAT ===");
        StringBuilder history = new StringBuilder();
        Move wrapped = new LoggingMoveDecorator(newSolitaireComposite(history));
        wrapped.perform(); wrapped.undo();
        verify(history.toString(), "ABba");
        System.out.println("Thu tu sau khi boc: " + history + " (PASS)\n");
    }

    private static void demoJetUmlComposite() {
        System.out.println("=== 3. JETUML: COMPOSITE GOC (LONG NHAU) ===");
        StringBuilder history = new StringBuilder();
        CompoundOperation root = newJetUmlComposite(history);
        root.execute(); root.undo();
        verify(history.toString(), "ABCcba");
        System.out.println("Thu tu execute/undo: " + history + " (PASS)\n");
    }

    private static void demoJetUmlDecorator() {
        System.out.println("=== 4. JETUML: DECORATOR DE XUAT ===");
        StringBuilder history = new StringBuilder();
        DiagramOperation wrapped = new LoggingDiagramOperation(newJetUmlComposite(history));
        wrapped.execute(); wrapped.undo();
        verify(history.toString(), "ABCcba");
        System.out.println("Thu tu sau khi boc: " + history + " (PASS)");
    }
}
