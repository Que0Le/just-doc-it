package org.jdi.plugin.notes.model;

import java.util.List;

public class Note {

    // ── identity ──────────────────────────────────────────────────────────────
    public String id;
    public String schemaVersion;

    // ── reference ─────────────────────────────────────────────────────────────
    public Ref ref;

    // ── content ───────────────────────────────────────────────────────────────
    public String brief;
    public List<String> details;

    // ── classification ────────────────────────────────────────────────────────
    public String kind;       // observation | question | warning | todo | explanation | contract | decision | deprecated
    public String status;     // open | resolved | stale | wontfix
    public String severity;   // critical | high | medium | low | info
    public List<String> tags;

    // ── authorship ────────────────────────────────────────────────────────────
    public String author;
    public String createdAt;
    public String updatedAt;

    // ── links ─────────────────────────────────────────────────────────────────
    public List<String> linkedNotes;    // "sourceId::noteId"
    public List<String> linkedTickets;  // "JIRA-1234"

    // ── display ───────────────────────────────────────────────────────────────
    public String visibility;  // private | team | group | department
    public boolean inline;

    // ── nested ref ───────────────────────────────────────────────────────────
    public static class Ref {
        public String kind;        // method | class | field | parameter | file | package
        public String fqn;         // com.example.Service#methodName
        public String file;        // relative path from project root
        public int    line;        // soft fallback if fqn is stale
        public String project;
        public String module;
        public String commit;
        public String branch;
        public String stability;   // "fqn" | "line"
    }
}