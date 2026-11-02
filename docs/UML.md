# Sơ đồ UML – giải thích hai mẫu trong hai project

Đây là sơ đồ rút gọn phục vụ phân tích các lớp đã được sao chép nguyên bản.

## Solitaire – Composite (mã gốc)

```mermaid
classDiagram
  class Move { <<interface>> +perform() +undo() +isNull() }
  class CompositeMove { -List~Move~ aMoves +perform() +undo() }
  class SingleMove { +perform() +undo() }
  Move <|.. CompositeMove
  Move <|.. SingleMove
  CompositeMove o-- "0..*" Move : aMoves
```

`SingleMove` trong hình là *vai trò leaf minh họa*, không phải tên lớp sao chép vào project này.

## JetUML – Composite (mã gốc)

```mermaid
classDiagram
  class DiagramOperation { <<interface>> +execute() +undo() }
  class SimpleOperation { -Runnable aOperation -Runnable aReverse }
  class CompoundOperation { -List~DiagramOperation~ aOperations +add(op) +execute() +undo() }
  DiagramOperation <|.. SimpleOperation
  DiagramOperation <|.. CompoundOperation
  CompoundOperation o-- "0..*" DiagramOperation
```

## Solitaire – Decorator (đề xuất, không có trong mã gốc)

```mermaid
classDiagram
  class Move { <<interface>> +perform() +undo() +isNull() }
  class LoggingMoveDecorator { -Move wrapped +perform() +undo() +isNull() }
  Move <|.. LoggingMoveDecorator
  LoggingMoveDecorator o-- "1" Move : wrapped
```

## JetUML – Decorator (đề xuất, không có trong mã gốc)

```mermaid
classDiagram
  class DiagramOperation { <<interface>> +execute() +undo() }
  class LoggingDiagramOperation { -DiagramOperation wrapped +execute() +undo() }
  DiagramOperation <|.. LoggingDiagramOperation
  LoggingDiagramOperation o-- "1" DiagramOperation : wrapped
```
