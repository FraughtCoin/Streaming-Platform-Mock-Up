package utils;

public final class DatabaseUtils {

    private DatabaseUtils() {

    }

    public static String createTableQuery(String tableName, String[] columns, String[] columnsTypes) {
        StringBuilder query = new StringBuilder("CREATE TABLE " + tableName + " (");
//        for (String s : columns) {
//            query.append(s).append(" varchar(255),");
//        }
        for (int i = 0; i < columns.length; i++) {
            query.append(columns[i]).append(" ").append(columnsTypes[i]).append(",");
        }
        query = new StringBuilder(query.substring(0, query.length() - 1));
        query.append(");");
        return query.toString();
    }

    public static String insertIntoQuery(String tableName, String[] columns, String[] columnValues) {
        StringBuilder query = new StringBuilder("INSERT INTO " + tableName + " (");
        for (String s : columns) {
            query.append(s).append(", ");
        }
        query = new StringBuilder(query.substring(0, query.length() - 2));
        query.append(")\n").append("VALUES (");
        for (String s : columnValues) {
            query.append(s).append(", ");
        }
        query = new StringBuilder(query.substring(0, query.length() - 2));
        query.append(");");

        return query.toString();
    }

    public static String selectFromWhereEqualsQuery(String tableName, String columnName, String columnValue) {
        return "SELECT * FROM " + tableName + " WHERE " + columnName + "=" + columnValue;
    }
}
