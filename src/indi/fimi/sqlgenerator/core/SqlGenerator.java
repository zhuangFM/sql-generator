package indi.fimi.sqlgenerator.core;

import java.util.Arrays;
import java.util.List;

public class SqlGenerator {
    private List<String> colNameList;
    private String tableName;
    private String primaryKey;

    public List<String> getColNameList() {
        return colNameList;
    }

    public void setColNameList(List<String> colNameList) {
        this.colNameList = colNameList;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getInsertSql() {
        StringBuilder result = new StringBuilder("");
        result.append("INSERT INTO " + this.tableName + " (");
        for (String col : colNameList) {
            if (col.equals(this.primaryKey))
                continue;
            result.append("`" + col + "`,");
        }
        result.replace(result.length() - 1, result.length(), "");
        result.append(") VALUES (");
        for (String col : colNameList) {
            if (col.equals(this.primaryKey))
                continue;
            int position = col.indexOf("_");
            char[] toChArr = col.toCharArray();
            while (position > 0) {
                toChArr[position + 1] = Character.toUpperCase(toChArr[position + 1]);
                position = col.indexOf("_", position + 1);
            }
            result.append("#{" + String.valueOf(toChArr).replaceAll("_", "") + "},");
        }
        result.replace(result.length() - 1, result.length(), "");
        result.append(")");
        return result.toString();
    }

    public String getUpdateSql() {
        StringBuilder result = new StringBuilder("");
        result.append("UPDATE " + this.tableName + " SET ");
        for (String col : colNameList) {
            if (col.equals(this.primaryKey))
                continue;
            String str = "`" + col + "`" + "=#{";
            int position = col.indexOf("_");
            char[] toChArr = col.toCharArray();
            while (position > 0) {
                toChArr[position + 1] = Character.toUpperCase(toChArr[position + 1]);
                position = col.indexOf("_", position + 1);
            }
            str += String.valueOf(toChArr).replaceAll("_", "") + "},";
            result.append(str);
        }
        result.replace(result.length() - 1, result.length(), "");
        result.append(" WHERE " + "`" + this.primaryKey + "`=#{");
        int position = this.primaryKey.indexOf("_");
        char[] toChArr = this.primaryKey.toCharArray();
        while (position > 0) {
            toChArr[position + 1] = Character.toUpperCase(toChArr[position + 1]);
            position = this.primaryKey.indexOf("_", position + 1);
        }
        result.append(String.valueOf(toChArr).replaceAll("_", "") + "}");
        return result.toString();
    }

    public String getDeleteSql() {
        StringBuilder result = new StringBuilder("");
        result.append("DELETE FROM " + this.tableName + " WHERE " + this.primaryKey + "=#{");
        int position = this.primaryKey.indexOf("_");
        char[] toChArr = this.primaryKey.toCharArray();
        while (position > 0) {
            toChArr[position + 1] = Character.toUpperCase(toChArr[position + 1]);
            position = this.primaryKey.indexOf("_", position + 1);
        }
        result.append(String.valueOf(toChArr).replaceAll("_", "") + "}");
        return result.toString();
    }

    public String getSelectSql() {
        StringBuilder result = new StringBuilder("");
        result.append("SELECT * FROM " + this.tableName);
        return result.toString();
    }

    public String getSelectByPrimaryKeySql() {
        StringBuilder result = new StringBuilder("");
        result.append("SELECT * FROM " + this.tableName + " WHERE " + this.primaryKey + "=#{");
        int position = this.primaryKey.indexOf("_");
        char[] toChArr = this.primaryKey.toCharArray();
        while (position > 0) {
            toChArr[position + 1] = Character.toUpperCase(toChArr[position + 1]);
            position = this.primaryKey.indexOf("_", position + 1);
        }
        result.append(String.valueOf(toChArr).replaceAll("_", "") + "}");
        return result.toString();
    }

    public static void main(String[] args) {
        SqlGenerator sqlGenerator = new SqlGenerator();
        sqlGenerator.setTableName("gdpj_user");
        sqlGenerator.setPrimaryKey("user_id");
        sqlGenerator.setColNameList(Arrays.asList(new String[]{"user_id", "name", "password", "create_time"}));
        System.out.println(sqlGenerator.getInsertSql());
        System.out.println(sqlGenerator.getUpdateSql());
    }

}
