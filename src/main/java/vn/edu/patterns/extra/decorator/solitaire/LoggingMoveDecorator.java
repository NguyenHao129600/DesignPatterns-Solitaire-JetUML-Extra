package vn.edu.patterns.extra.decorator.solitaire;

import java.util.Objects;
import java.util.function.Consumer;
import ca.mcgill.solitaire.model.Move;

/**
 * DE XUAT MO RONG - KHONG THUOC MA NGUON SOLITAIRE GOC.
 * Decorator cung giao dien Move, giu mot Move va uy quyen moi loi goi.
 * Logger co the thay the, de kiem thu khong can thay doi System.out.
 */
public final class LoggingMoveDecorator implements Move {
    private final Move wrapped;
    private final Consumer<String> logger;

    public LoggingMoveDecorator(Move wrapped) {
        this(wrapped, System.out::println);
    }

    public LoggingMoveDecorator(Move wrapped, Consumer<String> logger) {
        this.wrapped = Objects.requireNonNull(wrapped, "wrapped");
        this.logger = Objects.requireNonNull(logger, "logger");
    }

    @Override
    public void perform() {
        logger.accept("[Solitaire] Bat dau perform");
        wrapped.perform();
        logger.accept("[Solitaire] Hoan thanh perform");
    }

    @Override
    public void undo() {
        logger.accept("[Solitaire] Bat dau undo");
        wrapped.undo();
        logger.accept("[Solitaire] Hoan thanh undo");
    }

    @Override
    public boolean isNull() {
        return wrapped.isNull();
    }
}
