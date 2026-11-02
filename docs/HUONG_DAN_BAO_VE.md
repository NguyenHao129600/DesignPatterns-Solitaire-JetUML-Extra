# Hướng dẫn giải thích project khi báo cáo

## Composite – Solitaire
`Move` định nghĩa `perform()` và `undo()`. `CompositeMove implements Move`, lưu nhiều `Move` trong `List<Move>`. `perform()` gọi lần lượt từng move, còn `undo()` chạy ngược danh sách. `ExtraPatternLauncher` dùng hai thao tác mẫu A, B để kiểm chứng `ABba`.

## Composite – JetUML
`DiagramOperation` định nghĩa `execute()` và `undo()`. `SimpleOperation` là phần tử đơn, `CompoundOperation` là phần tử tổng hợp chứa `List<DiagramOperation>`. Đặc biệt, `CompoundOperation` có annotation `@Composite` trong mã nguồn gốc. Demo `ABCcba` dùng một compound nằm trong compound khác.

## Decorator – Solitaire (ĐỀ XUẤT)
`LoggingMoveDecorator implements Move` và chứa **một** trường `wrapped: Move`. Nó ghi log rồi chuyển tiếp lời gọi `wrapped.perform()` / `wrapped.undo()` và trả `wrapped.isNull()`. Có thể bọc `CompositeMove` hoặc một decorator khác mà không sửa file gốc.

## Decorator – JetUML (ĐỀ XUẤT)
`LoggingDiagramOperation implements DiagramOperation`, giữ `wrapped: DiagramOperation` rồi ghi log trước/sau lời gọi `wrapped.execute()` và `wrapped.undo()`. Demo bọc trực tiếp một `CompoundOperation` gồm các `SimpleOperation`.

## Phân biệt hai mẫu
Composite thường **chứa danh sách** đối tượng cùng giao diện; Decorator thường **chứa một** đối tượng cùng giao diện và thêm hành vi bằng ủy quyền. Đây là dấu hiệu nhận dạng, không phải quy tắc duy nhất.

## Test
- `CompositeMoveTest` + `CompoundOperationTest` là các lớp kiểm thử **sao chép từ ứng dụng gốc**.
- `SolitairePatternExtraTest` + `JetUmlPatternExtraTest` là kiểm thử **viết thêm** để xác minh thứ tự, cây lồng nhau, logging và khả năng bọc lồng nhau.

## Trả lời giảng viên
**Hỏi:** Decorator có sẵn trong hai project không? **Đáp:** Phần mã đã khảo sát chưa cho bằng chứng đủ chắc chắn để kết luận. Hai lớp Logging* là ví dụ thiết kế mở rộng của em, không có trong ZIP gốc.

**Hỏi:** Sao không chạy nguyên ứng dụng? **Đáp:** Bài này nhằm kiểm chứng trực tiếp các lớp tham gia design pattern, nên em trích nguyên bản các lớp độc lập và chạy demo không cần JavaFX. Muốn demo màn hình gốc phải chạy project Solitaire và JetUML đầy đủ.
