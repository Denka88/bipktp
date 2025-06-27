package com.denka88.bipktp.impl;

import com.denka88.bipktp.model.CTP;
import com.denka88.bipktp.model.Chapter;
import com.denka88.bipktp.model.Record;
import com.denka88.bipktp.model.User;
import com.denka88.bipktp.service.CTPService;
import com.denka88.bipktp.service.DocumentGenerationService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DocumentGenerationServiceImpl implements DocumentGenerationService {

    private final CTPService ctpService;

    @Override
    public byte[] generateCTPDocument(Long ctpId) {
        CTP ctp = ctpService.findById(ctpId)
                .orElseThrow(() -> new IllegalArgumentException("КТП не найдено"));

        try (InputStream templateStream = getClass().getResourceAsStream("/templates/template.docx");
             XWPFDocument doc = new XWPFDocument(templateStream);
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            replacePlaceholdersSmart(doc, Map.of(
                    "${discipline}", getSafe(ctp.getDiscipline().getName()),
                    "${speciality}", getSafe(ctp.getSpeciality().getName()),
                    "${period}", ctp.getPeriod().getStart() + "–" + ctp.getPeriod().getEnd(),
                    "${user}", getTeacherInitials(ctp.getUser())
            ));

            replacePlaceholdersInTextboxes(doc, Map.of(
                    "${committee}", getSafe(ctp.getCommittee().getName())
            ));

            System.out.println(ctp.getCommittee().getName());
            System.out.println(ctp.getDiscipline().getName());
            System.out.println(ctp.getSpeciality().getName());
            System.out.println(ctp.getPeriod().getStart() + "/" + ctp.getPeriod().getEnd());

            // 2. Вставка записей в таблицу
            insertRecordsIntoTable(doc, ctp.getChapters());

            doc.write(out);
            return out.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Ошибка при генерации документа КТП", e);
        }
    }

    private void replacePlaceholdersSmart(XWPFDocument doc, Map<String, String> placeholders) {
        for (XWPFParagraph paragraph : doc.getParagraphs()) {
            processRuns(paragraph.getRuns(), placeholders);
        }

        for (IBodyElement element : doc.getBodyElements()) {
            if (element instanceof XWPFParagraph paragraph) {
                processRuns(paragraph.getRuns(), placeholders);
            }
        }

        for (XWPFTable table : doc.getTables()) {
            for (XWPFTableRow row : table.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {
                    for (XWPFParagraph paragraph : cell.getParagraphs()) {
                        processRuns(paragraph.getRuns(), placeholders);
                    }
                }
            }
        }
    }

    private void processRuns(List<XWPFRun> runs, Map<String, String> placeholders) {
        if (runs == null || runs.isEmpty()) return;

        // Собираем текст всего параграфа
        StringBuilder fullText = new StringBuilder();
        for (XWPFRun run : runs) {
            String text = run.getText(0);
            if (text != null) fullText.append(text);
        }

        String replacedText = fullText.toString();
        boolean hasReplacements = false;
        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            if (replacedText.contains(entry.getKey())) {
                replacedText = replacedText.replace(entry.getKey(), entry.getValue());
                hasReplacements = true;
            }
        }

        // Только если что-то заменили — обновляем текст
        if (hasReplacements) {
            // Используем только первый run, остальные чистим
            XWPFRun firstRun = runs.get(0);
            firstRun.setText(replacedText, 0);
            for (int i = 1; i < runs.size(); i++) {
                runs.get(i).setText("", 0);
            }
        }
    }
    private void insertRecordsIntoTable(XWPFDocument doc, List<Chapter> chapters) {
        if (doc.getTables().size() < 3) return;
        XWPFTable table = doc.getTables().get(2); // 3-я таблица — содержательная часть

        int number = 1;
        for (Chapter chapter : chapters) {
            XWPFTableRow chapterRow = table.createRow();
            chapterRow.getCell(0).setText("Раздел: " + chapter.getTitle());

            for (Record record : chapter.getRecords()) {
                XWPFTableRow row = table.createRow();
                row.getCell(0).setText(String.valueOf(number++));
                row.getCell(1).setText(getSafe(record.getTitle()));
                row.getCell(2).setText(String.valueOf(record.getHours()));
                row.getCell(3).setText(getSafe(record.getLessonType().getName()));
                row.getCell(4).setText(record.getTeachMethods().stream()
                        .map(method -> getSafe(method.getName()))
                        .collect(Collectors.joining(", ")));
                row.getCell(5).setText(getSafe(record.getEquipment()));
                row.getCell(6).setText(getSafe(record.getHomework()));
            }
        }
    }

    private String getTeacherInitials(User user) {
        String surname = Optional.ofNullable(user.getSurname()).orElse("");
        String name = Optional.ofNullable(user.getName()).orElse("");
        String patronymic = Optional.ofNullable(user.getPatronymic()).orElse("");

        StringBuilder initials = new StringBuilder();

        if (!surname.isBlank()) {
            initials.append(surname).append(" ");
        }
        if (!name.isBlank()) {
            initials.append(name.charAt(0)).append(".");
        }
        if (!patronymic.isBlank()) {
            initials.append(patronymic.charAt(0)).append(".");
        }

        return initials.toString().trim();
    }

    private String getSafe(String text) {
        return text != null ? text : "";
    }

    private void replacePlaceholdersInTextboxes(XWPFDocument doc, Map<String, String> placeholders) throws Exception {
        XmlCursor cursor = doc.getDocument().newCursor();
        cursor.selectPath("declare namespace w='http://schemas.openxmlformats.org/wordprocessingml/2006/main' .//w:txbxContent");

        while (cursor.toNextSelection()) {
            XmlObject txbxContent = cursor.getObject();

            XmlCursor paragraphCursor = txbxContent.newCursor();
            paragraphCursor.selectPath("declare namespace w='http://schemas.openxmlformats.org/wordprocessingml/2006/main' .//w:p");

            while (paragraphCursor.toNextSelection()) {
                XmlObject p = paragraphCursor.getObject();
                XmlCursor tCursor = p.newCursor();
                tCursor.selectPath("declare namespace w='http://schemas.openxmlformats.org/wordprocessingml/2006/main' .//w:t");

                // 1. Собираем все тексты и курсоры
                List<XmlCursor> cursors = new ArrayList<>();
                StringBuilder combinedText = new StringBuilder();

                while (tCursor.toNextSelection()) {
                    XmlCursor current = tCursor.newCursor();
                    cursors.add(current);
                    combinedText.append(current.getTextValue());
                }

                // 2. Заменяем плейсхолдеры
                String resultText = combinedText.toString();
                for (Map.Entry<String, String> entry : placeholders.entrySet()) {
                    resultText = resultText.replace(entry.getKey(), entry.getValue());
                }

                // 3. Перезаписываем только первый <w:t>, остальные очищаем
                if (!cursors.isEmpty()) {
                    cursors.get(0).setTextValue(resultText);
                    for (int i = 1; i < cursors.size(); i++) {
                        cursors.get(i).setTextValue("");
                    }
                }

                tCursor.dispose();
            }

            paragraphCursor.dispose();
        }

        cursor.dispose();
    }
}