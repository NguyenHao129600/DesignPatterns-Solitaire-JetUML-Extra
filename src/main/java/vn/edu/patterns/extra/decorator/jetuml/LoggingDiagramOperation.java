package vn.edu.patterns.extra.decorator.jetuml;

import java.util.Objects;
import java.util.function.Consumer;
import org.jetuml.diagram.builder.DiagramOperation;

/**
 * DE XUAT MO RONG - KHONG THUOC MA NGUON JETUML GOC.
 * Decorator bao 1 DiagramOperation va uy quyen execute/undo.
 */
public final class LoggingDiagramOperation implements DiagramOperation {
    private final DiagramOperation wrapped;
    private final Consumer<String> logger;

    public LoggingDiagramOperation(DiagramOperation wrapped) {
        this(wrapped, System.out::println);
    }

    public LoggingDiagramOperation(DiagramOperation wrapped, Consumer<String> logger) {
        this.wrapped = Objects.requireNonNull(wrapped, "wrapped");
        this.logger = Objects.requireNonNull(logger, "logger");
    }

    @Override
    public void execute() {
        logger.accept("[JetUML] Bat dau execute");
        wrapped.execute();
        logger.accept("[JetUML] Hoan thanh execute");
    }

    @Override
    public void undo() {
        logger.accept("[JetUML] Bat dau undo");
        wrapped.undo();
        logger.accept("[JetUML] Hoan thanh undo");
    }
}
