package org.jdi.plugin.notes;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.jdi.plugin.common.LoggerFactory;
import org.jdi.plugin.common.PluginLogger;
import org.jdi.plugin.notes.model.Note;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NoteScanner {

    private static final PluginLogger LOG  = LoggerFactory.getLogger(NoteScanner.class);
    private static final Gson         GSON = new Gson();
    private static final Type         NOTE_LIST_TYPE = new TypeToken<List<Note>>() {}.getType();

    private final Path rootPath;

    public NoteScanner(String rootFolder) {
        this.rootPath = Paths.get(rootFolder);
    }

    // ── public entry point ────────────────────────────────────────────────────

    public List<Note> scanAll() {
        if (!Files.exists(rootPath) || !Files.isDirectory(rootPath)) {
            LOG.warn("Notes root folder not found or not a directory: " + rootPath);
            return Collections.emptyList();
        }

        List<Note> collected = new ArrayList<>();

        try {
            Files.walkFileTree(rootPath, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    if (isNoteFile(file)) {
                        List<Note> parsed = parseFile(file);
                        collected.addAll(parsed);
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException e) {
                    LOG.error("Failed to visit file: " + file, e);
                    return FileVisitResult.CONTINUE;  // skip, don't abort the whole scan
                }
            });
        } catch (IOException e) {
            LOG.error("Failed to scan notes root: " + rootPath, e);
        }

        LOG.info("Scan complete: " + collected.size() + " notes found in " + rootPath);
        return collected;
    }

    // ── file parsing ──────────────────────────────────────────────────────────

    private List<Note> parseFile(Path file) {
        try (Reader reader = Files.newBufferedReader(file)) {
            List<Note> notes = GSON.fromJson(reader, NOTE_LIST_TYPE);
            if (notes == null) {
                LOG.warn("Empty or null JSON array in: " + file);
                return Collections.emptyList();
            }
            LOG.debug("Parsed " + notes.size() + " notes from: " + rootPath.relativize(file));
            return notes;
        } catch (Exception e) {
            LOG.error("Failed to parse note file: " + file, e);
            return Collections.emptyList();  // skip bad file, don't abort scan
        }
    }

    // ── helpers ───────────────────────────────────────────────────────────────

    private boolean isNoteFile(Path file) {
        return file.toString().endsWith(".json")
                && !file.getFileName().toString().startsWith(".");  // skip hidden files
    }
}
