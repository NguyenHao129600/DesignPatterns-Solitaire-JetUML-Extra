# PROJECT CỘNG ĐIỂM – Composite & Decorator trong Solitaire và JetUML

**Mục đích:** Project Java Maven độc lập để minh họa và kiểm thử các ví dụ trong báo cáo cộng điểm. Không cần chạy giao diện JavaFX của hai ứng dụng gốc.

## 1. Điểm quan trọng để trình bày với giảng viên

| Project | Composite trong mã gốc | Decorator |
|---|---|---|
| Solitaire | `ca.mcgill.solitaire.model.Move` + `CompositeMove` | `LoggingMoveDecorator` là **lớp tự đề xuất**, KHÔNG có sẵn trong source gốc |
| JetUML | `org.jetuml.diagram.builder.DiagramOperation` + `SimpleOperation` + `CompoundOperation` | `LoggingDiagramOperation` là **lớp tự đề xuất**. Mã gốc còn có `NamedHandler`/`NewDiagramHandler` **gần Decorator về cấu trúc** (xem `docs/DOI_CHIEU_DECORATOR_GOC.md`), nhưng không có hành vi ghi log như ví dụ tự viết |

**Cần nhấn mạnh:** Phần Composite trích nguyên văn lớp liên quan từ hai ZIP đã nhận; phần Decorator là thiết kế mới để minh họa khả năng mở rộng. Việc có Decorator trong *project demo này* **không chứng minh** nó có sẵn trong toàn bộ ứng dụng Solitaire hoặc JetUML.

## 2. Nguồn các lớp gốc (không chỉnh sửa)

**Solitaire** (từ `Solitaire-master (2)(1).zip`):
- `src/main/java/ca/mcgill/solitaire/model/Move.java`
- `src/main/java/ca/mcgill/solitaire/model/CompositeMove.java`
- `src/test/java/ca/mcgill/solitaire/model/CompositeMoveTest.java` (test từ project gốc)

**JetUML** (từ `JetUML-master (1)(1).zip`):
- `src/main/java/org/jetuml/diagram/builder/DiagramOperation.java`
- `src/main/java/org/jetuml/diagram/builder/SimpleOperation.java`
- `src/main/java/org/jetuml/diagram/builder/CompoundOperation.java`
- `src/main/java/org/jetuml/annotations/Command.java`
- `src/main/java/org/jetuml/annotations/Composite.java`
- `src/test/java/org/jetuml/diagram/builder/CompoundOperationTest.java` (test từ project gốc)

Các lớp mã gốc được giữ nguyên thông báo bản quyền và giấy phép. Giấy phép gốc ở `LICENSES/`.

Các lớp đề xuất và demo do người thực hiện project tạo nằm dưới package `vn.edu.patterns.extra`. `GameModel.java` và `ObjectNode.java` vẫn nằm trong hai ZIP của ứng dụng gốc, không sao chép vào demo vì phụ thuộc các thành phần khác / giao diện JavaFX.

## 3. Cách chạy trên Eclipse

1. Giải nén ZIP và mở Eclipse.
2. `File > Import > Maven > Existing Maven Projects > Next`.
3. Chọn đúng thư mục có file `pom.xml` của project này > `Finish`.
4. Mở `src/main/java/vn/edu/patterns/extra/demo/ExtraPatternLauncher.java`.
5. Chuột phải > `Run As > Java Application`.
6. Chương trình in bốn demo và `KET LUAN: 4 DEMO PASS` nếu thành công.
7. Để kiểm thử: chuột phải `src/test/java` > `Run As > JUnit Test`. Các lớp JUnit chứa test gốc lẫn test bổ sung (**18 test case**).

**JDK:** Java 17 trở lên (JDK 21 hoặc 23 đều dùng được). **Không cần** MySQL, JavaFX hay máy chủ web.

## 4. Chạy bằng PowerShell

Khi đã có JDK, mở PowerShell trong thư mục dự án:

```powershell
.\run-demo.ps1
```

Nếu máy chặn chạy script, sử dụng Eclipse; hoặc chạy riêng lệnh của script. Để chạy JUnit bằng Maven (cần cài Maven hoặc để Eclipse quản lý):

```powershell
mvn clean test
```

## 5. Kết quả demo dự kiến

```text
=== 1. SOLITAIRE: COMPOSITE GOC ===
Thu tu perform/undo: ABba (PASS)

=== 2. SOLITAIRE: DECORATOR DE XUAT ===
[Solitaire] Bat dau perform
[Solitaire] Hoan thanh perform
[Solitaire] Bat dau undo
[Solitaire] Hoan thanh undo
Thu tu sau khi boc: ABba (PASS)

=== 3. JETUML: COMPOSITE GOC (LONG NHAU) ===
Thu tu execute/undo: ABCcba (PASS)

=== 4. JETUML: DECORATOR DE XUAT ===
[JetUML] Bat dau execute
[JetUML] Hoan thanh execute
[JetUML] Bat dau undo
[JetUML] Hoan thanh undo
Thu tu sau khi boc: ABCcba (PASS)

=== KET LUAN: 4 DEMO PASS ===
```

`ABba`: chạy A rồi B, undo B rồi A. `ABCcba`: chạy A-B-C và undo C-B-A (bao gồm Composite lồng nhau).

## 6. Gợi ý ảnh minh chứng cho báo cáo riêng

1. Solitaire: `Move.java`, `CompositeMove.java` (trường `List<Move>`; `perform()`; `undo()`).
2. JetUML: `DiagramOperation.java`, `CompoundOperation.java` (annotation `@Composite`; `List<DiagramOperation>`; `execute()`; `undo()`).
3. Hai lớp **đề xuất**: `LoggingMoveDecorator.java`, `LoggingDiagramOperation.java` (trường `wrapped`, chức năng ủy quyền).
4. Console khi chạy `ExtraPatternLauncher.java`.
5. Tab JUnit (sau khi chạy test thành công trên máy).

Sơ đồ Mermaid xem trong `docs/UML.md`. Đừng trình bày ảnh demo này thành ảnh chạy đầy đủ ứng dụng Solitaire hoặc JetUML.

## 7. Giấy phép và nguồn

- Solitaire: https://github.com/prmr/Solitaire — GPL-3.0-or-later (theo thông báo ở file nguồn).
- JetUML: https://github.com/prmr/JetUML — GPL-3.0-or-later (theo thông báo ở file nguồn).
- Hai ZIP nguồn do người học cung cấp. Các file nguyên bản được giữ nguyên; lớp Decorator, launcher và các test bổ sung là phần tự phát triển cho mục đích giáo dục.
- Project demo bao gồm phần mã gốc GPL, vì thế khi phân phối cần tuân thủ điều khoản GPL và giữ các thông báo bản quyền/giấy phép.

## 8. Bổ sung rà soát nguồn gốc

Xem `docs/DOI_CHIEU_DECORATOR_GOC.md` và `evidence/JetUML-goc/` để xem 3 file **nguyên bản** được tìm thấy sau khi rà soát sâu. Những file này chỉ là tài liệu đối chiếu, không được biên dịch trong project độc lập và không làm thay đổi 18 test.
