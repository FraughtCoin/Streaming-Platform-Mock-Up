package utils;

public final class DatabaseUtils {

    private DatabaseUtils() {

    }

    public static String createTableQuery(String tableName, String[] columns, String[] columnsTypes) {
        StringBuilder query = new StringBuilder("CREATE TABLE " + tableName + " (");
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

    public static String selectFromWhere2EqualsQuery(String tableName, String columnName1, String columnValue1,
                                                     String columnName2, String columnValue2) {
        return "SELECT * FROM " + tableName + " WHERE " + columnName1 + "=" + columnValue1 +
                " AND " + columnName2 + "=" + columnValue2;
    }

    public static String deleteFromWhereEqualsQuery(String tableName, String columnName, String columnValue) {
        return "DELETE FROM " + tableName + " WHERE " + columnName + "=" + columnValue;
    }

    public static String deleteFromWhere2EqualsQuery(String tableName, String columnName1, String columnValue1,
                                                     String columnName2, String columnValue2) {
        return "DELETE FROM " + tableName + " WHERE " + columnName1 + "=" + columnValue1 +
                " AND " + columnName2 + "=" + columnValue2;
    }

    public static String updateColumnWhere(String tableName, String columnName, String columnValue, String newValue) {
        return "UPDATE " + tableName + "\n" + "SET " + columnName + "=" + newValue + "\n" +
                "WHERE " + columnName + "=" + columnValue;
    }
}
