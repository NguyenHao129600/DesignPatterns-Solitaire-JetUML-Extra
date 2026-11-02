# Rà soát lại Decorator trong mã gốc JetUML

## Kết quả chính

- **Solitaire**: `CompositeMove` là Composite trực tiếp. Trong phiên bản gửi kèm chưa xác nhận Decorator chuẩn.
- **JetUML**: `NamedHandler` và `NewDiagramHandler` là hai ứng viên **Decorator-like về cấu trúc**; mã gốc được lưu trong `evidence/JetUML-goc/` để đối chiếu (KHÔNG nằm trong mã biên dịch của project demo, vì phụ thuộc JavaFX và các lớp khác của ứng dụng gốc).

## Dẫn chứng mã nguồn gốc

`org.jetuml.gui.NamedHandler` (dòng 29–63):
- `implements EventHandler<ActionEvent>`.
- `private final EventHandler<ActionEvent> aHandler`.
- `handle(ActionEvent e)` gọi `aHandler.handle(e)`.
- Có `aName`, `getName()`, `renamed(...)` để mang metadata tên hành động.

`org.jetuml.gui.NewDiagramHandler` (dòng 32–59):
- Cũng `implements EventHandler<ActionEvent>` và giữ một `EventHandler<ActionEvent>` bên trong.
- `handle(...)` ủy quyền; `getDiagramType()` cung cấp metadata loại sơ đồ.

`org.jetuml.gui.EditorFrame` (dòng 312–330):
- Tạo `NamedHandler` cho file gần đây, `NewDiagramHandler` cho loại sơ đồ.

### Mức độ kết luận
Hai wrapper này thỏa cơ chế bọc + cùng interface + ủy quyền và thêm dữ liệu, **không làm thay đổi cách `handle()` thực thi**. Vì thế gọi là *gần Decorator về cấu trúc*, không phải bằng chứng chắc chắn cho Decorator hành vi chuẩn. Không nhầm với `StringRenderer.Decoration.BOLD/ITALIC` (tên gọi “Decoration” không đồng nghĩa GoF Decorator).

## Giữ rõ code gốc / code tự phát triển
`src/main/java/vn/edu/patterns/extra/decorator/solitaire/LoggingMoveDecorator.java` và `.../jetuml/LoggingDiagramOperation.java` là **code demo đề xuất**. Các lớp gốc của JetUML chỉ được bổ sung ở thư mục `evidence` để làm bằng chứng, không thay thế những lớp đề xuất.

Bộ demo và JUnit cũ được giữ nguyên; người học đã chạy trên Eclipse và chụp ảnh 4/4 demo PASS, 18/18 JUnit PASS.
