package com.example.danilo.checklistapp.database;

/**
 * Created by danilo on 03/05/18.
 */

public class ScriptDLL {

    public static String getCreateTableChecklist(){

        StringBuilder sql = new StringBuilder();

        sql.append("CREATE TABLE Checklist (");
        sql.append("        ID          INTEGER    PRIMARY KEY");
        sql.append("        NOT NULL,");
        sql.append("        Description TEXT (200) DEFAULT (''),");
        sql.append("        Active      BOOLEAN    DEFAULT True");
        sql.append(");");

        return sql.toString();

    }

    public static String getChecklists(){
        StringBuilder sql = new StringBuilder();
        sql.append("Select ID,Description,Active");
        sql.append(" from Checklist");
        return sql.toString();
    }

    public static String getChecklist(){
        StringBuilder sql = new StringBuilder();
        sql.append("Select ID,Description,Active");
        sql.append(" from Checklist");
        sql.append(" where ID = ?");
        return sql.toString();
    }
}
