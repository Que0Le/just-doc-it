
package org.jdi.plugin.notes;

import org.jdi.plugin.common.LoggerFactory;
import org.jdi.plugin.common.PluginLogger;
import org.jdi.plugin.notes.model.Note;

import java.util.*;
import java.util.stream.Collectors;

public class NoteIndex {

    private static final PluginLogger LOG = LoggerFactory.getLogger(NoteIndex.class);

    // ── primary store ─────────────────────────────────────────────────────────
    private final List<Note> notes = new ArrayList<>();

    // ── lookup maps ───────────────────────────────────────────────────────────
    private Map<String, List<Note>> byFqn     = new HashMap<>();
    private Map<String, List<Note>> byProject = new HashMap<>();
    private Map<String, List<Note>> byKind    = new HashMap<>();
    private Map<String, List<Note>> byStatus  = new HashMap<>();
    private Map<String, List<Note>> bySeverity= new HashMap<>();
    private Map<String, List<Note>> byAuthor  = new HashMap<>();
    private Map<String, List<Note>> byTag     = new HashMap<>();
    private Map<String, Note>       byId      = new HashMap<>();

    // ── singleton ─────────────────────────────────────────────────────────────
    private static final NoteIndex INSTANCE = new NoteIndex();

    private NoteIndex() {}

    public static NoteIndex getInstance() {
        return INSTANCE;
    }

    // ── load ──────────────────────────────────────────────────────────────────

    public synchronized void loadAll(List<Note> incoming) {
        notes.clear();
        notes.addAll(incoming);
        rebuildIndex();
        LOG.info("Index loaded: " + notes.size() + " notes");
    }

    public synchronized void addNote(Note note) {
        notes.add(note);
        rebuildIndex();
    }

    public synchronized void removeNote(String id) {
        notes.removeIf(n -> id.equals(n.id));
        rebuildIndex();
    }

    public synchronized void updateNote(Note updated) {
        notes.removeIf(n -> updated.id.equals(n.id));
        notes.add(updated);
        rebuildIndex();
    }

    // ── queries (immutable views) ─────────────────────────────────────────────

    public List<Note> all() {
        return Collections.unmodifiableList(notes);
    }

    public List<Note> byFqn(String fqn) {
        return byFqn.getOrDefault(fqn, Collections.emptyList());
    }

    public List<Note> byProject(String project) {
        return byProject.getOrDefault(project, Collections.emptyList());
    }

    public List<Note> byKind(String kind) {
        return byKind.getOrDefault(kind, Collections.emptyList());
    }

    public List<Note> byStatus(String status) {
        return byStatus.getOrDefault(status, Collections.emptyList());
    }

    public List<Note> bySeverity(String severity) {
        return bySeverity.getOrDefault(severity, Collections.emptyList());
    }

    public List<Note> byAuthor(String author) {
        return byAuthor.getOrDefault(author, Collections.emptyList());
    }

    public List<Note> byTag(String tag) {
        return byTag.getOrDefault(tag, Collections.emptyList());
    }

    public Optional<Note> byId(String id) {
        return Optional.ofNullable(byId.get(id));
    }

    public List<Note> search(String text) {
        if (text == null || text.isBlank()) return all();
        String lower = text.toLowerCase();
        return notes.stream()
                .filter(n -> contains(n.brief, lower)
                        || contains(n.kind, lower)
                        || contains(n.status, lower)
                        || containsAny(n.tags, lower)
                        || containsAny(n.details, lower)
                        || (n.ref != null && contains(n.ref.fqn, lower)))
                .collect(Collectors.toList());
    }

    public int size() {
        return notes.size();
    }

    // ── index rebuild ─────────────────────────────────────────────────────────

    private void rebuildIndex() {
        byFqn      = groupBy(n -> n.ref != null ? n.ref.fqn : null);
        byProject  = groupBy(n -> n.ref != null ? n.ref.project : null);
        byKind     = groupBy(n -> n.kind);
        byStatus   = groupBy(n -> n.status);
        bySeverity = groupBy(n -> n.severity);
        byAuthor   = groupBy(n -> n.author);
        byId       = new HashMap<>();
        byTag      = new HashMap<>();

        for (Note note : notes) {
            if (note.id != null) byId.put(note.id, note);
            if (note.tags != null) {
                for (String tag : note.tags) {
                    byTag.computeIfAbsent(tag, k -> new ArrayList<>()).add(note);
                }
            }
        }

        LOG.debug("Index rebuilt: " + notes.size() + " notes across "
                + byFqn.size() + " FQNs, "
                + byProject.size() + " projects, "
                + byTag.size() + " tags");
    }

    // ── helpers ───────────────────────────────────────────────────────────────

    private Map<String, List<Note>> groupBy(java.util.function.Function<Note, String> keyFn) {
        Map<String, List<Note>> map = new HashMap<>();
        for (Note note : notes) {
            String key = keyFn.apply(note);
            if (key != null) map.computeIfAbsent(key, k -> new ArrayList<>()).add(note);
        }
        return map;
    }

    private boolean contains(String field, String lower) {
        return field != null && field.toLowerCase().contains(lower);
    }

    private boolean containsAny(List<String> fields, String lower) {
        if (fields == null) return false;
        return fields.stream().anyMatch(f -> contains(f, lower));
    }
}
