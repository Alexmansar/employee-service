package org.alexmansar.view;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Table {
    char C = '|';
    String LINE = "_____________________________________________________________________________";
   //String rowFormat;
   //String topFormat;
   //String tableName;

    protected void createLine() {
        System.out.println(LINE);
    }

    protected void createTableTitle(String topFormat, String tableName) {
        createLine();
        System.out.printf(topFormat, C, tableName, C);
        createLine();
    }
}