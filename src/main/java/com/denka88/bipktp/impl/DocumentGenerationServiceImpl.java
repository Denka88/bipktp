package com.denka88.bipktp.impl;

import com.denka88.bipktp.model.CTP;
import com.denka88.bipktp.model.Chapter;
import com.denka88.bipktp.model.Record;
import com.denka88.bipktp.model.User;
import com.denka88.bipktp.service.CTPService;
import com.denka88.bipktp.service.DocumentGenerationService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
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

            // 1. Подстановка текстовых плейсхолдеров
            replaceAllPlaceholders(doc, Map.of(
                    "${committee}", getSafe(ctp.getCommittee().getName()),
                    "${discipline}", getSafe(ctp.getDiscipline().getName()),
                    "${speciality}", getSafe(ctp.getSpeciality().getName()),
                    "${period}", ctp.getPeriod().getStart() + "–" + ctp.getPeriod().getEnd(),
                    "${user}", getTeacherInitials(ctp.getUser())
            ));

            // 2. Вставка записей в таблицу
            insertRecordsIntoTable(doc, ctp.getChapters());

            doc.write(out);
            return out.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Ошибка при генерации документа КТП", e);
        }
    }

    private void replaceAllPlaceholders(XWPFDocument doc, Map<String, String> placeholders) {
        for (XWPFParagraph paragraph : doc.getParagraphs()) {
            replaceInParagraph(paragraph, placeholders);
        }

        for (XWPFTable table : doc.getTables()) {
            for (XWPFTableRow row : table.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {
                    for (XWPFParagraph paragraph : cell.getParagraphs()) {
                        replaceInParagraph(paragraph, placeholders);
                    }
                }
            }
        }
    }

    private void replaceInParagraph(XWPFParagraph paragraph, Map<String, String> placeholders) {
        for (XWPFRun run : paragraph.getRuns()) {
            String text = run.getText(0);
            if (text != null) {
                for (Map.Entry<String, String> entry : placeholders.entrySet()) {
                    text = text.replace(entry.getKey(), entry.getValue());
                }
                run.setText(text, 0);
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
}